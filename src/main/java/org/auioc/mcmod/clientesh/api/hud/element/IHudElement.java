package org.auioc.mcmod.clientesh.api.hud.element;

import javax.annotation.Nullable;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public interface IHudElement {

    @Nullable
    Component getText();

}
