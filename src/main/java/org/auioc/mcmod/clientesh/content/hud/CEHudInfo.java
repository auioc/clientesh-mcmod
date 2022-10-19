package org.auioc.mcmod.clientesh.content.hud;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.auioc.mcmod.arnicalib.game.chat.TextUtils;
import org.auioc.mcmod.arnicalib.game.world.position.SpeedUnit;
import org.auioc.mcmod.clientesh.ClientEsh;
import org.auioc.mcmod.clientesh.api.hud.HudInfo;
import org.auioc.mcmod.clientesh.api.mixin.IMixinMinecraft;
import org.auioc.mcmod.clientesh.content.hud.CEHudConfig.CoordinatesRC;
import org.auioc.mcmod.clientesh.content.hud.CEHudConfig.SpeedRC;
import org.auioc.mcmod.clientesh.content.hud.CEHudConfig.SystemTimeRC;
import org.auioc.mcmod.clientesh.content.hud.CEHudConfig.VelocityRC;
import net.minecraft.SharedConstants;
import net.minecraft.client.ClientBrandRetriever;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Option;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

public class CEHudInfo {

    // ============================================================================================================== //

    public static final HudInfo MINECRAFT_VERSION = HudInfo.create("MINECRAFT_VERSION", CEHudInfo::minecraftVersion);
    public static final HudInfo FPS = HudInfo.create("FPS", CEHudInfo::fps);
    public static final HudInfo COORDINATES = HudInfo.create("COORDINATES", CoordinatesRC::build, CEHudInfo::coordinates);
    public static final HudInfo SPEED = HudInfo.create("SPEED", SpeedRC::build, CEHudInfo::speed);
    public static final HudInfo VELOCITY = HudInfo.create("VELOCITY", VelocityRC::build, CEHudInfo::velocity);
    public static final HudInfo SYSTEM_TIME = HudInfo.create("SYSTEM_TIME", SystemTimeRC::build, CEHudInfo::systemTime);

    // ============================================================================================================== //

    public static void init() {}

    private static final Minecraft MC = Minecraft.getInstance();

    private static MutableComponent label(String key) {
        return TextUtils.translatable(ClientEsh.i18n("hud.label.") + key);
    }

    private static MutableComponent str(String str) {
        return TextUtils.literal(str);
    }

    private static MutableComponent format(String format, Object... args) {
        return TextUtils.literal(String.format(format, args));
    }

    private static Entity e() {
        return MC.cameraEntity;
    }

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

    private static Component coordinates() {
        return label("coordinates").append(format(CoordinatesRC.format.get(), e().getX(), e().getY(), e().getZ()));
    }

    private static Vec3 getVelocity(SpeedUnit unit) {
        var e = e();
        double vX = unit.convertFrom(e.getX() - e.xOld);
        double vY = unit.convertFrom(e.getY() - e.yOld);
        double vZ = unit.convertFrom(e.getZ() - e.zOld);
        return new Vec3(vX, vY, vZ);
    }

    private static Component speed() {
        var unit = SpeedRC.unit.get();
        return label("speed").append(format(SpeedRC.format.get(), getVelocity(unit).length(), unit.getSymbol()));
    }

    private static Component velocity() {
        var unit = VelocityRC.unit.get();
        var v = getVelocity(unit);
        return label("velocity").append(format(VelocityRC.format.get(), v.x, v.y, v.z, unit.getSymbol()));
    }

    private static Component systemTime() {
        return label("system_time").append(new SimpleDateFormat(SystemTimeRC.format.get()).format(new Date(System.currentTimeMillis())));
    }

}
