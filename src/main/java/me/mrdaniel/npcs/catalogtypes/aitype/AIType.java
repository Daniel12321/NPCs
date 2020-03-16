package me.mrdaniel.npcs.catalogtypes.aitype;

import me.mrdaniel.npcs.NPCs;
import me.mrdaniel.npcs.ai.pattern.AIPatternStay;
import me.mrdaniel.npcs.ai.pattern.AbstractAIPattern;
import me.mrdaniel.npcs.catalogtypes.npctype.NPCType;
import me.mrdaniel.npcs.catalogtypes.npctype.NPCTypes;
import ninja.leaping.configurate.ConfigurationNode;
import org.spongepowered.api.CatalogType;
import org.spongepowered.api.util.annotation.CatalogedBy;

@CatalogedBy(AITypes.class)
public class AIType implements CatalogType {

	private final String name;
	private final String id;
	private final int defaultChance;
	private Class<? extends AbstractAIPattern> clazz;

	protected AIType(String name, String id, int defaultChance, Class<? extends AbstractAIPattern> clazz) {
		this.name = name;
		this.id = id;
		this.defaultChance = defaultChance;
		this.clazz = clazz;
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

	public AbstractAIPattern newInstance(NPCType type) {
		return this.instantiate(NPCType.class, type);
	}

	public AbstractAIPattern deserialize(ConfigurationNode node) {
		return this.instantiate(ConfigurationNode.class, node);
	}

	private <T> AbstractAIPattern instantiate(Class<T> constructorArgument, T argument) {
		try {
			return this.clazz.getConstructor(constructorArgument).newInstance(argument);
		} catch (Exception exc) {
			NPCs.getInstance().getLogger().error("Failed to deserialize AbstractAIPattern", exc);
			return new AIPatternStay(NPCTypes.CREEPER);
		}
	}
}
