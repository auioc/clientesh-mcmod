package org.auioc.mcmod.clientesh.mixin;

import org.auioc.mcmod.clientesh.content.tweak.HudHiddenTweaks;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.client.KeyboardHandler;

@Mixin(value = KeyboardHandler.class)
public class MixinKeyboardHandler {

    @Inject(
        method = "Lnet/minecraft/client/KeyboardHandler;keyPress(JIIII)V",
        at = @At(
            value = "FIELD",
            target = "Lnet/minecraft/client/Options;hideGui:Z",
            opcode = Opcodes.PUTFIELD,
            shift = At.Shift.AFTER
        ),
        require = 1,
        allow = 1
    )
    private void keyPress(long p_90894_, int p_90895_, int p_90896_, int p_90897_, int p_90898_, CallbackInfo ci) {
        HudHiddenTweaks.renderHandEvenHidden();
    }

}
