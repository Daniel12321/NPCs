package me.mrdaniel.npcs.mixin;

import java.util.Optional;
import java.util.UUID;

import javax.annotation.Nullable;

import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.entity.ArmorEquipable;
import org.spongepowered.api.entity.living.Human;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.flowpowered.math.vector.Vector3d;

import me.mrdaniel.npcs.NPCs;
import me.mrdaniel.npcs.catalogtypes.career.Career;
import me.mrdaniel.npcs.catalogtypes.cattype.CatType;
import me.mrdaniel.npcs.catalogtypes.glowcolor.GlowColor;
import me.mrdaniel.npcs.catalogtypes.horsecolor.HorseColor;
import me.mrdaniel.npcs.catalogtypes.horsecolor.HorseColors;
import me.mrdaniel.npcs.catalogtypes.horsepattern.HorsePattern;
import me.mrdaniel.npcs.catalogtypes.horsepattern.HorsePatterns;
import me.mrdaniel.npcs.catalogtypes.llamatype.LlamaType;
import me.mrdaniel.npcs.catalogtypes.optiontype.OptionTypeRegistryModule;
import me.mrdaniel.npcs.catalogtypes.optiontype.OptionTypes;
import me.mrdaniel.npcs.catalogtypes.parrottype.ParrotType;
import me.mrdaniel.npcs.catalogtypes.rabbittype.RabbitType;
import me.mrdaniel.npcs.interfaces.mixin.IMixinEntityVillager;
import me.mrdaniel.npcs.interfaces.mixin.NPCAble;
import me.mrdaniel.npcs.io.NPCFile;
import me.mrdaniel.npcs.managers.NPCManager;
import me.mrdaniel.npcs.utils.Position;
import me.mrdaniel.npcs.utils.TextUtils;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntitySnowman;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityLlama;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.passive.EntityParrot;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntityRabbit;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

@Mixin(value = EntityLiving.class, priority = 10)
public abstract class MixinEntityLiving extends EntityLivingBase implements NPCAble {

	private NPCFile file;
	private boolean looking;
	private boolean interact;

	public MixinEntityLiving(final World worldIn) {
		super(worldIn);

		this.file = null;
		this.looking = false;
		this.interact = true;
	}

	@Shadow public abstract void enablePersistence();
	@Shadow public abstract void setCanPickUpLoot(boolean canPickup);
	@Shadow public abstract void setNoAI(boolean disable);

	@Nullable
	@Override
	public NPCFile getNPCFile() {
		return this.file;
	}

