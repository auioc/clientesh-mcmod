package org.auioc.mcmod.clientesh.content.widget;

import org.auioc.mcmod.arnicalib.game.config.ConfigUtils;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;

@OnlyIn(Dist.CLIENT)
public class CEWidgetsConfig {

    public static BooleanValue enableExplosionCountdown;

    public static void build(final ForgeConfigSpec.Builder b) {
        ConfigUtils.push(b, "additional_tooltip", AdditionalItemTooltip.Config::build);
        enableExplosionCountdown = b.define("enable_explosion_countdown", true);
    }

}
