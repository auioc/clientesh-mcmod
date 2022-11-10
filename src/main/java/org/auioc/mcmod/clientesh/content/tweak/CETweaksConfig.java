package org.auioc.mcmod.clientesh.content.tweak;

import org.auioc.mcmod.arnicalib.game.config.ConfigUtils;
import com.electronwill.nightconfig.core.CommentedConfig;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;

@OnlyIn(Dist.CLIENT)
public class CETweaksConfig {

    public static BooleanValue enablePauseScreenTweaks;

    public static void build(final ForgeConfigSpec.Builder b) {
        enablePauseScreenTweaks = b.define("enablePauseScreenTweaks", true);
        ConfigUtils.push(b, "overlay", OverlayTweaks.Config::build);
        ConfigUtils.push(b, "screen_effect", ScreenEffectTweaks.Config::build);
    }

    public static void onLoad(CommentedConfig config) {
        OverlayTweaks.toggleOverlays();
    }

}
