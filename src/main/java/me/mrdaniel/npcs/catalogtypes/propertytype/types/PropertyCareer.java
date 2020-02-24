package me.mrdaniel.npcs.catalogtypes.propertytype.types;

import com.google.common.reflect.TypeToken;
import me.mrdaniel.npcs.catalogtypes.career.Career;
import me.mrdaniel.npcs.catalogtypes.npctype.NPCType;
import me.mrdaniel.npcs.catalogtypes.npctype.NPCTypes;
import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyType;
import me.mrdaniel.npcs.interfaces.mixin.IMixinEntityVillager;
import me.mrdaniel.npcs.interfaces.mixin.NPCAble;
import net.minecraft.entity.monster.EntityZombieVillager;
import net.minecraft.entity.passive.EntityVillager;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.text.Text;

public class PropertyCareer extends PropertyType<Career> {

	public PropertyCareer() {
		super("Career", "career", GenericArguments.catalogedElement(Text.of("career"), Career.class));
	}

	@Override
	public TypeToken<Career> getTypeToken() {
		return TypeToken.of(Career.class);
	}

	@Override
	public boolean isSupported(final NPCAble npc) {
		return npc instanceof EntityVillager || npc instanceof EntityZombieVillager;
	}

	@Override
	public boolean isSupported(NPCType type) {
		return type == NPCTypes.VILLAGER || type == NPCTypes.ZOMBIE_VILLAGER;
	}

	@Override
	public void apply(NPCAble npc, Career value) {
		if (npc instanceof EntityVillager) {
			((EntityVillager) npc).setProfession(value.getProfessionId());
			((IMixinEntityVillager) npc).setCareerId(value.getCareerId());
		} else if (npc instanceof EntityZombieVillager) {
			((EntityZombieVillager) npc).setProfession(value.getProfessionId());
		}
	}
}
