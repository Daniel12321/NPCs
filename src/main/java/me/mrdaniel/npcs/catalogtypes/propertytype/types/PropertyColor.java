package me.mrdaniel.npcs.catalogtypes.propertytype.types;

import com.google.common.reflect.TypeToken;
import me.mrdaniel.npcs.catalogtypes.dyecolor.DyeColorType;
import me.mrdaniel.npcs.catalogtypes.npctype.NPCType;
import me.mrdaniel.npcs.catalogtypes.npctype.NPCTypes;
import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyType;
import me.mrdaniel.npcs.io.database.DatabaseNPCData;
import me.mrdaniel.npcs.io.hocon.HoconNPCData;
import me.mrdaniel.npcs.io.nbt.NBTNPCData;
import me.mrdaniel.npcs.mixin.interfaces.IMixinEntityShulker;
import me.mrdaniel.npcs.mixin.interfaces.NPCAble;
import net.minecraft.entity.monster.EntityShulker;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntityWolf;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.text.Text;

import javax.annotation.Nullable;

public class PropertyColor extends PropertyType<DyeColorType> {

	public PropertyColor() {
		super("Color", "color", GenericArguments.catalogedElement(Text.of("color"), DyeColorType.class));
	}

	@Override
	public TypeToken<DyeColorType> getTypeToken() {
		return TypeToken.of(DyeColorType.class);
	}

	@Override
	public boolean isSupported(final NPCAble npc) {
		return npc instanceof EntityWolf
				|| npc instanceof EntityShulker
				|| npc instanceof EntitySheep;
	}

	@Override
	public boolean isSupported(NPCType type) {
		return type == NPCTypes.WOLF
				|| type == NPCTypes.SHULKER
				|| type == NPCTypes.SHEEP;
	}

	@Override
	public void apply(NPCAble npc, DyeColorType value) {
		if (npc instanceof EntityWolf) {
			((EntityWolf) npc).setTamed(true);
			((EntityWolf) npc).setCollarColor(value.getColor());
		} else if (npc instanceof EntityShulker) {
			((IMixinEntityShulker) npc).setColor(value.getColor());
		} else if (npc instanceof EntitySheep) {
			((EntitySheep) npc).setFleeceColor(value.getColor());
		}
	}

	@Override
	public void setHocon(HoconNPCData data, DyeColorType value) {
		data.color = value;
	}

	@Override
	public void setDatabase(DatabaseNPCData data, DyeColorType value) {

	}

	@Override
	public void setNBT(NBTNPCData data, DyeColorType value) {

	}

	@Nullable
	@Override
	public DyeColorType getHocon(HoconNPCData data) {
		return data.color;
	}

	@Nullable
	@Override
	public DyeColorType getDatabase(DatabaseNPCData data) {
		return null;
	}

	@Nullable
	@Override
	public DyeColorType getNBT(NBTNPCData data) {
		return null;
	}
}
