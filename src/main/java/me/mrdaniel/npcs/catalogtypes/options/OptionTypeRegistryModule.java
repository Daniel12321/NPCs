package me.mrdaniel.npcs.catalogtypes.options;

import java.util.List;
import java.util.Optional;

import javax.annotation.Nonnull;

import org.spongepowered.api.registry.CatalogRegistryModule;

import com.google.common.collect.Lists;

import lombok.Getter;

@SuppressWarnings("rawtypes")
public class OptionTypeRegistryModule implements CatalogRegistryModule<OptionType> {

	@Getter private static OptionTypeRegistryModule instance = new OptionTypeRegistryModule();

	@Getter private final List<OptionType> main;
	@Getter private final List<OptionType> armor;
	@Getter private final List<OptionType> all;

	private OptionTypeRegistryModule() {
		this.main = Lists.newArrayList(OptionTypes.NAME, OptionTypes.LOOKING, OptionTypes.INTERACT, OptionTypes.GLOWING, OptionTypes.GLOWCOLOR, OptionTypes.BABY, OptionTypes.CHARGED, OptionTypes.ANGRY, OptionTypes.SIZE, OptionTypes.SITTING, OptionTypes.SADDLE, OptionTypes.CAREER, OptionTypes.HORSESTYLE, OptionTypes.HORSECOLOR, OptionTypes.LLAMATYPE, OptionTypes.CATTYPE, OptionTypes.RABBITTYPE, OptionTypes.PARROTTYPE);
		this.armor = Lists.newArrayList(OptionTypes.HELMET, OptionTypes.CHESTPLATE, OptionTypes.LEGGINGS, OptionTypes.BOOTS, OptionTypes.MAINHAND, OptionTypes.OFFHAND);
		this.all = Lists.newArrayList(OptionTypes.WORLD, OptionTypes.POSITION, OptionTypes.SKIN);
		this.all.addAll(this.main);
		this.all.addAll(this.armor);
	}

	@Override
	public Optional<OptionType> getById(@Nonnull final String id) {
		for (OptionType type : this.all) { if (type.getId().equalsIgnoreCase(id)) { return Optional.of(type); } }
		return Optional.empty();
	}
}