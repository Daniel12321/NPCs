package me.mrdaniel.npcs.catalogtypes.conditiontype;

import com.google.common.collect.Lists;
import me.mrdaniel.npcs.actions.conditions.ConditionExp;
import me.mrdaniel.npcs.actions.conditions.ConditionItem;
import me.mrdaniel.npcs.actions.conditions.ConditionLevel;
import me.mrdaniel.npcs.actions.conditions.ConditionMoney;

import java.util.List;

public final class ConditionTypes {

	public static final ConditionType ITEM = new ConditionType("Item", "item", ConditionItem::new);
	public static final ConditionType MONEY = new ConditionType("Money", "money", ConditionMoney::new);
	public static final ConditionType LEVEL = new ConditionType("Level", "level", ConditionLevel::new);
	public static final ConditionType EXP = new ConditionType("EXP", "exp", ConditionExp::new);

	public static final List<ConditionType> ALL = Lists.newArrayList(ITEM, MONEY, LEVEL, EXP);
}
