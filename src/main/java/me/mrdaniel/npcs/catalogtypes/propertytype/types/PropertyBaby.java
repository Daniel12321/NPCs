package me.mrdaniel.npcs.catalogtypes.propertytype.types;

import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyType;
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

		//TODO: Check if this is needed
//		super.setLocationAndAngles(this.data.getX(), this.data.getY(), this.data.getZ(), this.data.getYaw(), this.data.getPitch());
	}
}
