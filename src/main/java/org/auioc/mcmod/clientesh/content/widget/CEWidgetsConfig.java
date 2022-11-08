package org.auioc.mcmod.clientesh.content.widget;

import org.auioc.mcmod.arnicalib.game.config.ConfigUtils;
import com.electronwill.nightconfig.core.CommentedConfig;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeConfigSpec;

@OnlyIn(Dist.CLIENT)
public class CEWidgetsConfig {

    public static void build(final ForgeConfigSpec.Builder b) {
        ConfigUtils.push(b, "additional_tooltip", AdditionalItemTooltip.Config::build);
        ConfigUtils.push(b, "explosion_countdown", ExplosionCountdown.Config::build);
    }

    public static void onLoad(CommentedConfig config) {
        AdditionalItemTooltip.Config.onLoad(config);
    }

}
