package org.auioc.mcmod.clientesh.mixin;

import org.auioc.mcmod.clientesh.content.tweak.SpyglassTweaks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import net.minecraft.client.player.AbstractClientPlayer;

@Mixin(value = AbstractClientPlayer.class)
public abstract class MixinAbstractClientPlayer {

    @Inject(
        method = "Lnet/minecraft/client/player/AbstractClientPlayer;getFieldOfViewModifier()F",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/player/AbstractClientPlayer;isScoping()Z",
            shift = At.Shift.BY,
            by = 2
        ),
        require = 1,
        allow = 1,
        cancellable = true
    )
    private void getFieldOfViewModifier(CallbackInfoReturnable<Float> cir) {
        cir.setReturnValue(SpyglassTweaks.getFovModifier());
    }

}
