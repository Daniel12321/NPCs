package me.mrdaniel.npcs.catalogtypes.aitype;

import com.google.common.collect.Lists;
import me.mrdaniel.npcs.catalogtypes.aitype.types.AITypeGuardPatrol;
import me.mrdaniel.npcs.catalogtypes.aitype.types.AITypeGuardRandom;
import me.mrdaniel.npcs.catalogtypes.aitype.types.AITypeStay;
import me.mrdaniel.npcs.catalogtypes.aitype.types.AITypeWander;

import java.util.List;

public final class AITypes {

	public static final AIType GUARD_RANDOM = new AITypeGuardRandom();
	public static final AIType GUARD_PATROL = new AITypeGuardPatrol();
	public static final AIType WANDER = new AITypeWander();
	public static final AIType STAY = new AITypeStay();

	public static final List<AIType> ALL = Lists.newArrayList(GUARD_RANDOM, GUARD_PATROL, WANDER, STAY);
}
