package me.mrdaniel.npcs.mixin;

import java.util.Optional;
import java.util.UUID;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.type.Career;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.data.type.HorseColor;
import org.spongepowered.api.data.type.HorseStyle;
import org.spongepowered.api.data.type.LlamaVariant;
import org.spongepowered.api.data.type.OcelotType;
import org.spongepowered.api.data.type.RabbitType;
import org.spongepowered.api.entity.ArmorEquipable;
import org.spongepowered.api.entity.living.Human;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColor;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.flowpowered.math.vector.Vector3d;

import me.mrdaniel.npcs.NPCs;
import me.mrdaniel.npcs.catalogtypes.options.OptionTypeRegistryModule;
import me.mrdaniel.npcs.catalogtypes.options.OptionTypes;
import me.mrdaniel.npcs.events.NPCEvent;
import me.mrdaniel.npcs.exceptions.NPCException;
import me.mrdaniel.npcs.interfaces.mixin.NPCAble;
import me.mrdaniel.npcs.io.NPCFile;
import me.mrdaniel.npcs.managers.ActionManager;
import me.mrdaniel.npcs.managers.GlowColorManager;
import me.mrdaniel.npcs.managers.MenuManager;
import me.mrdaniel.npcs.managers.NPCManager;
import me.mrdaniel.npcs.utils.Position;
import me.mrdaniel.npcs.utils.TextUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;

@Mixin(value = EntityLiving.class, priority = 1000)
public abstract class MixinEntityLiving extends EntityLivingBase implements NPCAble {

	private NPCFile file;
	private boolean looking;
	private boolean interact;

	public MixinEntityLiving(final net.minecraft.world.World worldIn) {
		super(worldIn);

		this.file = null;
		this.looking = false;
		this.interact = true;
	}

	@Shadow public abstract void faceEntity(Entity entityIn, float maxYawIncrease, float maxPitchIncrease);
	@Shadow public abstract void enablePersistence();
	@Shadow public abstract void setCanPickUpLoot(boolean canPickup);
	@Shadow public abstract void setNoAI(boolean disable);

	@Nullable
	@Override
	public NPCFile getNPCFile() {
		return this.file;
	}

	@Override
	public void setNPCFile(@Nonnull final NPCFile file) {
		this.file = file;

		this.enablePersistence();
		this.setCanPickUpLoot(false);
		this.setNoAI(true);

		super.setDropItemsWhenDead(false);
		super.setEntityInvulnerable(true);
		super.setNoGravity(true);
		super.setLocationAndAngles(file.getX(), file.getY(), file.getZ(), file.getYaw(), file.getPitch());

		file.getSkinUUID().ifPresent(skin -> this.setNPCSkin(skin));
		OptionTypeRegistryModule.getInstance().getMain().forEach(option -> option.writeToNPCFromFile(this, file));
		OptionTypeRegistryModule.getInstance().getArmor().forEach(option -> option.writeToNPCFromFile(this, file));
		OptionTypes.POSITION.writeToNPCFromFile(this, file);
	}

	@Override
	public void setNPCWorld(final World value) {
		super.setWorld((net.minecraft.world.World)value);
	}

	@Override
	public void setNPCPosition(final Position value) {
		super.setLocationAndAngles(value.getX(), value.getY(), value.getZ(), value.getYaw(), value.getPitch());
	}

	@Override
	public void setNPCName(final String value) {
		super.setCustomNameTag(TextUtils.toLegacy(value));
		super.setAlwaysRenderNameTag(true);
	}

	@Override
	public void setNPCSkin(final String value) {
		NPCs.getInstance().getGame().getServer().getGameProfileManager().get(value).thenAccept(gp -> Task.builder().delayTicks(0).execute(() -> {
			this.setNPCSkin(gp.getUniqueId());
			this.file.setSkinUUID(gp.getUniqueId()).save();
		}).submit(NPCs.getInstance()));
	}

	@Override
	public void setNPCSkin(final UUID value) {
		((Human)this).offer(Keys.SKIN_UNIQUE_ID, value);
	}

	@Override
	public void setNPCLooking(final boolean value) {
		this.looking = value;
	}

	@Override
	public void setNPCInteract(final boolean value) {
		this.interact = value;
	}

	@Override
	public void setNPCSilent(final boolean value) {
		super.setSilent(value);
	}

	@Override
	public void setNPCGlowing(final boolean value) {
		super.setGlowing(value);
	}

	@Override
	public void setNPCGlowColor(final TextColor value) {
		if (this.isGlowing()) { GlowColorManager.getInstance().setGlowColor((EntityLiving)(Object)this, value); }
	}

	@Override
	public void setNPCBaby(final boolean value) {
		if ((Object)this instanceof EntityZombie) { ((EntityZombie)(Object)this).setChild(value); }
		else { ((EntityAgeable)(Object)this).setGrowingAge(value ? Integer.MIN_VALUE : 0); }

		this.setLocationAndAngles(this.file.getX(), this.file.getY(), this.file.getZ(), this.file.getYaw(), this.file.getPitch());
	}

	@Override
	public void setNPCCharged(final boolean value) {
		((Living)this).offer(Keys.CREEPER_CHARGED, value);
	}

	@Override
	public void setNPCAngry(final boolean value) {
		((EntityWolf) (Object) this).setAngry(value);
	}

