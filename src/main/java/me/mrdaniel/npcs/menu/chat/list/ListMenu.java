package me.mrdaniel.npcs.menu.chat.list;

import com.google.common.collect.Lists;
import me.mrdaniel.npcs.NPCs;
import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyTypes;
import me.mrdaniel.npcs.exceptions.NPCException;
import me.mrdaniel.npcs.io.INPCData;
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

        Sponge.getServer().getWorlds().forEach(world -> {
            NPCs.getInstance().getNPCManager().getNPCs(world.getName()).forEach(data -> lines.add(getNPCText(data)));
        });

        return lines;
    }

    @Nullable
    @Override
    public Text getFooter() {
        return null;
    }

    private Text getNPCText(INPCData data) {
        Position pos = data.getNPCPosition();

        Text.Builder b = Text.builder()
                .append(Text.of(TextColors.BLUE, data.getNPCId(), ": "))
                .append(Text.of(TextColors.GOLD, "Loc=", TextColors.RED, pos.getWorldName(), ",", (int)pos.getX(), ",", (int)pos.getY(), ",", (int)pos.getZ()))
                .append(Text.of(TextColors.GOLD, " Type=", TextColors.RED, data.getNPCProperty(PropertyTypes.TYPE).get().getName()));
        data.getNPCProperty(PropertyTypes.NAME).ifPresent(name -> b.append(Text.of(TextColors.GOLD, " Name=", TextColors.RED), TextUtils.toText(name)));

        return b.onHover(TextActions.showText(Text.of(TextColors.YELLOW, "Select")))
                .onClick(TextActions.executeCallback(src -> {
                    try {
                        NPCs.getInstance().getMenuManager().select((Player) src, data);
                    } catch (NPCException exc) {
                        src.sendMessage(Text.of(TextColors.RED, "Failed to select NPC: NPC is not loaded!"));
                    }
                }))
                .build();
    }

//    private void sendNPCInfo(Player p, INPCData data) {
//        boolean worldLoaded = data.getNPCPosition().getWorld().isPresent();
//        boolean npcLoaded = worldLoaded && NPCs.getInstance().getNPCManager().getNPC(data).isPresent();
//
//        p.sendMessage(Text.EMPTY);
//        p.sendMessage(Text.of(Text.of(TextColors.YELLOW, "---------------=====[ ", TextColors.RED, "NPC Info", TextColors.YELLOW, " ]=====---------------")));
//        p.sendMessage(Text.of(TextColors.GOLD, "World Loaded: ", worldLoaded ? TextColors.DARK_GREEN : TextColors.RED, worldLoaded ? "True" : "False"));
//        p.sendMessage(Text.of(TextColors.GOLD, "NPC Loaded: ", npcLoaded ? TextColors.DARK_GREEN : TextColors.RED, npcLoaded ? "True" : "False"));
//        p.sendMessage(Text.EMPTY);
//
//        Text.Builder buttons = Text.builder();
//        if (worldLoaded) {
//            buttons.append(Text.of("  "), Text.builder().append(Text.of(TextColors.GOLD, "[Select]")).onHover(TextActions.showText(Text.of(TextColors.GOLD, "Select"))).onClick(TextActions.executeCallback(src -> select((Player)src, data))).build());
//            buttons.append(Text.of("  "), Text.builder().append(Text.of(TextColors.RED, "[Remove]")).onHover(TextActions.showText(Text.of(TextColors.RED, "Remove"))).onClick(TextActions.suggestCommand("/npc remove " + Integer.toString(data.getNPCId()))).build());
//        }
//
//        p.sendMessage(buttons.build());
//        p.sendMessage(Text.EMPTY);
//        p.sendMessage(Text.builder().append(Text.of(TextColors.YELLOW, "----------------====[ "), Text.builder().append(Text.of(TextColors.RED, "Back")).onHover(TextActions.showText(Text.of(TextColors.RED, "Back"))).onClick(TextActions.executeCallback(src -> sendNPCList(src))).build(), Text.of(TextColors.YELLOW, " ]====----------------")).build());
//    }

    private void select(Player p, INPCData data) {
        try {
            NPCs.getInstance().getMenuManager().select(p, data);
        } catch (final NPCException exc) {
            p.sendMessage(Text.of(TextColors.RED, exc.getMessage()));
        }
    }
}
