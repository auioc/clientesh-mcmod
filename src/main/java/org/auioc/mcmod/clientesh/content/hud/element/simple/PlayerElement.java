package org.auioc.mcmod.clientesh.content.hud.element.simple;

import org.auioc.mcmod.clientesh.api.hud.element.AbsHudElement.DoubleElement;
import org.auioc.mcmod.clientesh.api.hud.element.AbsHudElement.IntegerElement;
import org.auioc.mcmod.clientesh.api.hud.element.IHudElement;
import com.google.gson.JsonObject;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class PlayerElement {

    public static IHudElement airSupply(JsonObject json) {
        return new IntegerElement(json) {
            @Override
            public int intValue(int i) { return player.getAirSupply(); }
        };
    }

    public static IHudElement maxAirSupply(JsonObject json) {
        return new IntegerElement(json) {
            @Override
            public int intValue(int i) { return player.getMaxAirSupply(); }
        };
    }

    public static IHudElement frozenTicks(JsonObject json) {
        return new IntegerElement(json) {
            @Override
            public int intValue(int i) { return player.getTicksFrozen(); }
        };
    }

    public static IHudElement ticksRequiredToFreeze(JsonObject json) {
        return new IntegerElement(json) {
            @Override
            public int intValue(int i) { return player.getTicksRequiredToFreeze(); }
        };
    }

    public static IHudElement attackCooldown(JsonObject json) {
        return new DoubleElement(json) {
            @Override
            public double doubleValue(double d) { return player.getAttackStrengthScale(0.0F) * 100.0D; }
        };
    }

    public static IHudElement health(JsonObject json) {
        return new DoubleElement(json) {
            @Override
            public double doubleValue(double d) { return player.getHealth(); }
        };
    }

    public static IHudElement maxHealth(JsonObject json) {
        return new DoubleElement(json) {
            @Override
            public double doubleValue(double d) { return player.getMaxHealth(); }
        };
    }

    public static IHudElement foodLevel(JsonObject json) {
        return new IntegerElement(json) {
            @Override
            public int intValue(int i) { return player.getFoodData().getFoodLevel(); }
        };
    }

    public static IHudElement saturationLevel(JsonObject json) {
        return new DoubleElement(json) {
            @Override
            public double doubleValue(double d) { return player.getFoodData().getSaturationLevel(); }
        };
    }

    public static IHudElement exhaustionLevel(JsonObject json) {
        return new DoubleElement(json) {
            @Override
            public double doubleValue(double d) { return player.getFoodData().getExhaustionLevel(); }
        };
    }

    public static IHudElement armor(JsonObject json) {
        return new DoubleElement(json) {
            @Override
            public double doubleValue(double d) { return player.getAttributeValue(Attributes.ARMOR); }
        };
    }

    public static IHudElement maxArmor(JsonObject json) {
        return new DoubleElement(json) {
            @Override
            public double doubleValue(double d) { return ((RangedAttribute) Attributes.ARMOR).getMaxValue(); }
        };
    }

    public static IHudElement armorToughness(JsonObject json) {
        return new DoubleElement(json) {
            @Override
            public double doubleValue(double d) { return player.getAttributeValue(Attributes.ARMOR_TOUGHNESS); }
        };
    }

    public static IHudElement maxArmorToughness(JsonObject json) {
        return new DoubleElement(json) {
            @Override
            public double doubleValue(double d) { return ((RangedAttribute) Attributes.ARMOR_TOUGHNESS).getMaxValue(); }
        };
    }

    public static IHudElement experienceLevel(JsonObject json) {
        return new IntegerElement(json) {
            @Override
            public int intValue(int i) { return player.experienceLevel; }
        };
    }

    public static IHudElement experiencePoint(JsonObject json) {
        return new IntegerElement(json) {
            @Override
            public int intValue(int i) { return Math.round(player.getXpNeededForNextLevel() * player.experienceProgress); }
        };
    }

    public static IHudElement experiencePointNeeded(JsonObject json) {
        return new IntegerElement(json) {
            @Override
            public int intValue(int i) { return player.getXpNeededForNextLevel(); }
        };
    }

    public static IHudElement statusEffectCount(JsonObject json) {
        return new IntegerElement(json) {
            @Override
            public int intValue(int i) { return player.getActiveEffects().size(); }
        };
    }

}
