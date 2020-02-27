package me.mrdaniel.npcs.catalogtypes.propertytype.types;

import com.google.common.reflect.TypeToken;
import me.mrdaniel.npcs.catalogtypes.color.ColorType;
import me.mrdaniel.npcs.catalogtypes.npctype.NPCType;
import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyType;
import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyTypes;
import me.mrdaniel.npcs.mixin.interfaces.NPCAble;
import net.minecraft.entity.EntityLiving;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.text.TextFormatting;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.entity.living.Human;
import org.spongepowered.api.text.Text;

public class PropertyGlowColor extends PropertyType<ColorType> {

	public PropertyGlowColor() {
		super("GlowColor", "glowcolor", new Object[]{"glow", "color"}, GenericArguments.catalogedElement(Text.of("glowcolor"), ColorType.class));
	}

	@Override
	public TypeToken<ColorType> getTypeToken() {
		return TypeToken.of(ColorType.class);
	}

	@Override
	public boolean isSupported(final NPCAble npc) {
		return true;
	}

	@Override
	public boolean isSupported(NPCType type) {
		return true;
	}

	@Override
	public void apply(NPCAble npc, ColorType value) {
		if (npc.getData().getProperty(PropertyTypes.GLOWING).orElse(false)) {
			EntityLiving el = (EntityLiving) npc;

			String teamName = "NPC_" + value.getName();
			String npcName = (npc instanceof Human) ? el.getCustomNameTag() : el.getCachedUniqueIdString();
			Scoreboard board =  el.world.getScoreboard();
			ScorePlayerTeam team = board.getTeam(teamName);
			if (team == null) {
				team = board.createTeam(teamName);
			}

			board.addPlayerToTeam(npcName, teamName);
			team.setColor(value.getColor());
			team.setPrefix(value.getColor().toString());
			team.setSuffix(TextFormatting.RESET.toString());
		}
	}
}
