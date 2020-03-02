package me.mrdaniel.npcs.catalogtypes.aitype;

import com.google.common.collect.Lists;

import java.util.List;

public final class AITypes {

	public static final AIType GUARD_RANDOM = new AIType("Guard Random", "guard_random");
	public static final AIType GUARD_PATROL = new AIType("Guard Patrol", "guard_patrol");
	public static final AIType WANDER = new AIType("Wander", "wander");
	public static final AIType STAY = new AIType("Stay", "stay");

	public static final List<AIType> ALL = Lists.newArrayList(GUARD_RANDOM, GUARD_PATROL, WANDER, STAY);
}
