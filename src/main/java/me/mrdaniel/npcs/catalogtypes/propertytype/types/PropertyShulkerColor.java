package me.mrdaniel.npcs.catalogtypes.propertytype.types;

import com.google.common.reflect.TypeToken;
import me.mrdaniel.npcs.catalogtypes.dyecolor.DyeColorType;
import me.mrdaniel.npcs.catalogtypes.npctype.NPCType;
import me.mrdaniel.npcs.catalogtypes.npctype.NPCTypes;
import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyType;
import me.mrdaniel.npcs.interfaces.mixin.IMixinEntityShulker;
import me.mrdaniel.npcs.interfaces.mixin.NPCAble;
import net.minecraft.entity.monster.EntityShulker;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.text.Text;

public class PropertyShulkerColor extends PropertyType<DyeColorType> {

	public PropertyShulkerColor() {
		super("ShulkerColor", "shulkercolor", GenericArguments.catalogedElement(Text.of("shulkercolor"), DyeColorType.class));
	}

	@Override
	public TypeToken<DyeColorType> getTypeToken() {
		return TypeToken.of(DyeColorType.class);
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
	public void apply(NPCAble npc, DyeColorType value) {
		((IMixinEntityShulker)npc).setColor(value.getColor());
	}
}
