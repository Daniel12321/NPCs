package me.mrdaniel.npcs.catalogtypes.propertytype.types;

import com.google.common.reflect.TypeToken;
import me.mrdaniel.npcs.catalogtypes.npctype.NPCType;
import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyType;
import me.mrdaniel.npcs.interfaces.mixin.NPCAble;
import net.minecraft.entity.boss.EntityWither;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.text.Text;

public class PropertyWitherInvulnerable extends PropertyType<Boolean> {

	public PropertyWitherInvulnerable() {
		super("Invulnerable", "invulnerable", GenericArguments.bool(Text.of("invulnerable")));
	}

	@Override
	public TypeToken<Boolean> getTypeToken() {
		return TypeToken.of(Boolean.class);
	}

	@Override
	public boolean isSupported(final NPCAble npc) {
		return npc instanceof EntityWither;
	}

	@Override
	public boolean isSupported(NPCType type) {
		return true;
	}

	// TODO: Check if this works
	@Override
	public void apply(NPCAble npc, Boolean value) {
		((EntityWither)npc).setInvulTime(-1);
	}
}
