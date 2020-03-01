package me.mrdaniel.npcs.catalogtypes.aitype;

import me.mrdaniel.npcs.catalogtypes.npctype.NPCType;
import org.spongepowered.api.CatalogType;
import org.spongepowered.api.entity.ai.task.AITask;
import org.spongepowered.api.entity.living.Agent;
import org.spongepowered.api.entity.living.Creature;
import org.spongepowered.api.util.annotation.CatalogedBy;

@CatalogedBy(AITypes.class)
public abstract class AIType implements CatalogType {

	private final String name;
	private final String id;

	protected AIType(String name, String id) {
		this.name = name;
		this.id = id;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public String getId() {
		return this.id;
	}

	public abstract AITask<? extends Agent> createAITask(Creature owner, NPCType npcType);
}
