package org.auioc.mcmod.clientesh.content.tweak;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderBlockOverlayEvent;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.DoubleValue;

@OnlyIn(Dist.CLIENT)
public class ScreenEffectTweaks {

    public static void handleBlockOverlay(final RenderBlockOverlayEvent event) {
        if (!Config.renderBlockOverlay.get()) event.setCanceled(true);
    }

    public static void handleWaterOverlay(final RenderBlockOverlayEvent event) {
        if (!Config.renderWaterOverlay.get()) event.setCanceled(true);
    }

    public static void handleFireOverlay(final RenderBlockOverlayEvent event) {
        if (!Config.renderfireOverlay.get()) event.setCanceled(true);
    }

    /**
     * @see org.auioc.mcmod.clientesh.mixin.MixinScreenEffectRenderer#renderFire_Translate
     */
    public static double getFireOverlayHeight() {
        return -1.0D + Config.fireOverlayHeight.get();
    }

    /**
     * @see org.auioc.mcmod.clientesh.mixin.MixinScreenEffectRenderer#renderFire_Opacity
     */
    public static float getFireOverlayOpacity() {
        return Config.fireOverlayOpacity.get().floatValue();
    }

    // ============================================================================================================== //

    @OnlyIn(Dist.CLIENT)
    public static class Config {

        public static BooleanValue renderBlockOverlay;
        public static BooleanValue renderWaterOverlay;
        public static BooleanValue renderfireOverlay;
        public static DoubleValue fireOverlayOpacity;
        public static DoubleValue fireOverlayHeight;

        public static void build(final ForgeConfigSpec.Builder b) {
            b.push("block");
            {
                renderBlockOverlay = b
                    .comment("Please do not turn it off, otherwise you will get the Xray ability when your head is inside a block")
                    .define("enabled", true);
            }
            b.pop();
            b.push("water");
            {
                renderWaterOverlay = b.define("enabled", true);
            }
            b.pop();
            b.push("fire");
            {
                renderfireOverlay = b.define("enabled", true);
                fireOverlayOpacity = b.defineInRange("opacity", 0.9D, 0.0D, 1.0D);
                fireOverlayHeight = b.defineInRange("height", 0.7D, 0.0D, 1.0D);
            }
            b.pop();
        }

    }

}
