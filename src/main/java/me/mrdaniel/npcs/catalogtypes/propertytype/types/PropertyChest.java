package me.mrdaniel.npcs.catalogtypes.propertytype.types;

import com.google.common.reflect.TypeToken;
import me.mrdaniel.npcs.catalogtypes.npctype.NPCType;
import me.mrdaniel.npcs.catalogtypes.npctype.NPCTypes;
import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyType;
import me.mrdaniel.npcs.interfaces.mixin.NPCAble;
import net.minecraft.entity.passive.AbstractChestHorse;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.text.Text;

public class PropertyChest extends PropertyType<Boolean> {

	public PropertyChest() {
		super("Chest", "chest", GenericArguments.bool(Text.of("chest")));
	}

	@Override
	public TypeToken<Boolean> getTypeToken() {
		return TypeToken.of(Boolean.class);
	}

	@Override
	public boolean isSupported(final NPCAble npc) {
		return npc instanceof AbstractChestHorse;
	}

	@Override
	public boolean isSupported(NPCType type) {
		return type == NPCTypes.MULE
				|| type == NPCTypes.DONKEY
				|| type == NPCTypes.LLAMA;
	}

	@Override
	public void apply(NPCAble npc, Boolean value) {
		((AbstractChestHorse)npc).setChested(value);
	}
}
