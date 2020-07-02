package me.mrdaniel.npcs.catalogtypes.aitype;

import com.google.common.collect.Lists;
import me.mrdaniel.npcs.ai.pattern.AIPatternGuardPatrol;
import me.mrdaniel.npcs.ai.pattern.AIPatternGuardRandom;
import me.mrdaniel.npcs.ai.pattern.AIPatternStay;
import me.mrdaniel.npcs.ai.pattern.AIPatternWander;

import java.util.List;

public final class AITypes {

	public static final AIType GUARD_RANDOM = new AIType("Guard Random", "guard_random", 50, AIPatternGuardRandom.class);
	public static final AIType GUARD_PATROL = new AIType("Guard Patrol", "guard_patrol", 6, AIPatternGuardPatrol.class);
	public static final AIType WANDER = new AIType("Wander", "wander", 50, AIPatternWander.class);
	public static final AIType STAY = new AIType("Stay", "stay", 0, AIPatternStay.class);

	public static final List<AIType> ALL = Lists.newArrayList(GUARD_RANDOM, GUARD_PATROL, WANDER, STAY);
}
