package me.mrdaniel.npcs.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import me.mrdaniel.npcs.interfaces.mixin.NPCAble;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;

@Mixin(value = EntityLivingBase.class, priority = 10)
public abstract class MixinEntityLivingBase extends Entity {

	public MixinEntityLivingBase(World worldIn) {
		super(worldIn);
	}

	@Inject(method = "canBePushed", at = @At("RETURN"), cancellable = true)
    public void onCanBePushed(CallbackInfoReturnable<Boolean> cir) {
		if (this.isNPC() && cir.getReturnValue() != false) {
			cir.setReturnValue(false);
		}
	}

	private boolean isNPC() {
		if (!(this instanceof NPCAble)) {
			return false;
		}

		return ((NPCAble)this).getNPCFile() != null;
	}
}
