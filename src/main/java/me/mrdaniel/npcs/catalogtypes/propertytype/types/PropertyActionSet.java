package me.mrdaniel.npcs.catalogtypes.propertytype.types;

import com.google.common.reflect.TypeToken;
import me.mrdaniel.npcs.actions.ActionSet;
import me.mrdaniel.npcs.catalogtypes.npctype.NPCType;
import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyType;
import me.mrdaniel.npcs.io.database.DatabaseNPCData;
import me.mrdaniel.npcs.io.hocon.HoconNPCData;
import me.mrdaniel.npcs.io.nbt.NBTNPCData;
import me.mrdaniel.npcs.mixin.interfaces.NPCAble;

import javax.annotation.Nullable;

public class PropertyActionSet extends PropertyType<ActionSet> {

    public PropertyActionSet() {
        super("Action Set", "actions");
    }

    @Override
    public TypeToken<ActionSet> getTypeToken() {
        return TypeToken.of(ActionSet.class);
    }

    @Override
    public boolean isSupported(NPCAble npc) {
        return true;
    }

    @Override
    public boolean isSupported(NPCType type) {
        return true;
    }

    @Override
    public void apply(NPCAble npc, ActionSet value) {}

    @Override
    public void setHocon(HoconNPCData data, ActionSet value) {
        data.actions = value;
    }

    @Override
    public void setDatabase(DatabaseNPCData data, ActionSet value) {

    }

    @Override
    public void setNBT(NBTNPCData data, ActionSet value) {

    }

    @Nullable
    @Override
    public ActionSet getHocon(HoconNPCData data) {
        return data.actions;
    }

    @Nullable
    @Override
    public ActionSet getDatabase(DatabaseNPCData data) {
        return null;
    }

    @Nullable
    @Override
    public ActionSet getNBT(NBTNPCData data) {
        return null;
    }
}
