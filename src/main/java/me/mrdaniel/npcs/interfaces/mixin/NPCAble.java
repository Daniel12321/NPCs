package me.mrdaniel.npcs.interfaces.mixin;

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
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.world.World;

import javax.annotation.Nullable;
import java.util.UUID;

public interface NPCAble {

	@Nullable NPCFile getNPCFile();
	void setNPCFile(NPCFile file);

	default void selectNPC(Player p) {
		MenuManager.getInstance().select(p, this);
	}

	void setNPCWorld(World value);
	void setNPCPosition(Position value);

	void setNPCName(String value);
	void setNPCSkin(String value);
	void setNPCSkin(UUID value);
	void setNPCLooking(boolean value);
	boolean isNPCLooking();
	void setNPCInteract(boolean value);
	boolean canNPCInteract();
	void setNPCSilent(boolean value);
	void setNPCGlowing(boolean value);
	void setNPCGlowColor(GlowColor value);

	void setNPCBaby(boolean value);
	void setNPCCharged(boolean value);
	void setNPCAngry(boolean value);
	void setNPCSize(int value);
	void setNPCSitting(boolean value);
	void setNPCSaddle(boolean value);
	void setNPCHanging(boolean value);
	void setNPCPumpkin(boolean value);
	void setNPCCareer(Career value);
	void setNPCHorsePattern(HorsePattern value);
	void setNPCHorseColor(HorseColor value);
	void setNPCLlamaType(LlamaType value);
	void setNPCCatType(CatType value);
	void setNPCRabbitType(RabbitType value);
	void setNPCParrotType(ParrotType value);

	void setNPCHelmet(ItemStack value);
	void setNPCChestplate(ItemStack value);
	void setNPCLeggings(ItemStack value);
	void setNPCBoots(ItemStack value);
	void setNPCMainHand(ItemStack value);
	void setNPCOffHand(ItemStack value);
}
