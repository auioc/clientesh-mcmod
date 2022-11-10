package org.auioc.mcmod.clientesh.mixin;

import org.auioc.mcmod.clientesh.content.tweak.ScreenEffectTweaks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import net.minecraft.client.renderer.ScreenEffectRenderer;

@Mixin(value = ScreenEffectRenderer.class)
public class MixinScreenEffectRenderer {

    @ModifyArg(
        method = "Lnet/minecraft/client/renderer/ScreenEffectRenderer;renderFire(Lnet/minecraft/client/Minecraft;Lcom/mojang/blaze3d/vertex/PoseStack;)V",
        at = @At(
            value = "INVOKE",
            target = "Lcom/mojang/blaze3d/vertex/PoseStack;translate(DDD)V"
        ),
        index = 1 // y
    )
    private static double renderFire_Translate(double p_85839_) {
        return ScreenEffectTweaks.getFireOverlayHeight();
    }

    @ModifyArg(
        method = "Lnet/minecraft/client/renderer/ScreenEffectRenderer;renderFire(Lnet/minecraft/client/Minecraft;Lcom/mojang/blaze3d/vertex/PoseStack;)V",
        at = @At(
            value = "INVOKE",
            target = "Lcom/mojang/blaze3d/vertex/VertexConsumer;color(FFFF)Lcom/mojang/blaze3d/vertex/VertexConsumer;"
        ),
        index = 3 // alpha
    )
    private static float renderFire_Opacity(float p_85954_) {
        return ScreenEffectTweaks.getFireOverlayOpacity();
    }

}
