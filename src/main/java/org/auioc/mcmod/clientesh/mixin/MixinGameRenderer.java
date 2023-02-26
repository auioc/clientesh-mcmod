package org.auioc.mcmod.clientesh.mixin;

import org.auioc.mcmod.clientesh.content.tweak.HudHiddenTweaks;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;

@Mixin(value = GameRenderer.class)
public class MixinGameRenderer {

    @Shadow
    @Final
    private Minecraft minecraft;

    // ====================================================================== //

    @Inject(
        method = "Lnet/minecraft/client/renderer/GameRenderer;renderItemInHand(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/Camera;F)V",
        at = @At("HEAD")
    )
    private void renderItemInHand_Head(PoseStack p_109121_, Camera p_109122_, float p_109123_, CallbackInfo ci) {
        HudHiddenTweaks.preRender(minecraft, HudHiddenTweaks::shouldRenderHand);
    }

    @Inject(
        method = "Lnet/minecraft/client/renderer/GameRenderer;renderItemInHand(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/Camera;F)V",
        at = @At("TAIL")
    )
    private void renderItemInHand_Tail(PoseStack p_109121_, Camera p_109122_, float p_109123_, CallbackInfo ci) {
        HudHiddenTweaks.postRender(minecraft, HudHiddenTweaks::shouldRenderHand);
    }

    // ====================================================================== //

    @Inject(
        method = "Lnet/minecraft/client/renderer/GameRenderer;shouldRenderBlockOutline()Z",
        at = @At("HEAD")
    )
    private void shouldRenderBlockOutline_Head(CallbackInfoReturnable<Boolean> cir) {
        HudHiddenTweaks.preRender(minecraft, HudHiddenTweaks::shouldRenderBlockOutline);
    }

    @Inject(
        method = "Lnet/minecraft/client/renderer/GameRenderer;shouldRenderBlockOutline()Z",
        at = @At("TAIL")
    )
    private void shouldRenderBlockOutline_Tail(CallbackInfoReturnable<Boolean> cir) {
        HudHiddenTweaks.postRender(minecraft, HudHiddenTweaks::shouldRenderBlockOutline);
    }

}
