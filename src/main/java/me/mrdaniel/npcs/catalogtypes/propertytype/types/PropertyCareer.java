package me.mrdaniel.npcs.catalogtypes.propertytype.types;

import com.google.common.reflect.TypeToken;
import me.mrdaniel.npcs.catalogtypes.career.Career;
import me.mrdaniel.npcs.catalogtypes.npctype.NPCType;
import me.mrdaniel.npcs.catalogtypes.npctype.NPCTypes;
import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyType;
import me.mrdaniel.npcs.io.database.DatabaseNPCData;
import me.mrdaniel.npcs.io.hocon.HoconNPCData;
import me.mrdaniel.npcs.io.nbt.NBTNPCData;
import me.mrdaniel.npcs.mixin.interfaces.IMixinEntityVillager;
import me.mrdaniel.npcs.mixin.interfaces.NPCAble;
import net.minecraft.entity.monster.EntityZombieVillager;
import net.minecraft.entity.passive.EntityVillager;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.text.Text;

import javax.annotation.Nullable;

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
			((EntityVillager)npc).setProfession(value.getProfessionId());
			((IMixinEntityVillager)npc).setCareerId(value.getCareerId());
		} else if (npc instanceof EntityZombieVillager) {
			((EntityZombieVillager)npc).setProfession(value.getProfessionId());
		}
	}

	@Override
	public void setHocon(HoconNPCData data, Career value) {
		data.career = value;
	}

	@Override
	public void setDatabase(DatabaseNPCData data, Career value) {

	}

	@Override
	public void setNBT(NBTNPCData data, Career value) {

	}

	@Nullable
	@Override
	public Career getHocon(HoconNPCData data) {
		return data.career;
	}

	@Nullable
	@Override
	public Career getDatabase(DatabaseNPCData data) {
		return null;
	}

	@Nullable
	@Override
	public Career getNBT(NBTNPCData data) {
		return null;
	}
}
