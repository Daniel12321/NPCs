package me.mrdaniel.npcs.catalogtypes.rabbittype;

import com.google.common.collect.Lists;

import java.util.List;

public class RabbitTypes {

	public static final RabbitType BROWN = new RabbitType("Brown", "brown", 0);
	public static final RabbitType WHITE = new RabbitType("White", "white", 1);
	public static final RabbitType BLACK = new RabbitType("Black", "black", 2);
	public static final RabbitType BLACK_AND_WHITE = new RabbitType("BlackAndWhite", "black_and_white", 3);
	public static final RabbitType GOLD = new RabbitType("Gold", "gold", 4);
	public static final RabbitType SALT_AND_PEPPER = new RabbitType("SaltAndPepper", "salt_and_pepper", 5);
	public static final RabbitType KILLER = new RabbitType("Killer", "killer", 99);

	public static final List<RabbitType> ALL = Lists.newArrayList(
			RabbitTypes.BROWN, RabbitTypes.WHITE, RabbitTypes.BLACK, RabbitTypes.BLACK_AND_WHITE,
			RabbitTypes.GOLD, RabbitTypes.SALT_AND_PEPPER, RabbitTypes.KILLER
	);
}
