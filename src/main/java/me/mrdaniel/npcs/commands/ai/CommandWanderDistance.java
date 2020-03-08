package me.mrdaniel.npcs.commands.ai;

import com.google.common.collect.Lists;
import me.mrdaniel.npcs.ai.pattern.AIPatternWander;
import me.mrdaniel.npcs.ai.pattern.AbstractAIPattern;
import me.mrdaniel.npcs.catalogtypes.aitype.AITypes;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

public class CommandWanderDistance extends AICommand {

    public CommandWanderDistance() {
        super(Lists.newArrayList(AITypes.WANDER));
    }

    @Override
    public void execute(Player p, AbstractAIPattern ai, CommandContext args) throws CommandException {
        ((AIPatternWander)ai).setDistance(args.<Integer>getOne("distance").get());
    }

    public CommandSpec build() {
        return CommandSpec.builder()
                .description(Text.of(TextColors.GOLD, "NPCs | Set Distance"))
                .permission("npc.edit.ai.distance")
                .arguments(GenericArguments.integer(Text.of("distance")))
                .executor(this)
                .build();
    }
}
