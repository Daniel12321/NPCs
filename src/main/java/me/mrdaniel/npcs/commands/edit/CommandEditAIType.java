//package me.mrdaniel.npcs.commands.edit;
//
//import me.mrdaniel.npcs.NPCs;
//import me.mrdaniel.npcs.catalogtypes.aitype.AIType;
//import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyTypes;
//import me.mrdaniel.npcs.commands.PlayerCommand;
//import me.mrdaniel.npcs.gui.chat.npc.AIPage;
//import me.mrdaniel.npcs.gui.chat.npc.NPCChatMenu;
//import me.mrdaniel.npcs.io.INPCData;
//import me.mrdaniel.npcs.mixin.interfaces.NPCAble;
//import org.spongepowered.api.command.CommandException;
//import org.spongepowered.api.command.args.CommandContext;
//import org.spongepowered.api.command.spec.CommandSpec;
//import org.spongepowered.api.entity.living.player.Player;
//import org.spongepowered.api.text.Text;
//import org.spongepowered.api.text.format.TextColors;
//
//import java.util.Optional;
//
//public class CommandEditAIType extends PlayerCommand {
//
//    @Override
//    public void execute(Player p, CommandContext args) throws CommandException {
//        Optional<Integer> id = args.getOne("id");
//        NPCChatMenu menu = NPCs.getInstance().getSelectedManager().get(p.getUniqueId()).orElse(null);
//        AIType type = args.<AIType>getOne("aitype").get();
//
//        if (id.isPresent()) {
//            INPCData data = NPCs.getInstance().getNPCManager().getData(id.get()).orElseThrow(() -> new CommandException(Text.of(TextColors.RED, "No NPC with that ID exists!")));
//            NPCAble npc = NPCs.getInstance().getNPCManager().getNPC(data).orElseThrow(() -> new CommandException(Text.of(TextColors.RED, "NPC is not spawned in!")));
//            npc.setProperty(PropertyTypes.AI_PATTERN, type.newInstance(data.getProperty(PropertyTypes.TYPE).get())).save();
//        } else if (menu != null) {
//            NPCAble npc = NPCs.getInstance().getNPCManager().getNPC(selected).orElseThrow(() -> new CommandException(Text.of(TextColors.RED, "NPC is not spawned in!")));
//            npc.setProperty(PropertyTypes.AI_PATTERN, type.newInstance(selected.getProperty(PropertyTypes.TYPE).get())).save();
//            menu.setPage(AIPage.class).open();
//        } else {
//            throw new CommandException(Text.of(TextColors.RED, "You don't have an NPC selected!"));
//        }
//    }
//
//    public CommandSpec build() {
//        return CommandSpec.builder()
//                .description(Text.of(TextColors.GOLD, "NPCs | Set AI Type"))
//                .permission("npc.edit.ai.type")
//                .arguments(PropertyTypes.AI_PATTERN.getArgs())
//                .executor(this)
//                .build();
//    }
//}
