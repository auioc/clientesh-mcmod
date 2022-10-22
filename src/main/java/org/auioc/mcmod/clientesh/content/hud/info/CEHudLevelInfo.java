package org.auioc.mcmod.clientesh.content.hud.info;

import org.auioc.mcmod.clientesh.api.hud.HudInfo;
import org.auioc.mcmod.clientesh.content.adapter.SeedGetter;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeConfigSpec.Builder;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import net.minecraftforge.common.ForgeConfigSpec.DoubleValue;

@OnlyIn(Dist.CLIENT)
public class CEHudLevelInfo extends CEHudInfo {

    protected static void init() {}

    // ============================================================================================================== //

    public static final HudInfo SEED = HudInfo.create("SEED", CEHudLevelInfo::seed);
    public static final HudInfo DIMENSION = HudInfo.create("DIMENSION", DimensionRC::build, CEHudLevelInfo::dimension, true);
    public static final HudInfo BIOME = HudInfo.create("BIOME", BiomeRC::build, CEHudLevelInfo::biome, true);
    public static final HudInfo LIGHT = HudInfo.create("LIGHT", LightRC::build, CEHudLevelInfo::light, true);
    public static final HudInfo TARGETED_BLOCK = HudInfo.create("TARGETED_BLOCK", TargetedBlockRC::build, CEHudLevelInfo::targetedBlock, true);

    // ============================================================================================================== //
    //#region supplier

    private static Component[] seed() {
        return (SeedGetter.hasSeed()) ? lines(label("seed").append(format("%s", SeedGetter.get()))) : lines();
    }

    private static Component dimension() {
        var id = level().dimension().location();
        return label("dimension").append(format(DimensionRC.format, i10n(String.format("dimension.%s.%s", id.getNamespace(), id.getPath())), id.toString()));
    }

    private static Component[] biome() {
        var b = level().getBiome(blockpos()).unwrapKey();
        if (b.isPresent()) {
            var id = b.get().location();
            return lines(label("biome").append(format(BiomeRC.format, i10n(String.format("biome.%s.%s", id.getNamespace(), id.getPath())), id.toString())));
        }
        return lines();
    }

    private static Component light() {
        return label("light").append(format(LightRC.format, level().getBrightness(LightLayer.SKY, blockpos()), level().getBrightness(LightLayer.BLOCK, blockpos())));
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

    //#endregion supplier

    // ============================================================================================================== //
    //#region config

    private static class DimensionRC {
        public static ConfigValue<String> format;

        public static void build(final Builder b) {
            format = b.comment("1: name", "2: id").define("format", "%1$s (%2$s)");
        }
    }

    private static class BiomeRC {
        public static ConfigValue<String> format;

        public static void build(final Builder b) {
            format = b.comment("1: name", "2: id").define("format", "%1$s (%2$s)");
        }
    }

    private static class LightRC {
        public static ConfigValue<String> format;

        public static void build(final Builder b) {
            format = b.comment("1: sky", "2: block").define("format", "sky %1$d, block %2$d");
        }
    }

    private static class TargetedBlockRC {
        public static DoubleValue length;

        public static void build(final Builder b) {
            length = b.defineInRange("ray_length", 20.0D, 0.0D, Double.MAX_VALUE);
        }
    }

    //#endregion config

}
