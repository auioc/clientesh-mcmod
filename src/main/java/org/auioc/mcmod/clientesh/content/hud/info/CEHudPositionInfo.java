package org.auioc.mcmod.clientesh.content.hud.info;

import org.auioc.mcmod.arnicalib.game.world.position.SpeedUnit;
import org.auioc.mcmod.clientesh.api.hud.HudInfo;
import org.auioc.mcmod.clientesh.content.adapter.SeedGetter;
import net.minecraft.core.Direction;
import net.minecraft.core.SectionPos;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeConfigSpec.Builder;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import net.minecraftforge.common.ForgeConfigSpec.EnumValue;

@OnlyIn(Dist.CLIENT)
public class CEHudPositionInfo extends CEHudInfo {

    protected static void init() {}

    // ============================================================================================================== //

    public static final HudInfo COORDINATES = HudInfo.create("COORDINATES", CoordinatesC::build, CEHudPositionInfo::coordinates);
    public static final HudInfo FACING = HudInfo.create("FACING", FacingC::build, CEHudPositionInfo::facing);
    public static final HudInfo BLOCK_POSITION = HudInfo.create("BLOCK_POSITION", BlockPositionC::build, CEHudPositionInfo::blockPostion);
    public static final HudInfo CHUNK_POSITION = HudInfo.create("CHUNK_POSITION", ChunkPositionC::build, CEHudPositionInfo::chunkPostion);
    public static final HudInfo SPEED = HudInfo.create("SPEED", SpeedC::build, CEHudPositionInfo::speed);
    public static final HudInfo VELOCITY = HudInfo.create("VELOCITY", VelocityC::build, CEHudPositionInfo::velocity);

    // ============================================================================================================== //
    //#region supplier

    private static Component coordinates() {
        return label("coordinates").append(format(CoordinatesC.format, e().getX(), e().getY(), e().getZ()));
    }

    private static Component blockPostion() {
        var pos = blockpos();
        return label("block_postion").append(format(BlockPositionC.format, pos.getX(), pos.getY(), pos.getZ(), pos.getX() & 15, pos.getY() & 15, pos.getZ() & 15));
    }

    private static Component chunkPostion() {
        var bPos = blockpos();
        var cPos = new ChunkPos(bPos);
        boolean isSlimeChunk = false;
        if (SeedGetter.hasSeed()) isSlimeChunk = WorldgenRandom.seedSlimeChunk(cPos.x, cPos.z, SeedGetter.get(), 987234911L).nextInt(10) == 0;
        return label("chunk_postion").append(
            format(
                ChunkPositionC.format,
                cPos.x, SectionPos.blockToSectionCoord(bPos.getY()), cPos.z,
                (isSlimeChunk) ? "(slimechunk)" : "",
                cPos.getRegionLocalX(), cPos.getRegionLocalZ(), String.format("r.%d.%d.mca", cPos.getRegionX(), cPos.getRegionZ())
            )
        );
    }

    private static Component facing() {
        float yaw = Mth.wrapDegrees(e().getYRot());
        int direction8 = (int) Math.floor((yaw - 180.0F) / 45.0F + 0.5F) & 7;
        var direction4 = e().getDirection();
        String axis = ((direction4.getAxisDirection() == Direction.AxisDirection.POSITIVE) ? "+" : "-") + (direction4.getAxis().getName().toUpperCase());
        return label("facing").append(format(FacingC.format, valueString("facing", String.valueOf(direction8)), axis, yaw, Mth.wrapDegrees(e().getXRot())));
    }

    // TODO
    private static Vec3 _getVelocity(SpeedUnit unit) {
        var e = e();
        double vX = unit.convertFrom(e.getX() - e.xOld);
        double vY = unit.convertFrom(e.getY() - e.yOld);
        double vZ = unit.convertFrom(e.getZ() - e.zOld);
        return new Vec3(vX, vY, vZ);
    }

    private static Component speed() {
        var unit = SpeedC.unit.get();
        return label("speed").append(format(SpeedC.format, _getVelocity(unit).length(), unit.getSymbol()));
    }

    private static Component velocity() {
        var unit = VelocityC.unit.get();
        var v = _getVelocity(unit);
        return label("velocity").append(format(VelocityC.format, v.x, v.y, v.z, unit.getSymbol()));
    }

    //#endregion supplier

    // ============================================================================================================== //
    //#region config

    private static class CoordinatesC {
        public static ConfigValue<String> format;

        public static void build(final Builder b) {
            format = b.comment("1f: x", "2f: y", "3f: z").define("format", "%1$.3f / %2$.3f / %3$.3f");
        }
    }

    private static class FacingC {
        public static ConfigValue<String> format;

        public static void build(final Builder b) {
            format = b.comment("1s: direction", "2s: axis", "3f: yaw", "4f: pitch").define("format", "%1$s (%2$s) (%3$.1f / %4$.1f)");
        }
    }

    private static class BlockPositionC {
        public static ConfigValue<String> format;

        public static void build(final Builder b) {
            format = b.comment("1d: x", "2d: y", "3d: z", "4d: in chunk x", "5d: in chunk y", "6d: in chunk z").define("format", "%1$d %2$d %3$d [%4$d %5$d %6$d]");
        }
    }

    private static class ChunkPositionC {
        public static ConfigValue<String> format;

        public static void build(final Builder b) {
            format = b.comment("1d: x", "2d: y", "3d: z", "4s: slimechunk", "5d: region local x", "6d: region local z", "7s: region file").define("format", "%1$d %2$d %3$d [%5$d %6$d in %7$s] %4$s");
        }
    }

    private static class SpeedC {
        public static EnumValue<SpeedUnit> unit;
        public static ConfigValue<String> format;

        public static void build(final Builder b) {
            unit = b.defineEnum("unit", SpeedUnit.METRES_PER_SECOND);
            format = b.comment("1f: speed", "2s: unit").define("format", "%1$.3f (%2$s)");
        }
    }

    private static class VelocityC {
        public static EnumValue<SpeedUnit> unit;
        public static ConfigValue<String> format;

        public static void build(final Builder b) {
            unit = b.defineEnum("unit", SpeedUnit.METRES_PER_SECOND);
            format = b.comment("1f: x", "2f: y", "3f: z", "4s: unit").define("format", "%1$.3f / %2$.3f / %3$.3f (%4$s)");
        }
    }

    //#endregion config

}