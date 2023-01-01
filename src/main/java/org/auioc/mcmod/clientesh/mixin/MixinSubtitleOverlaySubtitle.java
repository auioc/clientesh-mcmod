package org.auioc.mcmod.clientesh.mixin;

import org.auioc.mcmod.clientesh.api.mixin.IMixinSubtitleOverlaySubtitle;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import net.minecraft.client.gui.components.SubtitleOverlay;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;

@Mixin(value = SubtitleOverlay.Subtitle.class)
public class MixinSubtitleOverlaySubtitle implements IMixinSubtitleOverlaySubtitle {

    @Shadow
    @Final
    private Component text;

    private SoundSource source;

    @Override
    public void setSource(SoundSource source) {
        this.source = source;
    }

    @Override
    public SoundSource getSource() {
        return this.source;
    }

}
