package me.mrdaniel.npcs.mixin;

import com.flowpowered.math.vector.Vector3d;
import me.mrdaniel.npcs.NPCs;
import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyType;
import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyTypes;
import me.mrdaniel.npcs.interfaces.mixin.NPCAble;
import me.mrdaniel.npcs.io.INPCData;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;
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

		this.enablePersistence();
		this.setCanPickUpLoot(false);
		this.setNoAI(true);
		super.setDropItemsWhenDead(false);
		super.setEntityInvulnerable(true);
		super.setNoGravity(true);

		// TODO: Make a list of properties to apply
		PropertyTypes.NPC_INIT.forEach(prop -> prop.apply(this, this.data.getProperty(prop)));

//		Position pos = data.getProperty(PropertyTypes.POSITION).get();
//		super.setLocationAndAngles(pos.getX(), pos.getY(), pos.getZ(), pos.getYaw(), pos.getPitch());

//		data.getProperty(PropertyTypes.SKIN_UUID).ifPresent(value -> PropertyTypes.SKIN_UUID.apply(this, value));
//		PropertyTypes.MAIN.forEach(prop -> this.data.getProperty(prop).ifPresent(value -> this.setProperty(prop, value)));
//		PropertyTypes.ARMOR.forEach(prop -> this.data.getProperty(prop).ifPresent(value -> this.setProperty(prop, value)));
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
	public int getId() {
		return this.data.getId();
	}

	@Override
	public <T> Optional<T> getProperty(PropertyType<T> property) {
		return this.data.getProperty(property);
	}

	@Override
	public <T> INPCData setProperty(PropertyType<T> property, T value) {
		property.apply(this, value);
		return this.data.setProperty(property, value);
	}

	@Override
	public void save() {
		this.data.save();
	}

	/*
		Old methods
	 */

