package me.mrdaniel.npcs.interfaces.mixin;

import java.util.UUID;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.world.World;

import me.mrdaniel.npcs.catalogtypes.career.Career;
import me.mrdaniel.npcs.catalogtypes.cattype.CatType;
import me.mrdaniel.npcs.catalogtypes.glowcolor.GlowColor;
import me.mrdaniel.npcs.catalogtypes.horsecolor.HorseColor;
import me.mrdaniel.npcs.catalogtypes.horsepattern.HorsePattern;
import me.mrdaniel.npcs.catalogtypes.llamatype.LlamaType;
import me.mrdaniel.npcs.catalogtypes.parrottype.ParrotType;
import me.mrdaniel.npcs.catalogtypes.rabbittype.RabbitType;
import me.mrdaniel.npcs.io.NPCFile;
import me.mrdaniel.npcs.managers.MenuManager;
import me.mrdaniel.npcs.utils.Position;

public interface NPCAble {

	@Nullable public NPCFile getNPCFile();
	public void setNPCFile(@Nonnull final NPCFile file);

	default void selectNPC(@Nonnull final Player p) { MenuManager.getInstance().select(p, this); }

	public void setNPCWorld(@Nonnull final World value);
	public void setNPCPosition(@Nonnull final Position value);

	public void setNPCName(@Nonnull final String value);
	public void setNPCSkin(@Nonnull final String value);
	public void setNPCSkin(@Nonnull final UUID value);
	public void setNPCLooking(final boolean value);
	public void setNPCInteract(final boolean value);
	public void setNPCSilent(final boolean value);
	public void setNPCGlowing(final boolean value);
	public void setNPCGlowColor(@Nonnull final GlowColor value);

	public void setNPCBaby(final boolean value);
	public void setNPCCharged(final boolean value);
	public void setNPCAngry(final boolean value);
	public void setNPCSize(final int value);
	public void setNPCSitting(final boolean value);
	public void setNPCSaddle(final boolean value);
	public void setNPCHanging(final boolean value);
	public void setNPCPumpkin(final boolean value);
	public void setNPCCareer(@Nonnull final Career value);
	public void setNPCHorsePattern(@Nonnull final HorsePattern value);
	public void setNPCHorseColor(@Nonnull final HorseColor value);
	public void setNPCLlamaType(@Nonnull final LlamaType value);
	public void setNPCCatType(@Nonnull final CatType value);
	public void setNPCRabbitType(@Nonnull final RabbitType value);
	public void setNPCParrotType(@Nonnull final ParrotType value);

	public void setNPCHelmet(@Nonnull final ItemStack value);
	public void setNPCChestplate(@Nonnull final ItemStack value);
	public void setNPCLeggings(@Nonnull final ItemStack value);
	public void setNPCBoots(@Nonnull final ItemStack value);
	public void setNPCMainHand(@Nonnull final ItemStack value);
	public void setNPCOffHand(@Nonnull final ItemStack value);
}