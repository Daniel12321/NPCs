package me.mrdaniel.npcs.commands.main;

import me.mrdaniel.npcs.NPCs;
import me.mrdaniel.npcs.catalogtypes.npctype.NPCType;
import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyTypes;
import me.mrdaniel.npcs.commands.PlayerCommand;
import me.mrdaniel.npcs.interfaces.mixin.NPCAble;
import me.mrdaniel.npcs.io.INPCData;
import me.mrdaniel.npcs.menu.chat.npc.PropertiesChatMenu;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import java.util.Optional;

public class CommandEditType extends PlayerCommand {

    @Override
    public void execute(Player p, CommandContext args) throws CommandException {
        Optional<Integer> id = args.getOne("id");
        Optional<INPCData> selected = NPCs.getInstance().getSelectedManager().get(p.getUniqueId());
        NPCType type = args.<NPCType>getOne("type").get();

        if (id.isPresent()) {
            INPCData data = NPCs.getInstance().getNPCManager().getData(id.get()).orElseThrow(() -> new CommandException(Text.of(TextColors.RED, "No NPC with that ID exists!")));
            NPCAble npc = NPCs.getInstance().getNPCManager().getNPC(data).orElseThrow(() -> new CommandException(Text.of(TextColors.RED, "NPC is not spawned in!")));
            npc.setNPCProperty(PropertyTypes.TYPE, type).saveNPC();
        } else if (selected.isPresent()) {
            NPCAble npc = NPCs.getInstance().getNPCManager().getNPC(selected.get()).orElseThrow(() -> new CommandException(Text.of(TextColors.RED, "NPC is not spawned in!")));
            npc.setNPCProperty(PropertyTypes.TYPE, type).saveNPC();

            Task.builder().delayTicks(5).execute(() -> {
                NPCs.getInstance().getNPCManager().getNPC(npc.getNPCData()).ifPresent(newNPC -> new PropertiesChatMenu(newNPC).send(p));
            }).submit(NPCs.getInstance());
        } else {
            throw new CommandException(Text.of(TextColors.RED, "You don't have an NPC selected!"));
        }
    }

    public CommandSpec build() {
        return CommandSpec.builder()
                .description(Text.of(TextColors.GOLD, "NPCs | Set Type"))
                .permission("npc.edit.type")
                .arguments(PropertyTypes.TYPE.getArgs())
                .executor(this)
                .build();
    }
}
