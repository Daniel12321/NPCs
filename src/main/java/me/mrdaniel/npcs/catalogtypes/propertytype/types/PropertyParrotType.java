package me.mrdaniel.npcs.catalogtypes.propertytype.types;

import com.google.common.reflect.TypeToken;
import me.mrdaniel.npcs.catalogtypes.npctype.NPCType;
import me.mrdaniel.npcs.catalogtypes.npctype.NPCTypes;
import me.mrdaniel.npcs.catalogtypes.parrottype.ParrotType;
import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyType;
import me.mrdaniel.npcs.mixin.interfaces.NPCAble;
import net.minecraft.entity.passive.EntityParrot;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.text.Text;

public class PropertyParrotType extends PropertyType<ParrotType> {

	public PropertyParrotType() {
		super("ParrotType", "parrottype", GenericArguments.catalogedElement(Text.of("parrottype"), ParrotType.class));
	}

	@Override
	public TypeToken<ParrotType> getTypeToken() {
		return TypeToken.of(ParrotType.class);
	}

	@Override
	public boolean isSupported(final NPCAble npc) {
		return npc instanceof EntityParrot;
	}

	@Override
	public boolean isSupported(NPCType type) {
		return type == NPCTypes.PARROT;
	}

	@Override
	public void apply(NPCAble npc, ParrotType value) {
		((EntityParrot)npc).setVariant(value.getNbtId());
	}
}
