package me.mrdaniel.npcs.catalogtypes.propertytype.types;

import com.google.common.reflect.TypeToken;
import me.mrdaniel.npcs.catalogtypes.npctype.NPCType;
import me.mrdaniel.npcs.catalogtypes.npctype.NPCTypes;
import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyType;
import me.mrdaniel.npcs.interfaces.mixin.NPCAble;
import net.minecraft.entity.passive.EntityBat;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.text.Text;

public class PropertyHanging extends PropertyType<Boolean> {

	public PropertyHanging() {
		super("Hanging", "hanging", GenericArguments.bool(Text.of("hanging")));
	}

	@Override
	public TypeToken<Boolean> getTypeToken() {
		return TypeToken.of(Boolean.class);
	}

	@Override
	public boolean isSupported(final NPCAble npc) {
		return npc instanceof EntityBat;
	}

	@Override
	public boolean isSupported(NPCType type) {
		return type == NPCTypes.BAT;
	}

	@Override
	public void apply(NPCAble npc, Boolean value) {
		((EntityBat) npc).setIsBatHanging(value);
	}
}