	@Override
	public void setNPCFile(final NPCFile file) {
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
	public void setNPCWorld(final org.spongepowered.api.world.World value) {
		super.setWorld((World)value);
	}

	@Override
	public void setNPCPosition(final Position value) {
		super.setLocationAndAngles(value.getX(), value.getY(), value.getZ(), value.getYaw(), value.getPitch());
	}

	@Override
	public void setNPCName(final String value) {
		((Living)this).offer(Keys.DISPLAY_NAME, TextUtils.toText(value));
		super.setAlwaysRenderNameTag(true);

		if (this.file.getGlowing()) { this.file.getGlowColor().ifPresent(color -> this.setNPCGlowColor(color)); }
	}

	@Override
	public void setNPCSkin(final String value) {
		new Thread(() -> {
			NPCs.getInstance().getGame().getServer().getGameProfileManager().get(value).thenAccept(gp -> Task.builder().delayTicks(0).execute(() -> {
				this.setNPCSkin(gp.getUniqueId());
				this.file.setSkinUUID(gp.getUniqueId()).save();
			}).submit(NPCs.getInstance()));
		}).start();
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
	public boolean isNPCLooking() {
		return this.looking;
	}

	@Override
	public void setNPCInteract(final boolean value) {
		this.interact = value;
	}

	@Override
	public boolean canNPCInteract() {
		return this.interact;
	}

	@Override
	public void setNPCSilent(final boolean value) {
		super.setSilent(value);
	}

	@Override
	public void setNPCGlowing(final boolean value) {
		super.setGlowing(value);

		if (value) { this.file.getGlowColor().ifPresent(color -> this.setNPCGlowColor(color)); }
	}

	@Override
	public void setNPCGlowColor(final GlowColor value) {
		if (this.isGlowing()) {
			String teamName = "NPC_" + value.getName();
			String npcName = (this instanceof Human) ? super.getCustomNameTag() : super.getCachedUniqueIdString();
			Scoreboard board =  super.world.getScoreboard();
			ScorePlayerTeam team = board.getTeam(teamName);
			if (team == null) { team = board.createTeam(teamName); }

			board.addPlayerToTeam(npcName, teamName);
			team.setColor(value.getColor());
			team.setPrefix(value.getColor().toString());
			team.setSuffix(TextFormatting.RESET.toString());
		}
	}

	@Override
	public void setNPCBaby(final boolean value) {
		if ((Object)this instanceof EntityZombie) { ((EntityZombie)(Object)this).setChild(value); }
		else { ((EntityAgeable)(Object)this).setGrowingAge(value ? Integer.MIN_VALUE : 0); }

		super.setLocationAndAngles(this.file.getX(), this.file.getY(), this.file.getZ(), this.file.getYaw(), this.file.getPitch());
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
		((EntityTameable)(Object)this).setSitting(value);
	}

	@Override
	public void setNPCHanging(final boolean value) {
		((EntityBat)(Object)this).setIsBatHanging(value);
	}

	@Override
	public void setNPCPumpkin(final boolean value) {
		((EntitySnowman)(Object)this).setPumpkinEquipped(value);
	}

	@Override
	public void setNPCSaddle(final boolean value) {
		((EntityPig)(Object)this).setSaddled(value);
	}

	@Override
	public void setNPCCareer(final Career value) {
		((EntityVillager)(Object)this).setProfession(value.getProfessionId());
		((IMixinEntityVillager)this).setCareerId(value.getCareerId());
	}

	@Override
	public void setNPCHorsePattern(final HorsePattern value) {
		((EntityHorse)(Object)this).setHorseVariant(value.getNbtId() + this.file.getHorseColor().orElse(HorseColors.BROWN).getNbtId());
	}

	@Override
	public void setNPCHorseColor(final HorseColor value) {
		((EntityHorse)(Object)this).setHorseVariant(value.getNbtId() + this.file.getHorsePattern().orElse(HorsePatterns.NONE).getNbtId());
	}

	@Override
	public void setNPCLlamaType(final LlamaType value) {
		((EntityLlama)(Object)this).setVariant(value.getNbtId());
	}

	@Override
	public void setNPCCatType(final CatType value) {
		((EntityOcelot)(Object)this).setTameSkin(value.getNbtId());
	}

	@Override
	public void setNPCRabbitType(final RabbitType value) {
		((EntityRabbit)(Object)this).setRabbitType(value.getNbtId());
	}

	@Override
	public void setNPCParrotType(final ParrotType value) {
		((EntityParrot)(Object)this).setVariant(value.getNbtId());
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

	// Start of Injections

	@Inject(method = "writeEntityToNBT", at = @At("RETURN"))
	public void onWriteEntityToNBT(final NBTTagCompound compound, final CallbackInfo info) {
		if (this.file != null) { compound.setInteger("NPC_ID", this.file.getId()); }
	}

	@Inject(method = "readEntityFromNBT", at = @At("RETURN"))
	public void onReadEntityFromNBT(final NBTTagCompound compound, final CallbackInfo info) {
		if (compound.hasKey("NPC_ID", 3)) {
			Optional<NPCFile> file = NPCManager.getInstance().getFile(compound.getInteger("NPC_ID"));
			if (file.isPresent()) { this.setNPCFile(file.get()); }
			else { this.setDead(); }
		}
	}

	@Inject(method = "onEntityUpdate", at = @At("RETURN"))
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