package org.auioc.mcmod.clientesh.content.hud.element;

import org.auioc.mcmod.clientesh.ClientEsh;
import org.auioc.mcmod.clientesh.api.hud.element.DefaultHudElement;
import org.auioc.mcmod.clientesh.api.hud.element.HudElementTypeRegistry;
import org.auioc.mcmod.clientesh.api.hud.element.HudElementTypeRegistry.HudElementEntry;
import org.auioc.mcmod.clientesh.api.hud.element.IHudElementDeserializer;
import org.auioc.mcmod.clientesh.content.hud.element.basic.LiteralElement;
import org.auioc.mcmod.clientesh.content.hud.element.basic.TranslatableElement;
import org.auioc.mcmod.clientesh.content.hud.element.complex.ConditionalElement;
import org.auioc.mcmod.clientesh.content.hud.element.complex.FormatterElement;
import org.auioc.mcmod.clientesh.content.hud.element.complex.MultielementElement;
import org.auioc.mcmod.clientesh.content.hud.element.simple.FpsElement;
import org.auioc.mcmod.clientesh.content.hud.element.simple.LevelElement;
import org.auioc.mcmod.clientesh.content.hud.element.simple.PlayerElement;
import org.auioc.mcmod.clientesh.content.hud.element.simple.VersionElement;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public final class CEHudElements {

    public static void register() {}

    // ============================================================================================================== //

    @Deprecated
    public static final HudElementEntry DEFAULT = register("default", (j) -> new DefaultHudElement());

    public static final HudElementEntry LITERAL = register("literal", LiteralElement::new);
    public static final HudElementEntry TRANSLATABLE = register("translatable", TranslatableElement::new);

    public static final HudElementEntry MULTIELEMENT = register("multielement", MultielementElement::new);
    public static final HudElementEntry FORMATTER = register("formatter", FormatterElement::new);
    public static final HudElementEntry CONDITIONAL = register("conditional", ConditionalElement::new);

    public static final HudElementEntry FPS_CURRENT = register("fps", FpsElement::current);
    public static final HudElementEntry FPS_LIMIT = register("fps_limit", FpsElement::limit);
    public static final HudElementEntry VSYNC = register("vsync", FpsElement::vsync);

    public static final HudElementEntry CURRENT_VERSION = register("current_version", VersionElement::currentVersion);
    public static final HudElementEntry LAUNCHED_VERSION = register("launched_version", VersionElement::launchedVersion);
    public static final HudElementEntry CLIENT_MOD_NAME = register("client_mod_name", VersionElement::clientModName);
    public static final HudElementEntry VERSION_TYPE = register("version_type", VersionElement::versionType);

    public static final HudElementEntry SKY_LIGHT = register("sky_light", LevelElement::skyLight);
    public static final HudElementEntry BLOCK_LIGHT = register("block_light", LevelElement::blockLight);
    public static final HudElementEntry BIOME_ID = register("biome_id", LevelElement::biomeId);
    public static final HudElementEntry BIOME_NAME = register("biome_name", LevelElement::biomeName);
    public static final HudElementEntry DIMENSION_ID = register("dimension_id", LevelElement::dimensionId);
    public static final HudElementEntry DIMENSION_NAME = register("dimension_name", LevelElement::dimensionName);

    public static final HudElementEntry AIR_SUPPLY = register("air_supply", PlayerElement::airSupply);
    public static final HudElementEntry MAX_AIR_SUPPLY = register("max_air_supply", PlayerElement::maxAirSupply);
    public static final HudElementEntry FROZEN_TICKS = register("frozen_ticks", PlayerElement::frozenTicks);
    public static final HudElementEntry TICKS_REQUIRED_TO_FREEZE = register("ticks_required_to_freeze", PlayerElement::ticksRequiredToFreeze);
    public static final HudElementEntry ATTACK_COOLDOWN = register("attack_cooldown", PlayerElement::attackCooldown);
    public static final HudElementEntry HEALTH = register("health", PlayerElement::health);
    public static final HudElementEntry MAX_HEALTH = register("max_health", PlayerElement::maxHealth);
    public static final HudElementEntry FOOD_LEVEL = register("food_level", PlayerElement::foodLevel);
    public static final HudElementEntry SATURATION_LEVEL = register("saturation_level", PlayerElement::saturationLevel);
    public static final HudElementEntry EXHAUSTION_LEVEL = register("exhaustion_level", PlayerElement::exhaustionLevel);
    public static final HudElementEntry ARMOR = register("armor", PlayerElement::armor);
    public static final HudElementEntry MAX_ARMOR = register("max_armor", PlayerElement::maxArmor);
    public static final HudElementEntry ARMOR_TOUGHNESS = register("armor_toughness", PlayerElement::armorToughness);
    public static final HudElementEntry MAX_ARMOR_TOUGHNESS = register("max_armor_toughness", PlayerElement::maxArmorToughness);
    public static final HudElementEntry EXPERIENCE_LEVEL = register("experience_level", PlayerElement::experienceLevel);
    public static final HudElementEntry EXPERIENCE_POINT = register("experience_point", PlayerElement::experiencePoint);
    public static final HudElementEntry EXPERIENCE_POINT_NEEDED = register("experience_point_needed", PlayerElement::experiencePointNeeded);

    // ============================================================================================================== //

    private static HudElementEntry register(String id, IHudElementDeserializer deserializer) {
        return HudElementTypeRegistry.register(ClientEsh.id(id), deserializer);
    }

}
