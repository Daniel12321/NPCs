package me.mrdaniel.npcs.managers;

import javax.annotation.Nonnull;

import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.scoreboard.Team;
import org.spongepowered.api.scoreboard.Visibilities;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColor;
import org.spongepowered.api.text.format.TextColors;

import me.mrdaniel.npcs.NPCObject;
import me.mrdaniel.npcs.NPCs;

public class GlowColorManager extends NPCObject {

	public GlowColorManager(@Nonnull final NPCs npcs) {
		super(npcs);
	}

	@Nonnull
	private Team getTeam(@Nonnull final TextColor color) {
		Team team = super.getServer().getServerScoreboard().get().getTeam("NPC_" + color.getName()).orElse(null);
		if (team == null) {
			team = Team.builder().name("NPC_" + color.getName())
					.color(color)
					.allowFriendlyFire(false)
					.canSeeFriendlyInvisibles(false)
					.deathTextVisibility(Visibilities.NEVER)
					.nameTagVisibility(Visibilities.NEVER)
					.build();
			super.getServer().getServerScoreboard().get().registerTeam(team);
		}
		return team;
	}

	public void setGlowColor(@Nonnull final Entity e, @Nonnull final TextColor color) {
		super.getServer().getServerScoreboard().get().getTeams().forEach(team -> team.removeMember(Text.of(e.getUniqueId())));

		if (color != TextColors.WHITE) {
			this.getTeam(color).addMember(Text.of(e.getUniqueId().toString()));
		}
	}
}