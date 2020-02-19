package me.mrdaniel.npcs.catalogtypes.llamatype;

import com.google.common.collect.Lists;

import java.util.List;

public class LlamaTypes {

	public static final LlamaType CREAMY = new LlamaType("Creamy", "creamy", 0);
	public static final LlamaType WHITE = new LlamaType("White", "white", 1);
	public static final LlamaType BROWN = new LlamaType("Brown", "brown", 2);
	public static final LlamaType GRAY = new LlamaType("Gray", "gray", 3);

	public static final List<LlamaType> ALL = Lists.newArrayList(CREAMY, WHITE, BROWN, GRAY);
}
