package me.mrdaniel.npcs.catalogtypes.propertytype.types;

import com.google.common.reflect.TypeToken;
import me.mrdaniel.npcs.catalogtypes.npctype.NPCType;
import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyType;
import me.mrdaniel.npcs.io.database.DatabaseNPCData;
import me.mrdaniel.npcs.io.hocon.HoconNPCData;
import me.mrdaniel.npcs.io.nbt.NBTNPCData;
import me.mrdaniel.npcs.mixin.interfaces.NPCAble;
import org.spongepowered.api.entity.ArmorEquipable;
import org.spongepowered.api.item.inventory.ItemStack;

import javax.annotation.Nullable;

public class PropertyHelmet extends PropertyType<ItemStack> {

	public PropertyHelmet() {
		super("Helmet", "helmet");
	}

	@Override
	public TypeToken<ItemStack> getTypeToken() {
		return TypeToken.of(ItemStack.class);
	}

	@Override
	public boolean isSupported(final NPCAble npc) {
		return npc instanceof ArmorEquipable;
	}

	@Override
	public boolean isSupported(NPCType type) {
		return type.isArmorEquipable();
	}

	@Override
	public void apply(NPCAble npc, ItemStack value) {
		((ArmorEquipable)npc).setHelmet(value);
	}

	@Override
	public void setHocon(HoconNPCData data, ItemStack value) {
		data.equipment.helmet = value;
	}

	@Override
	public void setDatabase(DatabaseNPCData data, ItemStack value) {

	}

	@Override
	public void setNBT(NBTNPCData data, ItemStack value) {

	}

	@Nullable
	@Override
	public ItemStack getHocon(HoconNPCData data) {
		return data.equipment.helmet;
	}

	@Nullable
	@Override
	public ItemStack getDatabase(DatabaseNPCData data) {
		return null;
	}

	@Nullable
	@Override
	public ItemStack getNBT(NBTNPCData data) {
		return null;
	}
}
