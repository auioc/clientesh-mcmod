package org.auioc.mcmod.clientesh.content.hud.info;

import org.auioc.mcmod.clientesh.api.hud.HudInfo;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.Builder;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;

@OnlyIn(Dist.CLIENT)
public class CEHudPlayerInfo extends CEHudInfo {

    protected static void init() {}

    // ============================================================================================================== //

    public static final HudInfo PLAYER_AIR_SUPPLY = HudInfo.create("PLAYER_AIR_SUPPLY", PlayerAirSupplyRC::build, CEHudPlayerInfo::playerAirSupply);

    // ============================================================================================================== //
    //#region supplier

    private static Component[] playerAirSupply() {
        int current = e().getAirSupply();
        int max = e().getMaxAirSupply();
        return (PlayerAirSupplyRC.hideIfFull.get() && current == max) ? lines() : lines(label("player_air_supply").append(format(PlayerAirSupplyRC.format.get(), current, max)));
    }

    //#endregion supplier

    // ============================================================================================================== //
    //#region config

    private static class PlayerAirSupplyRC {
        public static BooleanValue hideIfFull;
        public static ConfigValue<String> format;

        public static void build(final Builder b) {
            hideIfFull = b.define("hide_if_full", false);
            format = b
                .comment("1: current", "2: max")
                .define("format", "%1$d / %2$d");
        }
    }

    //#endregion config

}
