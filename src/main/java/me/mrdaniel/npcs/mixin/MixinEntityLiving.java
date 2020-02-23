package me.mrdaniel.npcs.mixin;

import com.flowpowered.math.vector.Vector3d;
import me.mrdaniel.npcs.NPCs;
import me.mrdaniel.npcs.actions.ActionSet;
import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyType;
import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyTypes;
import me.mrdaniel.npcs.interfaces.mixin.NPCAble;
import me.mrdaniel.npcs.io.INPCData;
import me.mrdaniel.npcs.utils.Position;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

@Mixin(value = EntityLiving.class, priority = 10)
public abstract class MixinEntityLiving extends EntityLivingBase implements NPCAble {

	private INPCData data;
	private boolean looking;

	public MixinEntityLiving(World worldIn) {
		super(worldIn);

		this.data = null;
		this.looking = false;
	}

	@Shadow public abstract void enablePersistence();
	@Shadow public abstract void setCanPickUpLoot(boolean canPickup);
	@Shadow public abstract void setNoAI(boolean disable);

	@Nullable
	@Override
	public INPCData getNPCData() {
		return this.data;
	}

	@Override
	public void setNPCData(INPCData data) {
		this.data = data;

		this.setup(PropertyTypes.NPC_INIT);
	}

	@Override
	public void refreshNPC() {
		PropertyTypes.ARMOR.forEach(prop -> prop.apply(this, null));

		Task.builder().delayTicks(5).execute(() -> this.setup(PropertyTypes.NPC_RELOAD)).submit(NPCs.getInstance());
	}

	private void setup(List<PropertyType> properties) {
		this.enablePersistence();
		this.setCanPickUpLoot(false);
		this.setNoAI(true);
		super.setDropItemsWhenDead(false);
		super.setEntityInvulnerable(true);
		super.setNoGravity(true);

		properties.stream()
				.filter(prop -> prop.isSupported(this))
				.forEach(prop -> this.data.getNPCProperty(prop)
						.ifPresent(value -> prop.apply(this, value)));
	}

	@Override
	public void setNPCLooking(boolean value) {
		this.looking = value;
	}

	@Override
	public boolean isNPCLooking() {
		return this.looking;
	}

	@Override
	public int getNPCId() {
		return this.data.getNPCId();
	}

	@Override
	public Position getNPCPosition() {
		return this.data.getNPCPosition();
	}

	@Override
	public INPCData setNPCPosition(Position value) {
		return this.data.setNPCPosition(value);
	}

	@Override
	public <T> Optional<T> getNPCProperty(PropertyType<T> property) {
		return this.data.getNPCProperty(property);
	}

	@Override
	public <T> INPCData setNPCProperty(PropertyType<T> property, T value) {
		this.data.setNPCProperty(property, value);
		property.apply(this, value);
		return this.data;
	}

	@Override
	public ActionSet getNPCActions() {
		return this.data.getNPCActions();
	}

	@Override
	public INPCData writeNPCActions() {
		return this.data.writeNPCActions();
	}

	@Override
	public void saveNPC() {
		this.data.saveNPC();
	}

	@Inject(method = "onEntityUpdate", at = @At("RETURN"))
	public void onOnEntityUpdate(CallbackInfo ci) {
		if (this.data != null && this.looking) {
			super.world.getEntities(EntityPlayer.class, p -> p.getDistanceToEntity(this) < 20.0 && p.getUniqueID() != super.entityUniqueID)
					.stream()
					.reduce((p1, p2) -> p1.getDistanceToEntity(this) < p2.getDistanceToEntity(this) ? p1 : p2)
					.ifPresent(p -> ((Living)this).lookAt(new Vector3d(p.posX, p.posY + p.getEyeHeight(), p.posZ)));
		}
	}
}
