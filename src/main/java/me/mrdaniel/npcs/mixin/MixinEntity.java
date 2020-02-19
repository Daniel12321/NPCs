package me.mrdaniel.npcs.mixin;

import me.mrdaniel.npcs.interfaces.mixin.NPCAble;
import net.minecraft.entity.Entity;
import net.minecraft.util.DamageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = Entity.class, priority = 10)
public abstract class MixinEntity {

	@Inject(method = "setFire", at = @At("HEAD"), cancellable = true)
	public void onSetFire(final int seconds, CallbackInfo ci) {
		if (this.isNPC()) {
			ci.cancel();
		}
	}

	@Inject(method = "isPushedByWater", at = @At("RETURN"), cancellable = true)
	public void onIsPushedByWater(final CallbackInfoReturnable<Boolean> cir) {
		if (this.isNPC() && cir.getReturnValue() != false) {
			cir.setReturnValue(false);
		}
    }

	@Inject(method = "isEntityInvulnerable", at = @At("RETURN"), cancellable = true)
	public void onIsEntityInvulnerable(final DamageSource source, final CallbackInfoReturnable<Boolean> cir) {
		if (this.isNPC() && cir.getReturnValue() != true) {
			cir.setReturnValue(true);
		}
	}

	private boolean isNPC() {
		if (!(this instanceof NPCAble)) {
			return false;
		}
		return ((NPCAble)this).getNPCFile() != null;
	}
}
