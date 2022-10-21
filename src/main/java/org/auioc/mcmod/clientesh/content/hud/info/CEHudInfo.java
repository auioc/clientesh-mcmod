package org.auioc.mcmod.clientesh.content.hud.info;

import org.auioc.mcmod.arnicalib.game.chat.TextUtils;
import org.auioc.mcmod.clientesh.ClientEsh;
import org.auioc.mcmod.clientesh.api.hud.HudInfo;
import org.auioc.mcmod.clientesh.api.mixin.IMixinMinecraft;
import org.auioc.mcmod.clientesh.content.adapter.SeedGetter;
import org.auioc.mcmod.clientesh.content.hud.info.CEHudConfig.BiomeRC;
import org.auioc.mcmod.clientesh.content.hud.info.CEHudConfig.DimensionRC;
import org.auioc.mcmod.clientesh.content.hud.info.CEHudConfig.LightRC;
import org.auioc.mcmod.clientesh.content.hud.info.CEHudConfig.TargetedBlockRC;
import net.minecraft.ChatFormatting;
import net.minecraft.SharedConstants;
import net.minecraft.client.ClientBrandRetriever;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Option;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CEHudInfo {

    // ============================================================================================================== //

    public static final HudInfo MINECRAFT_VERSION = HudInfo.create("MINECRAFT_VERSION", CEHudInfo::minecraftVersion);
    public static final HudInfo FPS = HudInfo.create("FPS", CEHudInfo::fps);
    public static final HudInfo SEED = HudInfo.create("SEED", CEHudInfo::seed);
    public static final HudInfo DIMENSION = HudInfo.create("DIMENSION", DimensionRC::build, CEHudInfo::dimension, true);
    public static final HudInfo BIOME = HudInfo.create("BIOME", BiomeRC::build, CEHudInfo::biome, true);
    public static final HudInfo LIGHT = HudInfo.create("LIGHT", LightRC::build, CEHudInfo::light, true);
    public static final HudInfo TARGETED_BLOCK = HudInfo.create("TARGETED_BLOCK", TargetedBlockRC::build, CEHudInfo::targetedBlock, true);

    // ============================================================================================================== //
    //#region f1

    public static void register() {
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

    private static Component[] seed() {
        return (SeedGetter.hasSeed()) ? lines(label("seed").append(format("%s", SeedGetter.get()))) : lines();
    }

    private static Component dimension() {
        var id = level().dimension().location();
        return label("dimension").append(format(DimensionRC.format.get(), i10n(String.format("dimension.%s.%s", id.getNamespace(), id.getPath())), id.toString()));
    }

    private static Component[] biome() {
        var b = level().getBiome(blockpos()).unwrapKey();
        if (b.isPresent()) {
            var id = b.get().location();
            return lines(label("biome").append(format(BiomeRC.format.get(), i10n(String.format("biome.%s.%s", id.getNamespace(), id.getPath())), id.toString())));
        }
        return lines();
    }

    private static Component light() {
        return label("light").append(format(LightRC.format.get(), level().getBrightness(LightLayer.SKY, blockpos()), level().getBrightness(LightLayer.BLOCK, blockpos())));
    }

    private static Component[] targetedBlock() {
        var hit = e().pick(TargetedBlockRC.length.get(), 0.0F, false);
        if (hit.getType() == HitResult.Type.BLOCK) {
            var pos = ((BlockHitResult) hit).getBlockPos();
            double distance = Vec3.atCenterOf(e().eyeBlockPosition()).distanceTo(Vec3.atCenterOf(pos));
            var state = level().getBlockState(pos);
            var block = state.getBlock();
            return lines(
                null,
                label("targeted_block").withStyle(ChatFormatting.BOLD),
                value("targeted_block", "xyz").append(format("%d, %d, %d", pos.getX(), pos.getY(), pos.getZ())),
                value("targeted_block", "distance").append(format("%.2f", distance)),
                value("targeted_block", "name").append(block.getName().append(format(" (%s)", state.getBlock().getRegistryName().toString())))
            );
        }
        return lines();
    }

}
