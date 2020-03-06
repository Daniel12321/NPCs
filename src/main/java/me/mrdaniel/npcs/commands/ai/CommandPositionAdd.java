package me.mrdaniel.npcs.commands.ai;

import com.google.common.collect.Lists;
import me.mrdaniel.npcs.ai.pattern.AbstractAIGuardPattern;
import me.mrdaniel.npcs.ai.pattern.AbstractAIPattern;
import me.mrdaniel.npcs.catalogtypes.aitype.AITypes;
import me.mrdaniel.npcs.utils.Position;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

public class CommandPositionAdd extends AICommand {

    public CommandPositionAdd() {
        super(Lists.newArrayList(AITypes.GUARD_PATROL, AITypes.GUARD_RANDOM));
    }

    @Override
    public void execute(Player p, AbstractAIPattern ai, CommandContext args) throws CommandException {
        ((AbstractAIGuardPattern)ai).getPositions().add(new Position(p));
    }

    public CommandSpec build() {
        return CommandSpec.builder()
                .description(Text.of(TextColors.GOLD, "NPCs | Add Position"))
                .permission("npc.edit.ai.position.add")
                .executor(this)
                .build();
    }
}
