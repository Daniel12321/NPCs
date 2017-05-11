package me.mrdaniel.npcs.managers;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

public interface PlaceHolderManager {

	String formatCommand(Player p, String txt);
	Text formatNPCMessage(Player p, String txt, String npc_name);
	Text formatChoiceMessage(Player p, Text choices);
}