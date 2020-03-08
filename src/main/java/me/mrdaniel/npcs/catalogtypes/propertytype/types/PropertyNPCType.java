package me.mrdaniel.npcs.catalogtypes.propertytype.types;

import com.google.common.reflect.TypeToken;
import me.mrdaniel.npcs.NPCs;
import me.mrdaniel.npcs.catalogtypes.npctype.NPCType;
import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyType;
import me.mrdaniel.npcs.exceptions.NPCException;
import me.mrdaniel.npcs.mixin.interfaces.NPCAble;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.text.Text;

public class PropertyNPCType extends PropertyType<NPCType> {

	public PropertyNPCType() {
		super("Type", "type", GenericArguments.catalogedElement(Text.of("type"), NPCType.class));
	}

	@Override
	public TypeToken<NPCType> getTypeToken() {
		return TypeToken.of(NPCType.class);
	}

	@Override
	public boolean isSupported(final NPCAble npc) {
		return true;
	}

	@Override
	public boolean isSupported(NPCType type) {
		return true;
	}

	@Override
	public void apply(NPCAble npc, NPCType value) {
		try {
			NPCs.getInstance().getNPCManager().spawn(npc.getData());
		} catch (NPCException exc) {
			NPCs.getInstance().getLogger().error("Failed to respawn NPC after type change: ", exc);
		}
	}
}
