package me.mrdaniel.npcs.commands.ai;

import com.google.common.collect.Lists;
import me.mrdaniel.npcs.ai.pattern.AbstractAIGuardPattern;
import me.mrdaniel.npcs.ai.pattern.AbstractAIPattern;
import me.mrdaniel.npcs.catalogtypes.aitype.AITypes;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

public class CommandPositionRemove extends AICommand {

    public CommandPositionRemove() {
        super(Lists.newArrayList(AITypes.GUARD_PATROL, AITypes.GUARD_RANDOM));
    }

    @Override
    public void execute(Player p, AbstractAIPattern ai, CommandContext args) throws CommandException {
        ((AbstractAIGuardPattern)ai).getPositions().remove(args.<Integer>getOne("position").get().intValue());
    }

    public CommandSpec build() {
        return CommandSpec.builder()
                .description(Text.of(TextColors.GOLD, "NPCs | Remove Position"))
                .permission("npc.edit.ai.position.remove")
                .arguments(GenericArguments.integer(Text.of("position")))
                .executor(this)
                .build();
    }
}
