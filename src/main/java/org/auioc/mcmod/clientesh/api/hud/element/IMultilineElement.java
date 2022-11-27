package org.auioc.mcmod.clientesh.api.hud.element;

import java.util.function.Consumer;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public interface IMultilineElement extends IHudElement {

    Component[] getLines();

    @Override
    public default Component getText() {
        throw new UnsupportedOperationException("Unresolved multiline element");
    }

    public static boolean resolve(IHudElement element, Consumer<IMultilineElement> consumer) {
        if (element instanceof IMultilineElement m) { consumer.accept(m); return true; }
        return false;
    }

}
