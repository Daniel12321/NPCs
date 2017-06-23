package me.mrdaniel.npcs.managers;

import javax.annotation.Nonnull;

import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.Human;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.scoreboard.Team;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColor;

import me.mrdaniel.npcs.NPCObject;
import me.mrdaniel.npcs.NPCs;
import me.mrdaniel.npcs.utils.TextUtils;

public class GlowColorManager extends NPCObject {

	public GlowColorManager(@Nonnull final NPCs npcs) {
		super(npcs);
	}

	private void createTeam(@Nonnull final String name, @Nonnull final Living npc) {
		if (!super.getServer().getServerScoreboard().get().getTeam(name).isPresent()) {
			super.getServer().getServerScoreboard().get().registerTeam(Team.builder().name(name).displayName(Text.of(name)).build());
			this.addMember(name, npc);
		}
	}

	public void setGlowColor(@Nonnull final Living npc, final int id, @Nonnull final TextColor color) {
		String name = "npc_" + id;

		this.createTeam(name, npc);
		this.setColor(name, color.getName());
	}

	private void addMember(@Nonnull final String name, @Nonnull final Living npc) {
		String id = npc instanceof Human ? npc.get(Keys.DISPLAY_NAME).map(txt -> TextUtils.toLegacy(txt)).orElse("Steve") : npc.getUniqueId().toString();
		this.execute("scoreboard teams join " + name + " " + id);
	}

	private void setColor(@Nonnull final String name, @Nonnull final String color) {
		this.execute("scoreboard teams option " + name + " color " + color);
	}

	private void execute(@Nonnull final String command) {
		super.getGame().getCommandManager().process(super.getServer().getConsole(), command);
	}
}