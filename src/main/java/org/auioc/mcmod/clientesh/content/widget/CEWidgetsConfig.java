package org.auioc.mcmod.clientesh.content.widget;

import org.auioc.mcmod.arnicalib.game.config.ConfigUtils;
import net.minecraftforge.common.ForgeConfigSpec;

public class CEWidgetsConfig {

    public static void build(final ForgeConfigSpec.Builder b) {
        ConfigUtils.push(b, "additional_tooltip", AdditionalItemTooltip.Config::build);
    }

}
