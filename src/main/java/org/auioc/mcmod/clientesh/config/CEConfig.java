package org.auioc.mcmod.clientesh.config;

import org.auioc.mcmod.arnicalib.game.config.ConfigUtils;
import org.auioc.mcmod.clientesh.api.hud.HudConfig;
import org.auioc.mcmod.clientesh.content.tweak.CETweaksConfig;
import org.auioc.mcmod.clientesh.content.widget.CEWidgetsConfig;
import com.electronwill.nightconfig.core.CommentedConfig;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeConfigSpec;

@OnlyIn(Dist.CLIENT)
public class CEConfig {

    public static final ForgeConfigSpec SPEC;

    static {
        final ForgeConfigSpec.Builder b = new ForgeConfigSpec.Builder();

        ConfigUtils.push(b, "tweaks", CETweaksConfig::build);
        ConfigUtils.push(b, "widgets", CEWidgetsConfig::build);
        ConfigUtils.push(b, "hud", HudConfig::build);

        SPEC = b.build();
    }

    public static void onLoad(CommentedConfig config) {
        HudConfig.onLoad(config);
    }

}
