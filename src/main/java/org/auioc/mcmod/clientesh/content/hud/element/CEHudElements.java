package org.auioc.mcmod.clientesh.content.hud.element;

import org.auioc.mcmod.clientesh.ClientEsh;
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
import org.auioc.mcmod.clientesh.content.hud.element.simple.VersionElement;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public final class CEHudElements {

    public static void register() {}

    // ============================================================================================================== //

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

    // ============================================================================================================== //

    private static HudElementEntry register(String id, IHudElementDeserializer deserializer) {
        return HudElementTypeRegistry.register(ClientEsh.id(id), deserializer);
    }

}