	@Override
	public void setNPCSize(final int value) {
		((Living)this).offer(Keys.SLIME_SIZE, value);
	}

	@Override
	public void setNPCSitting(final boolean value) {
		((Living)this).offer(Keys.IS_SITTING, value);
	}

	public void setNPCSaddle(final boolean value) {
		((Living)this).offer(Keys.PIG_SADDLE, value);
	}

	@Override
	public void setNPCCareer(final Career value) {
		((Living)this).offer(Keys.CAREER, value);
	}

	@Override
	public void setNPCHorseStyle(final HorseStyle value) {
		((Living)this).offer(Keys.HORSE_STYLE, value);
	}

	@Override
	public void setNPCHorseColor(final HorseColor value) {
		((Living)this).offer(Keys.HORSE_COLOR, value);
	}

	@Override
	public void setNPCLlamaVariant(final LlamaVariant value) {
		((Living)this).offer(Keys.LLAMA_VARIANT, value);
	}

	@Override
	public void setNPCCatType(final OcelotType value) {
		((Living)this).offer(Keys.OCELOT_TYPE, value);
	}

	public void setNPCRabbitType(@Nonnull final RabbitType value) {
		((Living)this).offer(Keys.RABBIT_TYPE, value);
	}

	@Override
	public void setNPCHelmet(final ItemStack value) {
		((ArmorEquipable)this).setHelmet(value);
	}

	@Override
	public void setNPCChestplate(final ItemStack value) {
		((ArmorEquipable)this).setChestplate(value);
	}

	@Override
	public void setNPCLeggings(final ItemStack value) {
		((ArmorEquipable)this).setLeggings(value);
	}

	@Override
	public void setNPCBoots(final ItemStack value) {
		((ArmorEquipable)this).setBoots(value);
	}

	@Override
	public void setNPCMainHand(final ItemStack value) {
		((ArmorEquipable)this).setItemInHand(HandTypes.MAIN_HAND, value);
	}

	@Override
	public void setNPCOffHand(final ItemStack value) {
		((ArmorEquipable)this).setItemInHand(HandTypes.MAIN_HAND, value);
	}

	@Inject(method = "writeEntityToNBT", at = @At("RETURN"), cancellable = false)
	public void onWriteEntityToNBT(final NBTTagCompound compound, final CallbackInfo info) {
		if (this.file != null) { compound.setInteger("NPC_ID", this.file.getId()); }
	}

	@Inject(method = "readEntityFromNBT", at = @At("RETURN"), cancellable = false)
	public void onReadEntityFromNBT(final NBTTagCompound compound, final CallbackInfo info) {
		if (compound.hasKey("NPC_ID", 3)) {
			Optional<NPCFile> file = NPCManager.getInstance().getFile(compound.getInteger("NPC_ID"));
			if (file.isPresent()) { this.setNPCFile(file.get()); }
			else { this.setDead(); }
		}
	}

	@Inject(method = "processInitialInteract", at = @At("RETURN"), cancellable = true)
	public void onProcessInitialInteract(final EntityPlayer player, final EnumHand hand, final CallbackInfoReturnable<Boolean> cir) {
		if (this.file != null) {
			if (hand == EnumHand.OFF_HAND) {
				Player spongePlayer = (Player) player;
				NPCAble npc = (NPCAble) this;
				if (player.isSneaking() && spongePlayer.hasPermission("npc.edit.select")) {
					MenuManager.getInstance().select(spongePlayer, npc);
				}
				else {
					if (!new NPCEvent.Interact(spongePlayer, npc).post()) {
						try { ActionManager.getInstance().execute(player.getUniqueID(), this.file); }
						catch (final NPCException exc) { spongePlayer.sendMessage(Text.of(TextColors.RED, "Failed to perform NPC actions: " + exc.getMessage())); }
					}
				}
			}
			if (cir.getReturnValue() != this.interact) {
				cir.setReturnValue(this.interact);
			}
		}
	}

	@Inject(method = "onEntityUpdate", at = @At("RETURN"), cancellable = false)
	public void onOnEntityUpdate(final CallbackInfo ci) {
		if (this.file != null && this.looking) {
			super.world.getEntities(EntityPlayer.class, p -> true)
			.stream().filter(p -> p.getDistanceToEntity(this) < 20.0 && p.getUniqueID() != super.entityUniqueID)
			.reduce((p1, p2) -> p1.getDistanceToEntity(this) < p2.getDistanceToEntity(this) ? p1 : p2)
			.ifPresent(p -> ((Living)this).lookAt(new Vector3d(p.posX, p.posY + p.getEyeHeight(), p.posZ)));
		}
	}

	@Inject(method = "canDespawn", at = @At("RETURN"), cancellable = true)
	public void onCanDespawn(final CallbackInfoReturnable<Boolean> cir) {
		if (this.file != null && cir.getReturnValue() != false) {
			cir.setReturnValue(false);
		}
	}

	@Inject(method = "canBeSteered", at = @At("RETURN"), cancellable = true)
	public void onCanBeRidden(final CallbackInfoReturnable<Boolean> cir) {
		if (this.file != null && cir.getReturnValue() != false) {
			cir.setReturnValue(false);
		}
	}
}
