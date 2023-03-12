package org.auioc.mcmod.clientesh.mixin;

import org.auioc.mcmod.clientesh.api.mixin.IMixinSubtitleOverlaySubtitle;
import org.auioc.mcmod.clientesh.content.widget.SubtitleHighlight;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;
import net.minecraft.client.gui.components.SubtitleOverlay;
import net.minecraft.client.gui.components.SubtitleOverlay.Subtitle;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.WeighedSoundEvents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.phys.Vec3;

@Mixin(value = SubtitleOverlay.class)
public class MixinSubtitleOverlay {

    @Redirect(
        method = "Lnet/minecraft/client/gui/components/SubtitleOverlay;render(Lcom/mojang/blaze3d/vertex/PoseStack;)V",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/gui/components/SubtitleOverlay$Subtitle;getText()Lnet/minecraft/network/chat/Component;",
            ordinal = 1
        ),
        require = 1,
        allow = 1
    )
    private Component render_RedirectGetSubtitleText(Subtitle subtitle) {
        return SubtitleHighlight.highlight(subtitle);
    }

    // ====================================================================== //

    @ModifyArg(
        method = "Lnet/minecraft/client/gui/components/SubtitleOverlay;render(Lcom/mojang/blaze3d/vertex/PoseStack;)V",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/gui/Font;draw(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/network/chat/Component;FFI)I"
        ),
        index = 1, // component
        require = 1,
        allow = 1
    )
    private Component render_ClearComponentColor(Component component) {
        return SubtitleHighlight.clearComponentColor(component);
    }

    // ============================================================================================================== //

    @Redirect(
        method = "Lnet/minecraft/client/gui/components/SubtitleOverlay;onPlaySound(Lnet/minecraft/client/resources/sounds/SoundInstance;Lnet/minecraft/client/sounds/WeighedSoundEvents;)V",
        at = @At(
            value = "NEW",
            target = "Lnet/minecraft/client/gui/components/SubtitleOverlay$Subtitle;<init>(Lnet/minecraft/network/chat/Component;Lnet/minecraft/world/phys/Vec3;)Lnet/minecraft/client/gui/components/SubtitleOverlay$Subtitle;"
        ),
        require = 1,
        allow = 1
    )
    private Subtitle onPlaySound_RedirectNewSubtitle(Component component, Vec3 vec3, SoundInstance p_94645_, WeighedSoundEvents p_94646_) {
        var subtitle = new Subtitle(component, vec3);
        var mixinSubtitle = (IMixinSubtitleOverlaySubtitle) subtitle;
        mixinSubtitle.setSource(p_94645_.getSource());
        mixinSubtitle.setSoundEvent(p_94646_.getResourceLocation());
        return subtitle;
    }

}
