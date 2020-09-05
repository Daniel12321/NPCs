package me.mrdaniel.npcs.gui.chat.list;

import me.mrdaniel.npcs.NPCs;
import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyTypes;
import me.mrdaniel.npcs.gui.chat.AbstractChatMenu;
import me.mrdaniel.npcs.io.INPCData;
import me.mrdaniel.npcs.utils.Position;
import me.mrdaniel.npcs.utils.TextUtils;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColors;

import java.util.List;
import java.util.stream.Collectors;

public class ListMenu extends AbstractChatMenu {

    public ListMenu(Player player) {
        super.setPlayer(player);
        super.setTitle(Text.of(Text.of(TextColors.YELLOW, "[ ", TextColors.RED, "NPC List", TextColors.YELLOW, " ]")));
    }

    @Override
    protected List<Text> getContents() {
        return NPCs.getInstance().getNPCManager().getData().stream().map(this::getNPCText).collect(Collectors.toList());
    }

    private Text getNPCText(INPCData data) {
        Position pos = data.getProperty(PropertyTypes.POSITION).get();

        Text.Builder b = Text.builder()
                .append(Text.of(TextColors.BLUE, data.getId(), ": "))
                .append(Text.of(TextColors.GOLD, "Loc=", TextColors.RED, pos.getWorldName(), ",", (int)pos.getX(), ",", (int)pos.getY(), ",", (int)pos.getZ()))
                .append(Text.of(TextColors.GOLD, " Type=", TextColors.RED, data.getProperty(PropertyTypes.TYPE).get().getName()));
        data.getProperty(PropertyTypes.NAME).ifPresent(name -> b.append(Text.of(TextColors.GOLD, " Name=", TextColors.RED), TextUtils.toText(name)));

        return b.onHover(TextActions.showText(Text.of(TextColors.YELLOW, "Select")))
                .onClick(TextActions.executeCallback(src -> NPCs.getInstance().getSelectedManager().select((Player) src, data)))
                .build();
    }
}
