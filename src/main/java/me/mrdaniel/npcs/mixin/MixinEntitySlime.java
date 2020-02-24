package me.mrdaniel.npcs.mixin;

import me.mrdaniel.npcs.interfaces.mixin.INPCMixin;
import net.minecraft.entity.monster.EntitySlime;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntitySlime.class)
public class MixinEntitySlime implements INPCMixin {

    @Inject(method = "canDamagePlayer", at = @At("RETURN"), cancellable = true)
    public void onCanDamagePlayer(CallbackInfoReturnable<Boolean> cir) {
        if (this.isNPC() && cir.getReturnValue()) {
            cir.setReturnValue(false);
        }
    }
}