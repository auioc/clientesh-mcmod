package org.auioc.mcmod.clientesh.content.tweak;

import org.auioc.mcmod.arnicalib.game.input.KeyboardUtils;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class HudHiddenTweaks {

    private static final Minecraft MC = Minecraft.getInstance();

    private static boolean renderHandEvenHidden = false;

    /**
     * @see org.auioc.mcmod.clientesh.mixin.MixinKeyboardHandler
     */
    public static void renderHandEvenHidden() {
        renderHandEvenHidden = KeyboardUtils.isShiftKeyDown() && MC.options.hideGui;
    }

    /**
     * @see org.auioc.mcmod.clientesh.mixin.MixinGameRenderer
     */
    public static boolean ShouldRenderHandEvenHidden() {
        return renderHandEvenHidden;
    }

}
