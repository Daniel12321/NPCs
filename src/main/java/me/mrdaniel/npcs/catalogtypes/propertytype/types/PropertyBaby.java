package me.mrdaniel.npcs.catalogtypes.propertytype.types;

import com.google.common.reflect.TypeToken;
import me.mrdaniel.npcs.catalogtypes.npctype.NPCType;
import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyType;
import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyTypes;
import me.mrdaniel.npcs.mixin.interfaces.NPCAble;
import me.mrdaniel.npcs.utils.Position;
import net.minecraft.entity.Entity;
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
	public boolean isSupported(NPCType type) {
		return type.isAgeable();
	}

	@Override
	public void apply(NPCAble npc, Boolean value) {
		if (npc instanceof EntityZombie) {
			((EntityZombie)npc).setChild(value);
		} else {
			((EntityAgeable)npc).setGrowingAge(value ? Integer.MIN_VALUE : 0);
		}

		// Fixes some entities (etc. horses) moving around when changing from baby to adult
		Position pos = npc.getData().getProperty(PropertyTypes.POSITION).get();
		((Entity)npc).setLocationAndAngles(pos.getX(), pos.getY(), pos.getZ(), pos.getYaw(), pos.getPitch());
	}
}
