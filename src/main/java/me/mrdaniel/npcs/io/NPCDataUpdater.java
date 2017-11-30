package me.mrdaniel.npcs.io;

import javax.annotation.Nonnull;

import me.mrdaniel.npcs.utils.Position;

public class NPCDataUpdater {

	public static void update(@Nonnull final NPCFile file) {
		file.setPosition(new Position(
				file.getX(),
				file.getY(),
				file.getZ(),
				file.getNode("head", "y").getFloat(),
				file.getNode("head", "x").getFloat()));
		file.getNode().removeChild("head");

		file.getNode("type").setValue(file.getNode("type").getString().replace("minecraft:", "").replace("sponge:", ""));

		String career = file.getNode("career").getString();
		if (career != null) {
			file.getNode("career").setValue(career.replace("minecraft:", ""));
		}

		String cattype = file.getNode("cat").getString();
		if (cattype != null) {
			file.getNode().removeChild("cat");
			file.getNode("cattype").setValue(cattype.replace("_CAT", "").replace("_OCELOT", "").toLowerCase());
		}
	}
}