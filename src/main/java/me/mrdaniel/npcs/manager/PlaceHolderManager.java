package me.mrdaniel.npcs.manager;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

public interface PlaceHolderManager {

	String formatCMD(Player p, String txt);
	Text formatMSG(Player p, String txt, String npc_name);
}