//	@Override
//	public void setNPCWorld(org.spongepowered.api.world.World value) {
//		super.setWorld((World)value);
//	}
//
//	@Override
//	public void setNPCPosition(Position value) {
//		super.setLocationAndAngles(value.getX(), value.getY(), value.getZ(), value.getYaw(), value.getPitch());
//	}
//
//	@Override
//	public void setNPCName(String value) {
//		((Living)this).offer(Keys.DISPLAY_NAME, TextUtils.toText(value));
//		super.setAlwaysRenderNameTag(true);
//
//		if (this.data.getProperty(PropertyTypes.GLOWING)) { this.data.getGlowColor().ifPresent(color -> this.setNPCGlowColor(color)); }
//	}
//
//	@Override
//	public void setNPCSkin(String value) {
//		new Thread(() -> {
//			NPCs.getInstance().getGame().getServer().getGameProfileManager().get(value).thenAccept(gp -> Task.builder().delayTicks(0).execute(() -> {
//				this.setNPCSkin(gp.getUniqueId());
//				this.data.setSkinUUID(gp.getUniqueId()).save();
//			}).submit(NPCs.getInstance()));
//		}).start();
//	}
//
//	@Override
//	public void setNPCSkin(UUID value) {
//		((Human)this).offer(Keys.SKIN_UNIQUE_ID, value);
//	}
//
//	@Override
//	public void setNPCLooking(boolean value) {
//		this.looking = value;
//	}
//
//	@Override
//	public boolean isNPCLooking() {
//		return this.looking;
//	}
//
//	@Override
//	public void setNPCInteract(boolean value) {
//		this.interact = value;
//	}
//
//	@Override
//	public boolean canNPCInteract() {
//		return this.interact;
//	}
//
//	@Override
//	public void setNPCSilent(boolean value) {
//		super.setSilent(value);
//	}
//
//	@Override
//	public void setNPCGlowing(boolean value) {
//		super.setGlowing(value);
//
//		if (value) {
//			this.data.getGlowColor().ifPresent(this::setNPCGlowColor);
//		}
//	}
//
//	@Override
//	public void setNPCGlowColor(GlowColor value) {
//		if (this.isGlowing()) {
//			String teamName = "NPC_" + value.getName();
//			String npcName = (this instanceof Human) ? super.getCustomNameTag() : super.getCachedUniqueIdString();
//			Scoreboard board =  super.world.getScoreboard();
//			ScorePlayerTeam team = board.getTeam(teamName);
//			if (team == null) { team = board.createTeam(teamName); }
//
//			board.addPlayerToTeam(npcName, teamName);
//			team.setColor(value.getColor());
//			team.setPrefix(value.getColor().toString());
//			team.setSuffix(TextFormatting.RESET.toString());
//		}
//	}
//
//	@Override
//	public void setNPCBaby(boolean value) {
//		if ((Object)this instanceof EntityZombie) {
//			((EntityZombie)(Object)this).setChild(value);
//		} else {
//			((EntityAgeable)(Object)this).setGrowingAge(value ? Integer.MIN_VALUE : 0);
//		}
//
//		super.setLocationAndAngles(this.data.getX(), this.data.getY(), this.data.getZ(), this.data.getYaw(), this.data.getPitch());
//	}
//
//	@Override
//	public void setNPCCharged(boolean value) {
//		((Living)this).offer(Keys.CREEPER_CHARGED, value);
//	}
//
//	@Override
//	public void setNPCAngry(boolean value) {
//		((EntityWolf) (Object) this).setAngry(value);
//	}
//
//	@Override
//	public void setNPCSize(int value) {
//		((Living)this).offer(Keys.SLIME_SIZE, value);
//	}
//
//	@Override
//	public void setNPCSitting(boolean value) {
//		((EntityTameable)(Object)this).setSitting(value);
//	}
//
//	@Override
//	public void setNPCHanging(boolean value) {
//		((EntityBat)(Object)this).setIsBatHanging(value);
//	}
//
//	@Override
//	public void setNPCPumpkin(boolean value) {
//		((EntitySnowman)(Object)this).setPumpkinEquipped(value);
//	}
//
//	@Override
//	public void setNPCSaddle(boolean value) {
//		((EntityPig)(Object)this).setSaddled(value);
//	}
//
//	@Override
//	public void setNPCCareer(Career value) {
//		((EntityVillager)(Object)this).setProfession(value.getProfessionId());
//		((IMixinEntityVillager)this).setCareerId(value.getCareerId());
//	}
//
//	@Override
//	public void setNPCHorsePattern(HorsePattern value) {
//		((EntityHorse)(Object)this).setHorseVariant(value.getNbtId() + this.data.getHorseColor().orElse(HorseColors.BROWN).getNbtId());
//	}
//
//	@Override
//	public void setNPCHorseColor(HorseColor value) {
//		((EntityHorse)(Object)this).setHorseVariant(value.getNbtId() + this.data.getHorsePattern().orElse(HorsePatterns.NONE).getNbtId());
//	}
//
//	@Override
//	public void setNPCLlamaType(LlamaType value) {
//		((EntityLlama)(Object)this).setVariant(value.getNbtId());
//	}
//
//	@Override
//	public void setNPCCatType(CatType value) {
//		((EntityOcelot)(Object)this).setTameSkin(value.getNbtId());
//	}
//
//	@Override
//	public void setNPCRabbitType(RabbitType value) {
//		((EntityRabbit)(Object)this).setRabbitType(value.getNbtId());
//	}
//
//	@Override
//	public void setNPCParrotType(ParrotType value) {
//		((EntityParrot)(Object)this).setVariant(value.getNbtId());
//	}
//
//	@Override
//	public void setNPCHelmet(ItemStack value) {
//		((ArmorEquipable)this).setHelmet(value);
//	}
//
//	@Override
//	public void setNPCChestplate(ItemStack value) {
//		((ArmorEquipable)this).setChestplate(value);
//	}
//
//	@Override
//	public void setNPCLeggings(ItemStack value) {
//		((ArmorEquipable)this).setLeggings(value);
//	}
//
//	@Override
//	public void setNPCBoots(ItemStack value) {
//		((ArmorEquipable)this).setBoots(value);
//	}
//
//	@Override
//	public void setNPCMainHand(ItemStack value) {
//		((ArmorEquipable)this).setItemInHand(HandTypes.MAIN_HAND, value);
//	}
//
//	@Override
//	public void setNPCOffHand(ItemStack value) {
//		((ArmorEquipable)this).setItemInHand(HandTypes.MAIN_HAND, value);
//	}

	// Start of Injections

	// TODO: Remove and replace with custom data
	@Inject(method = "writeEntityToNBT", at = @At("RETURN"))
	public void onWriteEntityToNBT(NBTTagCompound compound, CallbackInfo info) {
		if (this.data != null) { compound.setInteger("NPC_ID", this.data.getId()); }
	}

	// TODO: Remove and replace with custom data
	@Inject(method = "readEntityFromNBT", at = @At("RETURN"))
	public void onReadEntityFromNBT(NBTTagCompound compound, CallbackInfo info) {
		if (compound.hasKey("NPC_ID", 3)) {
			Optional<INPCData> file = NPCs.getInstance().getNpcStore().getData(compound.getInteger("NPC_ID"));
			if (file.isPresent()) {
				this.setNPCData(file.get());
			} else {
				this.setDead();
			}
		}
	}

	// TODO: Check for more efficient methods
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
