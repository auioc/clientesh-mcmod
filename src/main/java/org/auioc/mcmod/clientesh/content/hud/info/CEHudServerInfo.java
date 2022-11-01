package org.auioc.mcmod.clientesh.content.hud.info;

import org.auioc.mcmod.clientesh.api.hud.HudInfo;
import org.auioc.mcmod.clientesh.compat.HulsealibCompat;
import org.auioc.mcmod.clientesh.mixin.MixinAccessorAbstractClientPlayer;
import org.auioc.mcmod.clientesh.mixin.compat.MixinHLAdditionalServerData;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.Builder;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;

@OnlyIn(Dist.CLIENT)
public class CEHudServerInfo extends CEHudInfo {

    protected static void init() {}

    // ============================================================================================================== //

    public static final HudInfo LATENCY = HudInfo.create("LATENCY", LatencyRC::build, CEHudServerInfo::latency);
    public static final HudInfo TICK_TIME = HudInfo.create("TICK_TIME", TickTimeRC::build, CEHudServerInfo::tickTime);

    // ============================================================================================================== //
    //#region supplier

    private static Component[] latency() {
        if (LatencyRC.hideIfSinglePlayer.get() && MC.hasSingleplayerServer()) return lines();
        int latency = -1;
        var playerInfo = ((MixinAccessorAbstractClientPlayer) p()).getPlayerInfoDirectly();
        if (playerInfo != null) latency = playerInfo.getLatency();
        return lines(label("latency").append(format("%d ms", latency)));
    }

    private static Component[] tickTime() {
        double tps = -2.0D, mspt = -2.0D;
        if (HulsealibCompat.FOUND_ADDITIONAL_SERVER_DATA) {
            tps = MixinHLAdditionalServerData.getTps();
            mspt = MixinHLAdditionalServerData.getMspt();
        }
        if (TickTimeRC.hideIfUnavailable.get() && (tps < 0.0D || mspt < 0.0D)) return lines();
        return lines(label("tick_time").append(format(TickTimeRC.format, tps, mspt)));

    }

    //#endregion supplier

    // ============================================================================================================== //
    //#region config

    private static class LatencyRC {
        public static BooleanValue hideIfSinglePlayer;

        public static void build(final Builder b) {
            hideIfSinglePlayer = b.define("hide_if_single_player", false);
        }
    }

    private static class TickTimeRC {
        public static BooleanValue hideIfUnavailable;
        public static ConfigValue<String> format;

        public static void build(final Builder b) {
            hideIfUnavailable = b.define("hide_if_unavailable", false);
            format = b.comment("1f: tps (requires hulsealib)", "2f: mspt (requires hulsealib)").define("format", "tps %.1f, mspt %.3f");
        }
    }


    //#endregion config
}
