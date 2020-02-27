package me.mrdaniel.npcs.catalogtypes.propertytype.types;

import com.google.common.reflect.TypeToken;
import me.mrdaniel.npcs.catalogtypes.npctype.NPCType;
import me.mrdaniel.npcs.catalogtypes.npctype.NPCTypes;
import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyType;
import me.mrdaniel.npcs.mixin.interfaces.NPCAble;
import net.minecraft.entity.boss.EntityWither;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.text.Text;

public class PropertyArmored extends PropertyType<Boolean> {

	public PropertyArmored() {
		super("Armored", "armored", GenericArguments.bool(Text.of("armored")));
	}

	@Override
	public TypeToken<Boolean> getTypeToken() {
		return TypeToken.of(Boolean.class);
	}

	@Override
	public boolean isSupported(final NPCAble npc) {
		return npc instanceof EntityWither;
	}

	@Override
	public boolean isSupported(NPCType type) {
		return type == NPCTypes.WITHER;
	}

	@Override
	public void apply(NPCAble npc, Boolean value) {
		EntityWither wither = (EntityWither) npc;

		wither.setHealth(value ?  wither.getMaxHealth() / 3.0f : wither.getMaxHealth());
	}
}
