package org.auioc.mcmod.clientesh.mixin;

import org.auioc.mcmod.clientesh.api.mixin.IMixinSubtitleOverlaySubtitle;
import org.auioc.mcmod.clientesh.content.widget.SubtitleHighlight;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import net.minecraft.client.gui.components.SubtitleOverlay;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.phys.Vec3;

@Mixin(value = SubtitleOverlay.Subtitle.class)
public class MixinSubtitleOverlaySubtitle implements IMixinSubtitleOverlaySubtitle {

    @Shadow
    @Final
    private Component text;
    @Shadow
    private Vec3 location;

    private SoundSource source;
    private ResourceLocation soundEvent;

    @Override
    public void setSource(SoundSource source) {
        this.source = source;
    }

    @Override
    public SoundSource getSource() {
        return this.source;
    }

    @Override
    public void setSoundEvent(ResourceLocation sound) {
        this.soundEvent = sound;
    }

    @Override
    public ResourceLocation getSoundEvent() {
        return this.soundEvent;
    }

    @Overwrite
    public Component getText() {
        return SubtitleHighlight.highlight(text, soundEvent, source, location);
    }

}
