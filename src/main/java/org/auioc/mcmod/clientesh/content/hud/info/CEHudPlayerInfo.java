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

    public static final HudInfo AIR_SUPPLY = HudInfo.create("AIR_SUPPLY", AirSupplyC::build, CEHudPlayerInfo::airSupply);
    public static final HudInfo ATTACK_COOLDOWN = HudInfo.create("ATTACK_COOLDOWN", AttackCooldownC::build, CEHudPlayerInfo::attackCooldown);

    // ============================================================================================================== //
    //#region supplier

    private static Component[] airSupply() {
        int current = e().getAirSupply();
        int max = e().getMaxAirSupply();
        return (AirSupplyC.hideIfFull.get() && current == max) ? lines() : lines(label("air_supply").append(format(AirSupplyC.format.get(), current, max)));
    }

    private static Component[] attackCooldown() {
        float s = MC.player.getAttackStrengthScale(0.0F);
        return (AttackCooldownC.hideIfFull.get() && s == 1.0F) ? lines() : lines(label("attack_cooldown").append(format(AttackCooldownC.format.get(), s * 100.0F)));
    }

    //#endregion supplier

    // ============================================================================================================== //
    //#region config

    private static class AirSupplyC {
        public static BooleanValue hideIfFull;
        public static ConfigValue<String> format;

        public static void build(final Builder b) {
            hideIfFull = b.define("hide_if_full", false);
            format = b.comment("1: current", "2: max").define("format", "%1$d / %2$d");
        }
    }

    private static class AttackCooldownC {
        public static BooleanValue hideIfFull;
        public static ConfigValue<String> format;

        public static void build(final Builder b) {
            hideIfFull = b.define("hide_if_full", false);
            format = b.define("format", "%.1f%%");
        }
    }

    //#endregion config

}
