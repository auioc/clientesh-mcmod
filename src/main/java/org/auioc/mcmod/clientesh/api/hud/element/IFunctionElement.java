package org.auioc.mcmod.clientesh.api.hud.element;

import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public interface IFunctionElement extends IHudElement {

    IHudElement getResult();

    @Override
    public default Component getText() {
        throw new UnsupportedOperationException();
    }

    public static IHudElement reslove(IHudElement element) {
        IHudElement r = element;
        while (true) {
            if (r instanceof IFunctionElement f) {
                r = f.getResult();
            } else {
                return r;
            }
        }
    }

}
