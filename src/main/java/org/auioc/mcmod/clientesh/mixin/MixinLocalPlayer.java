package org.auioc.mcmod.clientesh.mixin;

import org.auioc.mcmod.clientesh.event.CEForgeEventFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.client.player.LocalPlayer;

@Mixin(value = LocalPlayer.class)
public class MixinLocalPlayer {

    @Shadow
    private int permissionLevel;

    @Inject(
        method = "Lnet/minecraft/client/player/LocalPlayer;setPermissionLevel(I)V",
        at = @At(value = "HEAD"),
        require = 1,
        allow = 1
    )
    private void setPermissionLevel(int p_108649_, CallbackInfo ci) {
        CEForgeEventFactory.onPermissionLevelChanged(((LocalPlayer) (Object) this), this.permissionLevel, p_108649_);
    }

}
