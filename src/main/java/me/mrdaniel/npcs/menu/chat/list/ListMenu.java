package me.mrdaniel.npcs.menu.chat.list;

import com.google.common.collect.Lists;
import me.mrdaniel.npcs.NPCs;
import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyTypes;
import me.mrdaniel.npcs.io.INPCData;
import me.mrdaniel.npcs.managers.NPCManager;
import me.mrdaniel.npcs.menu.chat.ChatMenu;
import me.mrdaniel.npcs.utils.Position;
import me.mrdaniel.npcs.utils.TextUtils;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColors;

import javax.annotation.Nullable;
import java.util.List;

public class ListMenu implements ChatMenu {

    @Override
    public Text getTitle() {
        return Text.of(Text.of(TextColors.YELLOW, "[ ", TextColors.RED, "NPC List", TextColors.YELLOW, " ]"));
    }

    @Nullable
    @Override
    public Text getHeader() {
        return null;
    }

    @Override
    public List<Text> getContents() {
        List<Text> lines = Lists.newArrayList();

        NPCManager manager = NPCs.getInstance().getNPCManager();
        Sponge.getServer().getWorlds().forEach(w -> manager.getData(w.getName()).forEach(data -> lines.add(getNPCText(data))));

        return lines;
    }

    @Nullable
    @Override
    public Text getFooter() {
        return null;
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
