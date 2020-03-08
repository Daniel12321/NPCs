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

public class CommandSpeed extends AICommand {

    public CommandSpeed() {
        super(AITypes.ALL);
    }

    @Override
    public void execute(Player p, AbstractAIPattern ai, CommandContext args) throws CommandException {
        ai.setSpeed(args.<Double>getOne("speed").get());
    }

    public CommandSpec build() {
        return CommandSpec.builder()
                .description(Text.of(TextColors.GOLD, "NPCs | Set Speed"))
                .permission("npc.edit.ai.speed")
                .arguments(GenericArguments.doubleNum(Text.of("speed")))
                .executor(this)
                .build();
    }
}
