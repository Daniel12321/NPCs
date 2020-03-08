package me.mrdaniel.npcs.catalogtypes.aitype;

import me.mrdaniel.npcs.ai.pattern.AbstractAIPattern;
import ninja.leaping.configurate.ConfigurationNode;
import org.spongepowered.api.CatalogType;
import org.spongepowered.api.util.annotation.CatalogedBy;

import java.util.function.Function;

@CatalogedBy(AITypes.class)
public class AIType implements CatalogType {

	private final String name;
	private final String id;
	private final int defaultChance;
	private final Function<ConfigurationNode, AbstractAIPattern> deserializer;

	protected AIType(String name, String id, int defaultChance, Function<ConfigurationNode, AbstractAIPattern> deserializer) {
		this.name = name;
		this.id = id;
		this.defaultChance = defaultChance;
		this.deserializer = deserializer;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public String getId() {
		return this.id;
	}

	public int getDefaultChance() {
		return defaultChance;
	}

	public Function<ConfigurationNode, AbstractAIPattern> getDeserializer() {
		return this.deserializer;
	}
}
