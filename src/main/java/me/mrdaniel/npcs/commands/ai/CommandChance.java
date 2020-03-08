package me.mrdaniel.npcs.commands.ai;

import me.mrdaniel.npcs.ai.pattern.AbstractAIPattern;
import me.mrdaniel.npcs.catalogtypes.aitype.AITypes;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

public class CommandChance extends AICommand {

    public CommandChance() {
        super(AITypes.ALL);
    }

    @Override
    public void execute(Player p, AbstractAIPattern ai, CommandContext args) throws CommandException {
        ai.setChance(args.<Integer>getOne("chance").get());
    }

    public CommandSpec build() {
        return CommandSpec.builder()
                .description(Text.of(TextColors.GOLD, "NPCs | Set Walk Chance"))
                .permission("npc.edit.ai.chance")
                .arguments(GenericArguments.integer(Text.of("chance")))
                .executor(this)
                .build();
    }
}
