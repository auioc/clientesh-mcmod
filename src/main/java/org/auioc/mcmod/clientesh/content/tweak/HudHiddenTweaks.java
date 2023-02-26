package org.auioc.mcmod.clientesh.content.tweak;

import java.util.function.BooleanSupplier;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;

@OnlyIn(Dist.CLIENT)
public class HudHiddenTweaks {

    private static boolean renderHand;
    private static boolean renderBlockOutline;

    // ====================================================================== //

    /**
     * @see org.auioc.mcmod.clientesh.mixin.MixinKeyboardHandler
     */
    public static void onSwitchHudHidden(Minecraft minecraft) {
        final boolean hideGui = minecraft.options.hideGui;
        renderHand = Config.renderHand.get() && hideGui;
        renderBlockOutline = Config.renderBlockOutline.get() && hideGui;
    }

    public static void preRender(Minecraft minecraft, BooleanSupplier sup) {
        if (minecraft.options.hideGui && sup.getAsBoolean()) {
            minecraft.options.hideGui = false;
        }
    }

    public static void postRender(Minecraft minecraft, BooleanSupplier _sup) {
        if (!minecraft.options.hideGui && _sup.getAsBoolean()) {
            minecraft.options.hideGui = true;
        }
    }

    // ====================================================================== //

    /**
     * @see org.auioc.mcmod.clientesh.mixin.MixinGameRenderer#renderItemInHand_Head
     * @see org.auioc.mcmod.clientesh.mixin.MixinGameRenderer#renderItemInHand_Tail
     */
    public static boolean shouldRenderHand() {
        return renderHand;
    }

    /**
     * @see org.auioc.mcmod.clientesh.mixin.MixinGameRenderer#shouldRenderBlockOutline_Head
     * @see org.auioc.mcmod.clientesh.mixin.MixinGameRenderer#shouldRenderBlockOutline_Tail
     */
    public static boolean shouldRenderBlockOutline() {
        return renderBlockOutline;
    }

    // ============================================================================================================== //

    @OnlyIn(Dist.CLIENT)
    public static class Config {

        public static BooleanValue renderHand;
        public static BooleanValue renderBlockOutline;

        public static void build(final ForgeConfigSpec.Builder b) {
            renderHand = b.define("renderHand", false);
            renderBlockOutline = b.define("renderBlockOutline", false);
        }

    }

}
