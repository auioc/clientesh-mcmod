package org.auioc.mcmod.clientesh.content.hud;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.auioc.mcmod.arnicalib.game.chat.TextUtils;
import org.auioc.mcmod.arnicalib.game.world.MCTimeUtils;
import org.auioc.mcmod.arnicalib.game.world.position.SpeedUnit;
import org.auioc.mcmod.clientesh.ClientEsh;
import org.auioc.mcmod.clientesh.api.hud.HudInfo;
import org.auioc.mcmod.clientesh.api.mixin.IMixinMinecraft;
import org.auioc.mcmod.clientesh.content.adapter.SeedGetter;
import org.auioc.mcmod.clientesh.content.hud.CEHudConfig.*;
import net.minecraft.SharedConstants;
import net.minecraft.client.ClientBrandRetriever;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Option;
import net.minecraft.core.SectionPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CEHudInfo {

    // ============================================================================================================== //

    public static final HudInfo MINECRAFT_VERSION = HudInfo.create("MINECRAFT_VERSION", CEHudInfo::minecraftVersion);
    public static final HudInfo FPS = HudInfo.create("FPS", CEHudInfo::fps);
    public static final HudInfo COORDINATES = HudInfo.create("COORDINATES", CoordinatesRC::build, CEHudInfo::coordinates);
    public static final HudInfo BLOCK_POSITION = HudInfo.create("BLOCK_POSITION", BlockPositionRC::build, CEHudInfo::blockPostion);
    public static final HudInfo CHUNK_POSITION = HudInfo.create("CHUNK_POSITION", ChunkPositionRC::build, CEHudInfo::chunkPostion);
    public static final HudInfo SEED = HudInfo.create("SEED", CEHudInfo::seed);
    public static final HudInfo DIMENSION = HudInfo.create("DIMENSION", CEHudInfo::dimension);
    public static final HudInfo BIOME = HudInfo.create("BIOME", CEHudInfo::biome);
    public static final HudInfo SPEED = HudInfo.create("SPEED", SpeedRC::build, CEHudInfo::speed);
    public static final HudInfo VELOCITY = HudInfo.create("VELOCITY", VelocityRC::build, CEHudInfo::velocity);
    public static final HudInfo SYSTEM_TIME = HudInfo.create("SYSTEM_TIME", SystemTimeRC::build, CEHudInfo::systemTime);
    public static final HudInfo GAME_TIME = HudInfo.create("GAME_TIME", GameTimeRC::build, CEHudInfo::gameTime);

    // ============================================================================================================== //

    public static void init() {}

    private static final Minecraft MC = Minecraft.getInstance();

    private static MutableComponent label(String key) {
        return TextUtils.translatable(ClientEsh.i18n("hud.") + key + ".label");
    }

    private static MutableComponent format(String format, Object... args) {
        return TextUtils.literal(String.format(format, args));
    }

    public static Component[] lines(Component... lines) {
        return lines;
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

    private static Component blockPostion() {
        var pos = e().blockPosition();
        return label("block_postion").append(format(BlockPositionRC.format.get(), pos.getX(), pos.getY(), pos.getZ(), pos.getX() & 15, pos.getY() & 15, pos.getZ() & 15));
    }

    private static Component chunkPostion() {
        var bPos = e().blockPosition();
        var cPos = new ChunkPos(bPos);
        boolean isSlimeChunk = false;
        if (SeedGetter.hasSeed()) isSlimeChunk = WorldgenRandom.seedSlimeChunk(cPos.x, cPos.z, SeedGetter.get(), 987234911L).nextInt(10) == 0;
        return label("chunk_postion").append(
            format(
                ChunkPositionRC.format.get(),
                cPos.x, SectionPos.blockToSectionCoord(bPos.getY()), cPos.z,
                (isSlimeChunk) ? " (slimechunk)" : "",
                cPos.getRegionLocalX(), cPos.getRegionLocalZ(), String.format("r.%d.%d.mca", cPos.getRegionX(), cPos.getRegionZ())
            )
        );
    }

    private static Component[] seed() {
        return (SeedGetter.hasSeed()) ? lines(label("seed").append(format("%s", SeedGetter.get()))) : lines();
    }

    private static Component dimension() {
        return label("dimension").append(e().getLevel().dimension().location().toString());
    }

    private static Component[] biome() {
        var b = e().getLevel().getBiome(e().blockPosition()).unwrapKey();
        return (b.isPresent()) ? lines(label("biome").append(b.get().location().toString())) : lines();
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

    private static Component gameTime() {
        var t = MCTimeUtils.formatDayTime(e().level.getDayTime());
        return label("game_time").append(format(GameTimeRC.format.get(), t[0], t[1], t[2], t[3]));
    }

}
