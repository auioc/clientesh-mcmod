package org.auioc.mcmod.clientesh.content.tweak;

import org.auioc.mcmod.clientesh.api.config.CEConfigAt;
import org.auioc.mcmod.clientesh.api.config.CEConfigAt.Type;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;

@OnlyIn(Dist.CLIENT)
public class ItemTweaks {

    /**
     * @deprecated 1.19.4 Pre1
     */
    @Deprecated()
    public static boolean shouldPotionGlint() {
        return Config.potionGlint.get();
    }

    // ============================================================================================================== //

    @OnlyIn(Dist.CLIENT)
    @CEConfigAt(type = Type.TWEAKS, path = "item")
    public static class Config {

        /**
         * @deprecated 1.19.4 Pre1
         */
        @Deprecated()
        public static BooleanValue potionGlint;

        public static void build(final ForgeConfigSpec.Builder b) {
            potionGlint = b.define("potionGlint", true);
        }

    }

}
