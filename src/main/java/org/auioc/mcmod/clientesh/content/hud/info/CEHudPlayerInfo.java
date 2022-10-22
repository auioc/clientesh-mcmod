package org.auioc.mcmod.clientesh.content.hud.info;

import org.auioc.mcmod.clientesh.api.hud.HudInfo;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
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
    public static final HudInfo FROZEN_TICKS = HudInfo.create("FROZEN_TICKS", FrozenTicksC::build, CEHudPlayerInfo::frozenTicks);
    public static final HudInfo ATTACK_COOLDOWN = HudInfo.create("ATTACK_COOLDOWN", AttackCooldownC::build, CEHudPlayerInfo::attackCooldown);
    public static final HudInfo REMAINING_FIRE_TICKS = HudInfo.create("REMAINING_FIRE_TICKS", RemainingFireTicksC::build, CEHudPlayerInfo::remainingFireTicks);
    public static final HudInfo HEALTH = HudInfo.create("HEALTH", HealthC::build, CEHudPlayerInfo::health);
    public static final HudInfo HUNGER = HudInfo.create("HUNGER", HungerC::build, CEHudPlayerInfo::hunger);
    public static final HudInfo ARMOR = HudInfo.create("ARMOR", ArmorC::build, CEHudPlayerInfo::armor);
    public static final HudInfo ARMOR_TOUGHNESS = HudInfo.create("ARMOR_TOUGHNESS", ArmorToughnessC::build, CEHudPlayerInfo::armorToughness);
    public static final HudInfo EXPERIENCE = HudInfo.create("EXPERIENCE", ExperienceC::build, CEHudPlayerInfo::experience);

    // ============================================================================================================== //
    //#region supplier

    private static Component[] airSupply() {
        int current = e().getAirSupply();
        int max = e().getMaxAirSupply();
        return (AirSupplyC.hideIfFull.get() && current == max) ? lines() : lines(label("air_supply").append(format(AirSupplyC.format, current, max)));
    }

    private static Component[] frozenTicks() {
        int current = e().getTicksFrozen();
        int required = e().getTicksRequiredToFreeze();
        return (FrozenTicksC.hideIfZero.get() && current == 0) ? lines() : lines(label("frozen_ticks").append(format(FrozenTicksC.format, current, required)));
    }

    private static Component[] attackCooldown() {
        float s = p().getAttackStrengthScale(0.0F);
        return (AttackCooldownC.hideIfFull.get() && s == 1.0F) ? lines() : lines(label("attack_cooldown").append(format(AttackCooldownC.format, s * 100.0F)));
    }

    private static Component[] remainingFireTicks() {
        int t = p().getRemainingFireTicks();
        return (RemainingFireTicksC.hideIfZero.get() && t <= 0) ? lines() : lines(label("remaining_fire_ticks").append(format(RemainingFireTicksC.format, t)));
    }

    private static Component health() {
        return label("health").append(format(HealthC.format, p().getHealth(), p().getMaxHealth()));
    }

    private static Component hunger() {
        var food = p().getFoodData();
        return label("hunger").append(format(HungerC.format, food.getFoodLevel(), food.getSaturationLevel(), food.getExhaustionLevel()));
    }

    private static Component[] armor() {
        double current = p().getAttributeValue(Attributes.ARMOR);
        double max = ((RangedAttribute) Attributes.ARMOR).getMaxValue();
        return (ArmorC.hideIfZero.get() && current == 0.0D) ? lines() : lines(label("armor").append(format(ArmorC.format, current, max)));
    }

    private static Component[] armorToughness() {
        double current = p().getAttributeValue(Attributes.ARMOR_TOUGHNESS);
        double max = ((RangedAttribute) Attributes.ARMOR_TOUGHNESS).getMaxValue();
        return (ArmorToughnessC.hideIfZero.get() && current == 0.0D) ? lines() : lines(label("armor_toughness").append(format(ArmorToughnessC.format, current, max)));
    }

    private static Component experience() {
        int needed = p().getXpNeededForNextLevel();
        return label("experience").append(format(ExperienceC.format, p().experienceLevel, Math.round(needed * p().experienceProgress), needed));
    }

    //#endregion supplier

    // ============================================================================================================== //
    //#region config

    private static class AirSupplyC {
        public static BooleanValue hideIfFull;
        public static ConfigValue<String> format;

        public static void build(final Builder b) {
            hideIfFull = b.define("hide_if_full", false);
            format = b.comment("1d: current", "2d: max").define("format", "%1$d / %2$d");
        }
    }

    private static class FrozenTicksC {
        public static BooleanValue hideIfZero;
        public static ConfigValue<String> format;

        public static void build(final Builder b) {
            hideIfZero = b.define("hide_if_zero", false);
            format = b.comment("1d: current", "2d: required").define("format", "%1$d / %2$d");
        }
    }

    private static class AttackCooldownC {
        public static BooleanValue hideIfFull;
        public static ConfigValue<String> format;

        public static void build(final Builder b) {
            hideIfFull = b.define("hide_if_full", false);
            format = b.comment("1f: percent").define("format", "%.1f%%");
        }
    }

    private static class RemainingFireTicksC {
        public static BooleanValue hideIfZero;
        public static ConfigValue<String> format;

        public static void build(final Builder b) {
            hideIfZero = b.define("hide_if_zero", false);
            format = b.comment("1d: ticks (requires hulsealib)").define("format", "%d");
        }
    }

    private static class HealthC {
        public static ConfigValue<String> format;

        public static void build(final Builder b) {
            format = b.comment("1f: current", "2f: max").define("format", "%1$.1f / %2$.1f");
        }
    }

    private static class HungerC {
        public static ConfigValue<String> format;

        public static void build(final Builder b) {
            format = b.comment("1d: food level", "2f: food saturation level (requires hulsealib)", "3f: food exhaustion level (requires hulsealib)").define("format", "%1$d, %2$.1f, %3$.3f");
        }
    }

    private static class ArmorC {
        public static BooleanValue hideIfZero;
        public static ConfigValue<String> format;

        public static void build(final Builder b) {
            hideIfZero = b.define("hide_if_zero", false);
            format = b.comment("1f: current", "2f: max").define("format", "%1$.1f / %2$.1f");
        }
    }

    private static class ArmorToughnessC {
        public static BooleanValue hideIfZero;
        public static ConfigValue<String> format;

        public static void build(final Builder b) {
            hideIfZero = b.define("hide_if_zero", false);
            format = b.comment("1f: current", "2f: max").define("format", "%1$.1f / %2$.1f");
        }
    }

    private static class ExperienceC {
        public static ConfigValue<String> format;

        public static void build(final Builder b) {
            format = b.comment("1d: levels", "2d: points", "3d: points needed for next level").define("format", "%1$d, %2$d / %3$d");
        }
    }

    //#endregion config

}
