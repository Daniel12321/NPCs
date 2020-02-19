package me.mrdaniel.npcs.catalogtypes.career;

import org.spongepowered.api.CatalogType;
import org.spongepowered.api.util.annotation.CatalogedBy;

@CatalogedBy(Careers.class)
public class Career implements CatalogType {

	private final String name;
	private final String id;
	private final int professionId;
	private final int careerId;

	Career(String name, String id, int professionId, int careerId) {
		this.name = name;
		this.id = id;
		this.professionId = professionId;
		this.careerId = careerId;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getId() {
		return id;
	}

	public int getProfessionId() {
		return professionId;
	}

	public int getCareerId() {
		return careerId;
	}
}
