package me.mrdaniel.npcs.interfaces.mixin;

import java.util.UUID;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.spongepowered.api.data.type.Career;
import org.spongepowered.api.data.type.HorseColor;
import org.spongepowered.api.data.type.HorseStyle;
import org.spongepowered.api.data.type.LlamaVariant;
import org.spongepowered.api.data.type.OcelotType;
import org.spongepowered.api.data.type.RabbitType;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.format.TextColor;
import org.spongepowered.api.world.World;

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
	public void setNPCGlowColor(@Nonnull final TextColor value);

	public void setNPCBaby(final boolean value);
	public void setNPCCharged(final boolean value);
	public void setNPCAngry(final boolean value);
	public void setNPCSize(final int value);
	public void setNPCSitting(final boolean value);
	public void setNPCSaddle(final boolean value);
	public void setNPCCareer(@Nonnull final Career value);
	public void setNPCHorseStyle(@Nonnull final HorseStyle value);
	public void setNPCHorseColor(@Nonnull final HorseColor value);
	public void setNPCLlamaVariant(@Nonnull final LlamaVariant value);
	public void setNPCCatType(@Nonnull final OcelotType value);
	public void setNPCRabbitType(@Nonnull final RabbitType value);

	public void setNPCHelmet(@Nonnull final ItemStack value);
	public void setNPCChestplate(@Nonnull final ItemStack value);
	public void setNPCLeggings(@Nonnull final ItemStack value);
	public void setNPCBoots(@Nonnull final ItemStack value);
	public void setNPCMainHand(@Nonnull final ItemStack value);
	public void setNPCOffHand(@Nonnull final ItemStack value);
}