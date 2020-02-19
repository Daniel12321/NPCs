package me.mrdaniel.npcs.commands.main;

import me.mrdaniel.npcs.exceptions.NPCException;
import me.mrdaniel.npcs.io.NPCFile;
import me.mrdaniel.npcs.managers.MenuManager;
import me.mrdaniel.npcs.managers.NPCManager;
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
		this.sendNPCList(src);
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
		NPCManager.getInstance().getFiles().forEach(file -> src.sendMessage(getNPCText(file)));
		src.sendMessage(Text.of(TextColors.YELLOW, "--------------------------------------------------"));
	}

	private static Text getNPCText(NPCFile file) {
		Text.Builder b = Text.builder().append(Text.of(TextColors.BLUE, file.getId(), ": "), Text.of(TextColors.GOLD, "Location=", TextColors.RED, file.getWorldName(), ", ", (int)file.getX(), ", ", (int)file.getY(), ", ", (int)file.getZ()));
		file.getType().ifPresent(type -> b.append(Text.of(TextColors.GOLD, " Type=", TextColors.RED, type.getName())));
		file.getName().ifPresent(name -> b.append(Text.of(TextColors.GOLD, " Name=", TextColors.RED), TextUtils.toText(name)));
		return b.onHover(TextActions.showText(Text.of(TextColors.YELLOW, "Info"))).onClick(TextActions.executeCallback(src -> sendNPCInfo((Player)src, file))).build();
	}

	private static void sendNPCInfo(Player p, NPCFile file) {
		boolean worldLoaded = file.getWorld().isPresent();
		boolean npcLoaded = worldLoaded ? NPCManager.getInstance().getNPC(file).isPresent() : false;

		p.sendMessage(Text.EMPTY);
		p.sendMessage(Text.of(Text.of(TextColors.YELLOW, "---------------=====[ ", TextColors.RED, "NPC Info", TextColors.YELLOW, " ]=====---------------")));
		p.sendMessage(Text.of(TextColors.GOLD, "World Loaded: ", worldLoaded ? TextColors.DARK_GREEN : TextColors.RED, worldLoaded ? "True" : "False"));
		p.sendMessage(Text.of(TextColors.GOLD, "NPC Loaded: ", npcLoaded ? TextColors.DARK_GREEN : TextColors.RED, npcLoaded ? "True" : "False"));
		p.sendMessage(Text.EMPTY);

		Text.Builder buttons = Text.builder();
		if (worldLoaded) {
			buttons.append(Text.of("  "), Text.builder().append(Text.of(TextColors.GOLD, "[Select]")).onHover(TextActions.showText(Text.of(TextColors.GOLD, "Select"))).onClick(TextActions.executeCallback(src -> select((Player)src, file))).build());
			buttons.append(Text.of("  "), Text.builder().append(Text.of(TextColors.RED, "[Remove]")).onHover(TextActions.showText(Text.of(TextColors.RED, "Remove"))).onClick(TextActions.suggestCommand("/npc remove " + Integer.toString(file.getId()))).build());
		}

		p.sendMessage(buttons.build());
		p.sendMessage(Text.EMPTY);
		p.sendMessage(Text.builder().append(Text.of(TextColors.YELLOW, "----------------====[ "), Text.builder().append(Text.of(TextColors.RED, "Back")).onHover(TextActions.showText(Text.of(TextColors.RED, "Back"))).onClick(TextActions.executeCallback(src -> sendNPCList(src))).build(), Text.of(TextColors.YELLOW, " ]====----------------")).build());
	}

	private static void select(Player p, NPCFile file) {
		try {
			MenuManager.getInstance().select(p, file);
		} catch (final NPCException exc) {
			p.sendMessage(Text.of(TextColors.RED, exc.getMessage()));
		}
	}
}
