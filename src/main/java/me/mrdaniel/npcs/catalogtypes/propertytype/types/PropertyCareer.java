package me.mrdaniel.npcs.catalogtypes.propertytype.types;

import me.mrdaniel.npcs.catalogtypes.career.Career;
import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyType;
import me.mrdaniel.npcs.interfaces.mixin.IMixinEntityVillager;
import me.mrdaniel.npcs.interfaces.mixin.NPCAble;
import net.minecraft.entity.passive.EntityVillager;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.entity.living.Villager;
import org.spongepowered.api.entity.living.monster.ZombieVillager;
import org.spongepowered.api.text.Text;

public class PropertyCareer extends PropertyType<Career> {

	public PropertyCareer() {
		super("Career", "career", GenericArguments.catalogedElement(Text.of("career"), Career.class));
	}

	@Override
	public boolean isSupported(final NPCAble npc) {
		return npc instanceof Villager || npc instanceof ZombieVillager;
	}

	@Override
	public void apply(NPCAble npc, Career value) {
		((EntityVillager) npc).setProfession(value.getProfessionId());
		((IMixinEntityVillager) npc).setCareerId(value.getCareerId());
	}
}
