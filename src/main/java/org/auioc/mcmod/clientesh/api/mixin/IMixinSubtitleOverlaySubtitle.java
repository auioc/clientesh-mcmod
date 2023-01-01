package org.auioc.mcmod.clientesh.api.mixin;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;

public interface IMixinSubtitleOverlaySubtitle {

    void setSource(SoundSource source);

    SoundSource getSource();

    void setSoundEvent(ResourceLocation sound);

    ResourceLocation getSoundEvent();

}
