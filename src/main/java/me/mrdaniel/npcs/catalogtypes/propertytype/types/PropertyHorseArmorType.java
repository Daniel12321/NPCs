package me.mrdaniel.npcs.catalogtypes.propertytype.types;

import com.google.common.reflect.TypeToken;
import me.mrdaniel.npcs.catalogtypes.horsearmor.HorseArmorType;
import me.mrdaniel.npcs.catalogtypes.npctype.NPCType;
import me.mrdaniel.npcs.catalogtypes.npctype.NPCTypes;
import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyType;
import me.mrdaniel.npcs.io.database.DatabaseNPCData;
import me.mrdaniel.npcs.io.hocon.HoconNPCData;
import me.mrdaniel.npcs.io.nbt.NBTNPCData;
import me.mrdaniel.npcs.mixin.interfaces.NPCAble;
import net.minecraft.entity.passive.EntityHorse;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;

import javax.annotation.Nullable;

public class PropertyHorseArmorType extends PropertyType<HorseArmorType> {

	public PropertyHorseArmorType() {
		super("HorseArmor", "horsearmor", GenericArguments.catalogedElement(Text.of("horsearmor"), HorseArmorType.class));
	}

	@Override
	public TypeToken<HorseArmorType> getTypeToken() {
		return TypeToken.of(HorseArmorType.class);
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
	public void apply(NPCAble npc, HorseArmorType value) {
		ItemStack is = value.getType() == null ? null : ItemStack.builder().itemType(value.getType()).quantity(1).build();
		((EntityHorse)npc).setHorseArmorStack((net.minecraft.item.ItemStack)(Object)is);
	}

	@Override
	public void setHocon(HoconNPCData data, HorseArmorType value) {
		data.horsearmor = value;
	}

	@Override
	public void setDatabase(DatabaseNPCData data, HorseArmorType value) {

	}

	@Override
	public void setNBT(NBTNPCData data, HorseArmorType value) {

	}

	@Nullable
	@Override
	public HorseArmorType getHocon(HoconNPCData data) {
		return data.horsearmor;
	}

	@Nullable
	@Override
	public HorseArmorType getDatabase(DatabaseNPCData data) {
		return null;
	}

	@Nullable
	@Override
	public HorseArmorType getNBT(NBTNPCData data) {
		return null;
	}
}
