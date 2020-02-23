package me.mrdaniel.npcs.interfaces.mixin;

import me.mrdaniel.npcs.io.INPCData;

import javax.annotation.Nullable;

public interface NPCAble extends INPCData {

	@Nullable
    INPCData getNPCData();
	void setNPCData(INPCData data);
	void refreshNPC();

	void setNPCLooking(boolean value);
	boolean isNPCLooking();

//	default void selectNPC(Player p) {
//		NPCs.getInstance().getMenuManager().select(p, this);
//	}
//
//	void setNPCWorld(World value);
//	void setNPCPosition(Position value);
//
//	void setNPCName(String value);
//	void setNPCSkin(String value);
//	void setNPCSkin(UUID value);
//	void setNPCLooking(boolean value);
//	boolean isNPCLooking();
//	void setNPCInteract(boolean value);
//	boolean canNPCInteract();
//	void setNPCSilent(boolean value);
//	void setNPCGlowing(boolean value);
//	void setNPCGlowColor(GlowColor value);
//
//	void setNPCBaby(boolean value);
//	void setNPCCharged(boolean value);
//	void setNPCAngry(boolean value);
//	void setNPCSize(int value);
//	void setNPCSitting(boolean value);
//	void setNPCSaddle(boolean value);
//	void setNPCHanging(boolean value);
//	void setNPCPumpkin(boolean value);
//	void setNPCCareer(Career value);
//	void setNPCHorsePattern(HorsePattern value);
//	void setNPCHorseColor(HorseColor value);
//	void setNPCLlamaType(LlamaType value);
//	void setNPCCatType(CatType value);
//	void setNPCRabbitType(RabbitType value);
//	void setNPCParrotType(ParrotType value);
//
//	void setNPCHelmet(ItemStack value);
//	void setNPCChestplate(ItemStack value);
//	void setNPCLeggings(ItemStack value);
//	void setNPCBoots(ItemStack value);
//	void setNPCMainHand(ItemStack value);
//	void setNPCOffHand(ItemStack value);
}
