package org.auioc.mcmod.clientesh.content.hud.element;

import org.auioc.mcmod.clientesh.ClientEsh;
import org.auioc.mcmod.clientesh.api.hud.element.EmptyHudElement;
import org.auioc.mcmod.clientesh.api.hud.element.HudElementTypeRegistry;
import org.auioc.mcmod.clientesh.api.hud.element.HudElementTypeRegistry.HudElementEntry;
import org.auioc.mcmod.clientesh.api.hud.element.IHudElementDeserializer;
import org.auioc.mcmod.clientesh.api.hud.element.NullHudElement;
import org.auioc.mcmod.clientesh.content.hud.element.basic.LiteralElement;
import org.auioc.mcmod.clientesh.content.hud.element.basic.StyleElement;
import org.auioc.mcmod.clientesh.content.hud.element.basic.TranslatableElement;
import org.auioc.mcmod.clientesh.content.hud.element.complex.StatusEffectsElement;
import org.auioc.mcmod.clientesh.content.hud.element.function.*;
import org.auioc.mcmod.clientesh.content.hud.element.simple.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public interface CEHudElements {

    public static void register() {}

    // @formatter:off
    // ============================================================================================================== //

    @Deprecated
    HudElementEntry EMPTY = register("empty", (j) -> new EmptyHudElement());
    @Deprecated
    HudElementEntry NULL  = register("null", (j) -> new NullHudElement());

    HudElementEntry LITERAL      = register("literal",      LiteralElement::new);
    HudElementEntry TRANSLATABLE = register("translatable", TranslatableElement::new);
    HudElementEntry STYLE        = register("style",        StyleElement::new);

    HudElementEntry MULTIELEMENT = register("multielement", MultielementElement::new);
    HudElementEntry FORMATTER    = register("formatter",    FormatterElement::new);
    HudElementEntry CONDITIONAL  = register("conditional",  ConditionalElement::new);
    HudElementEntry MAX          = register("max",          BiFunctionElement.Max::new);
    HudElementEntry MIN          = register("min",          BiFunctionElement.Min::new);
    HudElementEntry ADD          = register("add",          BiFunctionElement.Add::new);
    HudElementEntry SUBTRACT     = register("subtract",     BiFunctionElement.Subtract::new);
    HudElementEntry MULTIPLY     = register("multiply",     BiFunctionElement.Multiply::new);
    HudElementEntry DIVIDE       = register("divide",       BiFunctionElement.Divide::new);
    HudElementEntry TO_INTEGER   = register("to_integer",   SiFunctionElement.ToInt::new);
    HudElementEntry TO_DOUBLE    = register("to_double",    SiFunctionElement.ToDouble::new);

    HudElementEntry FPS_CURRENT = register("fps",       FpsElement::current);
    HudElementEntry FPS_LIMIT   = register("fps_limit", FpsElement::limit);
    HudElementEntry VSYNC       = register("vsync",     FpsElement::vsync);

    HudElementEntry CURRENT_VERSION  = register("current_version",  VersionElement::currentVersion);
    HudElementEntry LAUNCHED_VERSION = register("launched_version", VersionElement::launchedVersion);
    HudElementEntry CLIENT_MOD_NAME  = register("client_mod_name",  VersionElement::clientModName);
    HudElementEntry VERSION_TYPE     = register("version_type",     VersionElement::versionType);

    HudElementEntry SKY_LIGHT      = register("sky_light",      LevelElement::skyLight);
    HudElementEntry SKY_DARKEN     = register("sky_darken",     LevelElement::skyDarken);
    HudElementEntry BLOCK_LIGHT    = register("block_light",    LevelElement::blockLight);
    HudElementEntry BIOME_ID       = register("biome_id",       LevelElement::biomeId);
    HudElementEntry BIOME_NAME     = register("biome_name",     LevelElement::biomeName);
    HudElementEntry DIMENSION_ID   = register("dimension_id",   LevelElement::dimensionId);
    HudElementEntry DIMENSION_NAME = register("dimension_name", LevelElement::dimensionName);
    HudElementEntry WEATHER        = register("weather",        LevelElement::weather);

    HudElementEntry AIR_SUPPLY               = register("air_supply",               PlayerElement::airSupply);
    HudElementEntry MAX_AIR_SUPPLY           = register("max_air_supply",           PlayerElement::maxAirSupply);
    HudElementEntry FROZEN_TICKS             = register("frozen_ticks",             PlayerElement::frozenTicks);
    HudElementEntry TICKS_REQUIRED_TO_FREEZE = register("ticks_required_to_freeze", PlayerElement::ticksRequiredToFreeze);
    HudElementEntry REMAINING_FIRE_TICKS     = register("remaining_fire_ticks",     PlayerElement::remainingFireTicks);
    HudElementEntry ATTACK_COOLDOWN          = register("attack_cooldown",          PlayerElement::attackCooldown);
    HudElementEntry HEALTH                   = register("health",                   PlayerElement::health);
    HudElementEntry MAX_HEALTH               = register("max_health",               PlayerElement::maxHealth);
    HudElementEntry FOOD_LEVEL               = register("food_level",               PlayerElement::foodLevel);
    HudElementEntry SATURATION_LEVEL         = register("saturation_level",         PlayerElement::saturationLevel);
    HudElementEntry EXHAUSTION_LEVEL         = register("exhaustion_level",         PlayerElement::exhaustionLevel);
    HudElementEntry ARMOR                    = register("armor",                    PlayerElement::armor);
    HudElementEntry MAX_ARMOR                = register("max_armor",                PlayerElement::maxArmor);
    HudElementEntry ARMOR_TOUGHNESS          = register("armor_toughness",          PlayerElement::armorToughness);
    HudElementEntry MAX_ARMOR_TOUGHNESS      = register("max_armor_toughness",      PlayerElement::maxArmorToughness);
    HudElementEntry EXPERIENCE_LEVEL         = register("experience_level",         PlayerElement::experienceLevel);
    HudElementEntry EXPERIENCE_POINT         = register("experience_point",         PlayerElement::experiencePoint);
    HudElementEntry EXPERIENCE_POINT_NEEDED  = register("experience_point_needed",  PlayerElement::experiencePointNeeded);
    HudElementEntry STATUS_EFFECT_COUNT      = register("status_effect_count",      PlayerElement::statusEffectCount);

    HudElementEntry STATUS_EFFECTS = register("status_effects", StatusEffectsElement::new);

    HudElementEntry SYSTEM_TIME      = register("system_time",      TimeElement.SystemTimeElement::new);
    HudElementEntry GAME_TIME        = register("game_time",        TimeElement.GameTimeElement::new);
    HudElementEntry GAME_TIME_DAY    = register("game_time_day",    TimeElement::gameTimeDay);
    HudElementEntry GAME_TIME_HOUR   = register("game_time_hour",   TimeElement::gameTimeHour);
    HudElementEntry GAME_TIME_MINUTE = register("game_time_minute", TimeElement::gameTimeMinute);
    HudElementEntry MOONPHASE        = register("moonphase",        TimeElement::moonphase);
    HudElementEntry MOONPHASE_NAME   = register("moonphase_name",   TimeElement::moonphaseName);

    HudElementEntry X                = register("x",                PositionElement::x);
    HudElementEntry Y                = register("y",                PositionElement::y);
    HudElementEntry Z                = register("z",                PositionElement::z);
    HudElementEntry BLOCK_X          = register("block_x",          PositionElement::blockX);
    HudElementEntry BLOCK_Y          = register("block_y",          PositionElement::blockY);
    HudElementEntry BLOCK_Z          = register("block_z",          PositionElement::blockZ);
    HudElementEntry SLIME_CHUNK      = register("slime_chunk",      PositionElement::isSlimeChunk);
    HudElementEntry CHUNK_X          = register("chunk_x",          PositionElement::chunkX);
    HudElementEntry CHUNK_Y          = register("chunk_y",          PositionElement::chunkY);
    HudElementEntry CHUNK_Z          = register("chunk_z",          PositionElement::chunkZ);
    HudElementEntry REGION_LOCAL_X   = register("region_local_x",   PositionElement::regionLocalX);
    HudElementEntry REGION_LOCAL_Z   = register("region_local_z",   PositionElement::regionLocalZ);
    HudElementEntry REGION_X         = register("region_x",         PositionElement::regionX);
    HudElementEntry REGION_Z         = register("region_z",         PositionElement::regionZ);
    HudElementEntry YAW              = register("yaw",              PositionElement::yaw);
    HudElementEntry PITCH            = register("pitch",            PositionElement::pitch);
    HudElementEntry FACING_AXIS      = register("facing_axis",      PositionElement::facingAxis);
    HudElementEntry FACING_DIRECTION = register("facing_direction", PositionElement::facingDirection);

    HudElementEntry SPEED      = register("speed",      SpeedElement::speed);
    HudElementEntry VELOCITY_X = register("velocity_x", SpeedElement::velocityX);
    HudElementEntry VELOCITY_Y = register("velocity_y", SpeedElement::velocityY);
    HudElementEntry VELOCITY_Z = register("velocity_z", SpeedElement::velocityZ);

    HudElementEntry LATENCY = register("latency", ServerElement::latency);
    HudElementEntry TPS     = register("tps",     ServerElement::tps);
    HudElementEntry MSPT    = register("mspt",    ServerElement::mspt);

    // @formatter:on
    // ============================================================================================================== //

    private static HudElementEntry register(String id, IHudElementDeserializer deserializer) {
        return HudElementTypeRegistry.register(ClientEsh.id(id), deserializer);
    }

}
