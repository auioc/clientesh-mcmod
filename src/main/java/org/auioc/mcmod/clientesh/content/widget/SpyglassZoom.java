package org.auioc.mcmod.clientesh.content.widget;

import org.auioc.mcmod.clientesh.api.config.CEConfigAt;
import org.auioc.mcmod.clientesh.api.config.CEConfigAt.Type;
import net.minecraft.client.Minecraft;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.DoubleValue;

@OnlyIn(Dist.CLIENT)
public class SpyglassZoom {

    private static final float MIN_FOV_MODIFIER = 0.1F;
    private static final float MAX_FOV_MODIFIER = 1.5F;
    private static float lastFovModifier;
    private static float fovModifier = Config.defaultFovModifier.get().floatValue();

    // ====================================================================== //

    /**
     * @see org.auioc.mcmod.clientesh.mixin.MixinAbstractClientPlayer#getFieldOfViewModifier
     */
    public static float getFovModifier() {
        return fovModifier;
    }

    // ====================================================================== //

    public static boolean onMouseScroll(final Minecraft minecraft, double scrollDelta) {
        if (minecraft.player.isScoping()) {
            lastFovModifier = fovModifier;
            fovModifier = Mth.clamp(
                fovModifier + ((float) scrollDelta * Config.scrollDeltaMultiplier.get().floatValue()),
                MIN_FOV_MODIFIER, MAX_FOV_MODIFIER
            );
            if (fovModifier != lastFovModifier) {
                minecraft.player.playSound(
                    (scrollDelta < 0) ? SoundEvents.SPYGLASS_USE : SoundEvents.SPYGLASS_STOP_USING,
                    1.0F, 1.0F
                );
            }
            return true;
        }
        return false;
    }

    // ============================================================================================================== //

    @OnlyIn(Dist.CLIENT)
    @CEConfigAt(type = Type.WIDGETS, path = "spyglass_zoom")
    public static class Config {

        public static DoubleValue defaultFovModifier;
        public static DoubleValue scrollDeltaMultiplier;

        public static void build(final ForgeConfigSpec.Builder b) {
            defaultFovModifier = b.defineInRange("defaultFovModifier", 0.1D, 0.1D, 1.5D);
            scrollDeltaMultiplier = b.defineInRange("scrollDeltaMultiplier", 0.025D, 0.001D, 1.0D);
        }

    }

}
