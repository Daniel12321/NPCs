package me.mrdaniel.npcs.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import me.mrdaniel.npcs.interfaces.mixin.NPCAble;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

@Mixin(value = EntityLivingBase.class, priority = 1000)
public abstract class MixinEntityLivingBase extends Entity {

	public MixinEntityLivingBase(World worldIn) {
		super(worldIn);
	}

	@Shadow public abstract boolean isChild();

	@Overwrite
	protected boolean canDropLoot() {
        return !this.isChild() || this.isNPC();
    }

	@Inject(method = "attackEntityFrom", at = @At("HEAD"), cancellable = true)
	public void onAttackEntityFrom(final DamageSource source, final float amount, CallbackInfoReturnable<Boolean> cir) {
		if (this.isNPC()) {
			cir.setReturnValue(false);
		}
	}

	@Inject(method = "canBreatheUnderwater", at = @At("RETURN"), cancellable = true)
	public void onCanBreatheUnderwater(final CallbackInfoReturnable<Boolean> cir) {
		if (this.isNPC() && cir.getReturnValue() != true) {
			cir.setReturnValue(true);
		}
	}

	@Inject(method = "collideWithEntity", at = @At("HEAD"), cancellable = true)
	protected void onCollideWithEntity(Entity entityIn, CallbackInfo ci) {
		if (this.isNPC()) {
			ci.cancel();
		}
	}

	@Inject(method = "canBeCollidedWith", at = @At("RETURN"), cancellable = true)
	public void onCanBeCollidedWith(final CallbackInfoReturnable<Boolean> cir) {
		if (this.isNPC() && cir.getReturnValue() != false) {
			cir.setReturnValue(false);
		}
	}

	@Inject(method = "canBePushed", at = @At("RETURN"), cancellable = true)
    public void onCanBePushed(final CallbackInfoReturnable<Boolean> cir) {
		if (this.isNPC() && cir.getReturnValue() != false) {
			cir.setReturnValue(false);
		}
	}

	@Inject(method = "canBeHitWithPotion", at = @At("RETURN"), cancellable = true)
	public void onCanBeHitWithPotion(final CallbackInfoReturnable<Boolean> cir) {
		if (this.isNPC() && cir.getReturnValue() != false) {
			cir.setReturnValue(false);
		}
	}

	private boolean isNPC() {
		if (!(this instanceof NPCAble)) { return false; }
		return ((NPCAble)this).getNPCFile() != null;
	}
}