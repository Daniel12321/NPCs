package me.mrdaniel.npcs.commands.main;

import me.mrdaniel.npcs.NPCs;
import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyTypes;
import me.mrdaniel.npcs.exceptions.NPCException;
import me.mrdaniel.npcs.io.INPCData;
import me.mrdaniel.npcs.utils.Position;
import me.mrdaniel.npcs.utils.TextUtils;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColors;

public class CommandList implements CommandExecutor {

	public CommandList() {}

	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
		sendNPCList(src);
		return CommandResult.success();
	}

	public CommandSpec build() {
		return CommandSpec.builder()
				.description(Text.of(TextColors.GOLD, "NPCs | List"))
				.permission("npc.list")
				.executor(this)
				.build();
	}

	//TODO: Remove static stuff
	public static void sendNPCList(CommandSource src) {
		src.sendMessage(Text.EMPTY);
		src.sendMessage(Text.of(Text.of(TextColors.YELLOW, "---------------=====[ ", TextColors.RED, "NPC List", TextColors.YELLOW, " ]=====---------------")));
		NPCs.getInstance().getNpcStore().getAllNPCs().forEach(data -> src.sendMessage(getNPCText(data)));
		src.sendMessage(Text.of(TextColors.YELLOW, "--------------------------------------------------"));
	}

	private static Text getNPCText(INPCData data) {
		Position pos = data.getProperty(PropertyTypes.POSITION).get();
		Text.Builder b = Text.builder().append(Text.of(TextColors.BLUE, data.getId(), ": "), Text.of(TextColors.GOLD, "Location=", TextColors.RED, data.getProperty(PropertyTypes.WORLD), ", ", (int)pos.getX(), ", ", (int)pos.getY(), ", ", (int)pos.getZ()));
		data.getProperty(PropertyTypes.TYPE).ifPresent(type -> b.append(Text.of(TextColors.GOLD, " Type=", TextColors.RED, type.getName())));
		data.getProperty(PropertyTypes.NAME).ifPresent(name -> b.append(Text.of(TextColors.GOLD, " Name=", TextColors.RED), TextUtils.toText(name)));
		return b.onHover(TextActions.showText(Text.of(TextColors.YELLOW, "Info"))).onClick(TextActions.executeCallback(src -> sendNPCInfo((Player)src, data))).build();
	}

	private static void sendNPCInfo(Player p, INPCData data) {
		boolean worldLoaded = data.getProperty(PropertyTypes.WORLD).isPresent();
		boolean npcLoaded = worldLoaded ? NPCs.getInstance().getNpcStore().getNPC(data).isPresent() : false;

		p.sendMessage(Text.EMPTY);
		p.sendMessage(Text.of(Text.of(TextColors.YELLOW, "---------------=====[ ", TextColors.RED, "NPC Info", TextColors.YELLOW, " ]=====---------------")));
		p.sendMessage(Text.of(TextColors.GOLD, "World Loaded: ", worldLoaded ? TextColors.DARK_GREEN : TextColors.RED, worldLoaded ? "True" : "False"));
		p.sendMessage(Text.of(TextColors.GOLD, "NPC Loaded: ", npcLoaded ? TextColors.DARK_GREEN : TextColors.RED, npcLoaded ? "True" : "False"));
		p.sendMessage(Text.EMPTY);

		Text.Builder buttons = Text.builder();
		if (worldLoaded) {
			buttons.append(Text.of("  "), Text.builder().append(Text.of(TextColors.GOLD, "[Select]")).onHover(TextActions.showText(Text.of(TextColors.GOLD, "Select"))).onClick(TextActions.executeCallback(src -> select((Player)src, data))).build());
			buttons.append(Text.of("  "), Text.builder().append(Text.of(TextColors.RED, "[Remove]")).onHover(TextActions.showText(Text.of(TextColors.RED, "Remove"))).onClick(TextActions.suggestCommand("/npc remove " + Integer.toString(data.getId()))).build());
		}

		p.sendMessage(buttons.build());
		p.sendMessage(Text.EMPTY);
		p.sendMessage(Text.builder().append(Text.of(TextColors.YELLOW, "----------------====[ "), Text.builder().append(Text.of(TextColors.RED, "Back")).onHover(TextActions.showText(Text.of(TextColors.RED, "Back"))).onClick(TextActions.executeCallback(src -> sendNPCList(src))).build(), Text.of(TextColors.YELLOW, " ]====----------------")).build());
	}

	private static void select(Player p, INPCData data) {
		try {
			NPCs.getInstance().getMenuManager().select(p, data);
		} catch (final NPCException exc) {
			p.sendMessage(Text.of(TextColors.RED, exc.getMessage()));
		}
	}
}
