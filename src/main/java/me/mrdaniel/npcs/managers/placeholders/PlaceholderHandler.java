package me.mrdaniel.npcs.managers.placeholders;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

public interface PlaceholderHandler {

	String formatCommand(Player p, String txt);
	Text formatNPCMessage(Player p, String txt, String npc_name);
	Text formatChoiceMessage(Player p, Text choices);
}