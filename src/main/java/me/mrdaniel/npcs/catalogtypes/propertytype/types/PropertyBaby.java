package me.mrdaniel.npcs.catalogtypes.propertytype.types;

import com.google.common.reflect.TypeToken;
import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyType;
import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyTypes;
import me.mrdaniel.npcs.interfaces.mixin.NPCAble;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.monster.EntityZombie;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.entity.living.Ageable;
import org.spongepowered.api.text.Text;

public class PropertyBaby extends PropertyType<Boolean> {

	public PropertyBaby() {
		super("Baby", "baby", GenericArguments.bool(Text.of("baby")));
	}

	@Override
	public TypeToken<Boolean> getTypeToken() {
		return TypeToken.of(Boolean.class);
	}

	@Override
	public boolean isSupported(final NPCAble npc) {
		return npc instanceof Ageable;
	}

	@Override
	public void apply(NPCAble npc, Boolean value) {
		if (npc instanceof EntityZombie) {
			((EntityZombie) npc).setChild(value);
		} else {
			((EntityAgeable) npc).setGrowingAge(value ? Integer.MIN_VALUE : 0);
		}

		PropertyTypes.POSITION.apply(npc, npc.getProperty(PropertyTypes.POSITION).get());
	}
}
