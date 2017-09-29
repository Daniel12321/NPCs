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

	@Nullable NPCFile getNPCFile();
	void setNPCFile(@Nonnull final NPCFile file);

	default void selectNPC(@Nonnull final Player p) { MenuManager.getInstance().select(p, this); }

	void setNPCWorld(@Nonnull final World value);
	void setNPCPosition(@Nonnull final Position value);

	void setNPCName(@Nonnull final String value);
	void setNPCSkin(@Nonnull final String value);
	void setNPCSkin(@Nonnull final UUID value);
	void setNPCLooking(final boolean value);
	void setNPCInteract(final boolean value);
	void setNPCSilent(final boolean value);
	void setNPCGlowing(final boolean value);
	void setNPCGlowColor(@Nonnull final GlowColor value);

	void setNPCBaby(final boolean value);
	void setNPCCharged(final boolean value);
	void setNPCAngry(final boolean value);
	void setNPCSize(final int value);
	void setNPCSitting(final boolean value);
	void setNPCSaddle(final boolean value);
	void setNPCHanging(final boolean value);
	void setNPCPumpkin(final boolean value);
	void setNPCCareer(@Nonnull final Career value);
	void setNPCHorsePattern(@Nonnull final HorsePattern value);
	void setNPCHorseColor(@Nonnull final HorseColor value);
	void setNPCLlamaType(@Nonnull final LlamaType value);
	void setNPCCatType(@Nonnull final CatType value);
	void setNPCRabbitType(@Nonnull final RabbitType value);
	void setNPCParrotType(@Nonnull final ParrotType value);

	void setNPCHelmet(@Nonnull final ItemStack value);
	void setNPCChestplate(@Nonnull final ItemStack value);
	void setNPCLeggings(@Nonnull final ItemStack value);
	void setNPCBoots(@Nonnull final ItemStack value);
	void setNPCMainHand(@Nonnull final ItemStack value);
	void setNPCOffHand(@Nonnull final ItemStack value);
}