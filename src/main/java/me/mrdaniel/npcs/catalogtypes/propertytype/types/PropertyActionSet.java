package me.mrdaniel.npcs.catalogtypes.propertytype.types;

import com.google.common.reflect.TypeToken;
import me.mrdaniel.npcs.actions.ActionSet;
import me.mrdaniel.npcs.catalogtypes.npctype.NPCType;
import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyType;
import me.mrdaniel.npcs.mixin.interfaces.NPCAble;

public class PropertyActionSet extends PropertyType<ActionSet> {

    public PropertyActionSet() {
        super("Action Set", "actions", new Object[]{"actions"});
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
}
