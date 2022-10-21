package org.auioc.mcmod.clientesh.content.hud.info;

import org.auioc.mcmod.arnicalib.game.chat.TextUtils;
import org.auioc.mcmod.clientesh.ClientEsh;
import org.auioc.mcmod.clientesh.api.hud.HudInfo;
import org.auioc.mcmod.clientesh.api.mixin.IMixinMinecraft;
import net.minecraft.SharedConstants;
import net.minecraft.client.ClientBrandRetriever;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Option;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CEHudInfo {

    // ============================================================================================================== //

    public static final HudInfo MINECRAFT_VERSION = HudInfo.create("MINECRAFT_VERSION", CEHudInfo::minecraftVersion);
    public static final HudInfo FPS = HudInfo.create("FPS", CEHudInfo::fps);

    // ============================================================================================================== //
    //#region f1

    public static void register() {
        CEHudLevelInfo.init();
        CEHudPositionInfo.init();
        CEHudTimeInfo.init();
        CEHudPlayerInfo.init();
    }

    protected static final Minecraft MC = Minecraft.getInstance();

    protected static MutableComponent label(String key) {
        return TextUtils.translatable(ClientEsh.i18n("hud.") + key + ".label");
    }

    protected static String i10n(String key, Object... args) {
        return TextUtils.translatable(key, args).getString();
    }

    protected static String i10n(String key) {
        return i10n(key, TextUtils.NO_ARGS);
    }

    protected static MutableComponent value(String key, String subkey, Object... args) {
        return TextUtils.translatable(ClientEsh.i18n("hud.") + key + ".value." + subkey, args);
    }

    protected static MutableComponent value(String key, String subkey) {
        return value(key, subkey, TextUtils.NO_ARGS);
    }

    protected static String valueString(String key, String subkey, Object... args) {
        return value(key, subkey, args).getString();
    }

    protected static String valueString(String key, String subkey) {
        return value(key, subkey).getString();
    }

    protected static MutableComponent format(String format, Object... args) {
        return TextUtils.literal(String.format(format, args));
    }

    protected static Component[] lines(Component... lines) {
        return lines;
    }

    protected static Entity e() {
        return MC.cameraEntity;
    }

    protected static Level level() {
        return MC.level;
    }

    protected static BlockPos blockpos() {
        return MC.cameraEntity.blockPosition();
    }

    //#endregion f1

    // ============================================================================================================== //

    private static Component minecraftVersion() {
        return format(
            "Minecraft %s (%s/%s%s)",
            SharedConstants.getCurrentVersion().getName(), MC.getLaunchedVersion(), ClientBrandRetriever.getClientModName(),
            ("release".equalsIgnoreCase(MC.getVersionType()) ? "" : "/" + MC.getVersionType())
        );
    }

    private static Component fps() {
        return label("fps").append(
            format(
                "%d%s%s",
                ((IMixinMinecraft) MC).getFps(),
                (double) MC.options.framerateLimit == Option.FRAMERATE_LIMIT.getMaxValue() ? "" : " / " + MC.options.framerateLimit,
                MC.options.enableVsync ? " (vsync)" : ""
            )
        );
    }

}
