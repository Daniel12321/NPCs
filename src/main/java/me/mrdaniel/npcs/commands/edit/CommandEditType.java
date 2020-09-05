//package me.mrdaniel.npcs.commands.edit;
//
//import me.mrdaniel.npcs.NPCs;
//import me.mrdaniel.npcs.catalogtypes.npctype.NPCType;
//import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyType;
//import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyTypes;
//import me.mrdaniel.npcs.commands.NPCCommand;
//import me.mrdaniel.npcs.events.NPCEditEvent;
//import me.mrdaniel.npcs.gui.chat.npc.PropertiesPage;
//import me.mrdaniel.npcs.io.INPCData;
//import me.mrdaniel.npcs.mixin.interfaces.NPCAble;
//import org.spongepowered.api.Sponge;
//import org.spongepowered.api.command.CommandException;
//import org.spongepowered.api.command.args.CommandContext;
//import org.spongepowered.api.command.spec.CommandSpec;
//import org.spongepowered.api.entity.living.player.Player;
//import org.spongepowered.api.scheduler.Task;
//import org.spongepowered.api.text.Text;
//import org.spongepowered.api.text.format.TextColors;
//
//import javax.annotation.Nullable;
//
//public class CommandEditType extends NPCCommand { // extends PlayerCommand {
//
//    public CommandEditType() {
//        super(PropertiesPage.class, false);
//    }
//
//    @Override
//    public boolean execute(Player player, INPCData data, @Nullable NPCAble npc, CommandContext args) throws CommandException {
//        if (Sponge.getEventManager().post(new NPCEditEvent(player, data, npc))) {
//            throw new CommandException(Text.of(TextColors.RED, "Event was cancelled!"));
//        }
//
//        PropertyType<NPCType> property = PropertyTypes.TYPE;
//        NPCType type = args.<NPCType>getOne(property.getId()).orElseThrow(() -> new CommandException(Text.of(TextColors.RED, "Invalid value!")));
//        if (npc == null) {
//            data.setProperty(property, type);
//            return true;
//        } else {
//            npc.setProperty(property, type);
//            Task.builder().delayTicks(5).execute(() -> {
//                NPCs.getInstance().getNPCManager().getNPC(data).ifPresent(newNPC -> NPCs.getInstance().getSelectedManager().getOrCreate(player, data).open());
//            }).submit(NPCs.getInstance());
//        }
//
//        return false;
//    }
//
//    public CommandSpec build() {
//        return CommandSpec.builder()
//                .description(Text.of(TextColors.GOLD, "NPCs | Set Type"))
//                .permission("npc.edit.type")
//                .arguments(PropertyTypes.TYPE.getArgs())
//                .executor(this)
//                .build();
//    }
//}
