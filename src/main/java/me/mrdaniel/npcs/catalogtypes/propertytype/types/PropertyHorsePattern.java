package me.mrdaniel.npcs.catalogtypes.propertytype.types;

import com.google.common.reflect.TypeToken;
import me.mrdaniel.npcs.catalogtypes.horsecolor.HorseColors;
import me.mrdaniel.npcs.catalogtypes.horsepattern.HorsePattern;
import me.mrdaniel.npcs.catalogtypes.npctype.NPCType;
import me.mrdaniel.npcs.catalogtypes.npctype.NPCTypes;
import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyType;
import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyTypes;
import me.mrdaniel.npcs.mixin.interfaces.NPCAble;
import net.minecraft.entity.passive.EntityHorse;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.text.Text;

public class PropertyHorsePattern extends PropertyType<HorsePattern> {

	public PropertyHorsePattern() {
		super("HorsePattern", "horsepattern", GenericArguments.catalogedElement(Text.of("horsepattern"), HorsePattern.class));
	}

	@Override
	public TypeToken<HorsePattern> getTypeToken() {
		return TypeToken.of(HorsePattern.class);
	}

	@Override
	public boolean isSupported(final NPCAble npc) {
		return npc instanceof EntityHorse;
	}

	@Override
	public boolean isSupported(NPCType type) {
		return type == NPCTypes.HORSE;
	}

	@Override
	public void apply(NPCAble npc, HorsePattern value) {
		((EntityHorse)npc).setHorseVariant(value.getNbtId() + npc.getData().getProperty(PropertyTypes.HORSECOLOR).orElse(HorseColors.BROWN).getNbtId());
	}
}
