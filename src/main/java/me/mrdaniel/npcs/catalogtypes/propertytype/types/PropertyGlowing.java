package me.mrdaniel.npcs.catalogtypes.propertytype.types;

import com.google.common.reflect.TypeToken;
import me.mrdaniel.npcs.catalogtypes.npctype.NPCType;
import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyType;
import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyTypes;
import me.mrdaniel.npcs.interfaces.mixin.NPCAble;
import net.minecraft.entity.Entity;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.text.Text;

public class PropertyGlowing extends PropertyType<Boolean> {

	public PropertyGlowing() {
		super("Glowing", "glowing", new Object[]{"glow", "enabled"}, GenericArguments.bool(Text.of("glowing")));
	}

	@Override
	public TypeToken<Boolean> getTypeToken() {
		return TypeToken.of(Boolean.class);
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
	public void apply(NPCAble npc, Boolean value) {
		((Entity)npc).setGlowing(value);

		if (value) {
			npc.getData().getProperty(PropertyTypes.GLOWCOLOR).ifPresent(color -> PropertyTypes.GLOWCOLOR.apply(npc, color));
		}
	}
}
