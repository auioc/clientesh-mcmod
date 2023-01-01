package org.auioc.mcmod.clientesh.api.mixin;

import net.minecraft.sounds.SoundSource;

public interface IMixinSubtitleOverlaySubtitle {

    void setSource(SoundSource source);

    SoundSource getSource();

}
