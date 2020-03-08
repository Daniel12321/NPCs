package me.mrdaniel.npcs.catalogtypes.propertytype.types;

import com.google.common.reflect.TypeToken;
import me.mrdaniel.npcs.catalogtypes.npctype.NPCType;
import me.mrdaniel.npcs.catalogtypes.npctype.NPCTypes;
import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyType;
import me.mrdaniel.npcs.mixin.interfaces.IMixinEntityShulker;
import me.mrdaniel.npcs.mixin.interfaces.NPCAble;
import net.minecraft.entity.monster.EntityShulker;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.text.Text;

public class PropertyPeeking extends PropertyType<Boolean> {

	public PropertyPeeking() {
		super("Peeking", "peeking", GenericArguments.bool(Text.of("peeking")));
	}

	@Override
	public TypeToken<Boolean> getTypeToken() {
		return TypeToken.of(Boolean.class);
	}

	@Override
	public boolean isSupported(final NPCAble npc) {
		return npc instanceof EntityShulker;
	}

	@Override
	public boolean isSupported(NPCType type) {
		return type == NPCTypes.SHULKER;
	}

	@Override
	public void apply(NPCAble npc, Boolean value) {
		((IMixinEntityShulker)npc).setPeek(value);
	}
}
