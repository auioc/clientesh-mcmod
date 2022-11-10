package org.auioc.mcmod.clientesh.mixin;

import org.auioc.mcmod.clientesh.content.tweak.HudHiddenTweaks;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;

@Mixin(value = GameRenderer.class)
public class MixinGameRenderer {

    @Shadow
    @Final
    private Minecraft minecraft;

    @Inject(
        method = "Lnet/minecraft/client/renderer/GameRenderer;renderItemInHand(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/Camera;F)V",
        at = @At("HEAD")
    )
    private void renderItemInHand_Head(PoseStack p_109121_, Camera p_109122_, float p_109123_, CallbackInfo ci) {
        if (minecraft.options.hideGui && HudHiddenTweaks.ShouldRenderHandEvenHidden()) {
            minecraft.options.hideGui = false;
        }
    }

    @Inject(
        method = "Lnet/minecraft/client/renderer/GameRenderer;renderItemInHand(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/Camera;F)V",
        at = @At("TAIL")
    )
    private void renderItemInHand_Tail(PoseStack p_109121_, Camera p_109122_, float p_109123_, CallbackInfo ci) {
        if (!minecraft.options.hideGui && HudHiddenTweaks.ShouldRenderHandEvenHidden()) {
            minecraft.options.hideGui = true;
        }
    }

}
