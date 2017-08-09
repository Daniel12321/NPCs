package me.mrdaniel.npcs.managers;

import javax.annotation.Nonnull;

import org.spongepowered.api.entity.living.Human;
import org.spongepowered.api.text.format.TextColor;
import org.spongepowered.api.text.format.TextColors;

import lombok.Getter;
import net.minecraft.entity.EntityLiving;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.text.TextFormatting;

public class GlowColorManager {

	@Getter private static GlowColorManager instance = new GlowColorManager();

	public void setGlowColor(@Nonnull final EntityLiving npc, @Nonnull final TextColor color) {
		TextFormatting form = this.getFormatting(color);
		String teamName = "NPC_" + color.getName();
		String npcName = (npc instanceof Human) ? npc.getCustomNameTag() : npc.getCachedUniqueIdString();
		Scoreboard board =  npc.getEntityWorld().getScoreboard();
		ScorePlayerTeam team = board.getTeam(teamName);
		if (team == null) { team = board.createTeam(teamName); }

		board.addPlayerToTeam(npcName, teamName);
		team.setColor(form);
		team.setPrefix(form.toString());
		team.setSuffix(TextFormatting.RESET.toString());
	}

	private TextFormatting getFormatting(@Nonnull final TextColor color) {
		if (color == TextColors.BLACK) { return TextFormatting.BLACK; }
		if (color == TextColors.DARK_BLUE) { return TextFormatting.DARK_BLUE; }
		if (color == TextColors.DARK_GREEN) { return TextFormatting.DARK_GREEN; }
		if (color == TextColors.DARK_AQUA) { return TextFormatting.DARK_AQUA; }
		if (color == TextColors.DARK_RED) { return TextFormatting.DARK_RED; }
		if (color == TextColors.DARK_PURPLE) { return TextFormatting.DARK_PURPLE; }
		if (color == TextColors.GOLD) { return TextFormatting.GOLD; }
		if (color == TextColors.GRAY) { return TextFormatting.GRAY; }
		if (color == TextColors.DARK_GRAY) { return TextFormatting.DARK_GRAY; }
		if (color == TextColors.BLUE) { return TextFormatting.BLUE; }
		if (color == TextColors.GREEN) { return TextFormatting.GREEN; }
		if (color == TextColors.AQUA) { return TextFormatting.AQUA; }
		if (color == TextColors.RED) { return TextFormatting.RED; }
		if (color == TextColors.LIGHT_PURPLE) { return TextFormatting.LIGHT_PURPLE; }
		if (color == TextColors.YELLOW) { return TextFormatting.YELLOW; }
		return TextFormatting.WHITE;
	}
}