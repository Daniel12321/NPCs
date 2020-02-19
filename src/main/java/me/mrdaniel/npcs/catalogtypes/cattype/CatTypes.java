package me.mrdaniel.npcs.catalogtypes.cattype;

import com.google.common.collect.Lists;

import java.util.List;

public class CatTypes {

	public static final CatType WILD = new CatType("Wild", "wild", 0);
	public static final CatType TUXEDO = new CatType("Tuxedo", "tuxedo", 1);
	public static final CatType TABBY = new CatType("Tabby", "tabby", 2);
	public static final CatType SIAMESE = new CatType("Siamese", "siamese", 3);

	public static final List<CatType> ALL = Lists.newArrayList(WILD, TUXEDO, TABBY, SIAMESE);
}
