package me.mrdaniel.npcs.mixin;

import com.flowpowered.math.vector.Vector3d;
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
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;
import java.util.List;

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
	public INPCData getData() {
		return this.data;
	}

	@Override
	public void setData(INPCData data) {
		this.data = data;

		this.setup(PropertyTypes.NPC_INIT);
	}

	private void setup(List<PropertyType> properties) {
		this.enablePersistence();
		this.setCanPickUpLoot(false);
		this.setNoAI(true);
		this.setDropItemsWhenDead(false);
		this.setEntityInvulnerable(true);
		this.setNoGravity(true);

		Position pos = this.data.getPosition();
		this.setPositionAndRotation(pos.getX(), pos.getY(), pos.getZ(), pos.getYaw(), pos.getPitch());

		properties.stream()
				.filter(prop -> prop.isSupported(this))
				.forEach(prop -> this.data.getProperty(prop)
						.ifPresent(value -> prop.apply(this, value)));
	}

	@Override
	public void setLooking(boolean value) {
		this.looking = value;
	}

	@Override
	public INPCData setPosition(Position value) {
		this.setPositionAndRotation(value.getX(), value.getY(), value.getZ(), value.getYaw(), value.getPitch());
		return this.data.setPosition(value);
	}

	@Override
	public <T> INPCData setProperty(PropertyType<T> property, T value) {
		this.data.setProperty(property, value);
		property.apply(this, value);
		return this.data;
	}

	@Inject(method = "onEntityUpdate", at = @At("RETURN"))
	public void onOnEntityUpdate(CallbackInfo ci) {
		if (this.data != null && this.looking) {
			this.world.getEntities(EntityPlayer.class, p -> p.getDistanceToEntity(this) < 20.0 && p.getUniqueID() != this.entityUniqueID)
					.stream()
					.reduce((p1, p2) -> p1.getDistanceToEntity(this) < p2.getDistanceToEntity(this) ? p1 : p2)
					.ifPresent(p -> ((Living)this).lookAt(new Vector3d(p.posX, p.posY + p.getEyeHeight(), p.posZ)));
		}
	}
}
