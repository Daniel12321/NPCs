package me.mrdaniel.npcs.catalogtypes.propertytype.types;

import com.google.common.reflect.TypeToken;
import me.mrdaniel.npcs.catalogtypes.cattype.CatType;
import me.mrdaniel.npcs.catalogtypes.npctype.NPCType;
import me.mrdaniel.npcs.catalogtypes.npctype.NPCTypes;
import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyType;
import me.mrdaniel.npcs.mixin.interfaces.NPCAble;
import net.minecraft.entity.passive.EntityOcelot;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.text.Text;

public class PropertyCatType extends PropertyType<CatType> {

	public PropertyCatType() {
		super("CatType", "cattype", GenericArguments.catalogedElement(Text.of("cattype"), CatType.class));
	}

	@Override
	public TypeToken<CatType> getTypeToken() {
		return TypeToken.of(CatType.class);
	}

	@Override
	public boolean isSupported(final NPCAble npc) {
		return npc instanceof EntityOcelot;
	}

	@Override
	public boolean isSupported(NPCType type) {
		return type == NPCTypes.OCELOT;
	}

	@Override
	public void apply(NPCAble npc, CatType value) {
		((EntityOcelot)npc).setTameSkin(value.getNbtId());
	}
}
