package org.auioc.mcmod.clientesh.content.hud.element.simple;

import org.auioc.mcmod.clientesh.api.hud.element.AbsHudElement.AbsBooleanElement;
import org.auioc.mcmod.clientesh.api.hud.element.AbsHudElement.AbsDoubleElement;
import org.auioc.mcmod.clientesh.api.hud.element.AbsHudElement.AbsIntegerElement;
import org.auioc.mcmod.clientesh.api.hud.element.IHudElement;
import org.auioc.mcmod.clientesh.content.adapter.SeedGetter;
import com.google.gson.JsonObject;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class PositionElement {

    public static IHudElement x(JsonObject json) {
        return new AbsDoubleElement(json) {
            @Override
            public double value() { return position.x; }
        };
    }

    public static IHudElement y(JsonObject json) {
        return new AbsDoubleElement(json) {
            @Override
            public double value() { return position.y; }
        };
    }

    public static IHudElement z(JsonObject json) {
        return new AbsDoubleElement(json) {
            @Override
            public double value() { return position.z; }
        };
    }

    public static IHudElement blockX(JsonObject json) {
        return new AbsIntegerElement(json) {
            @Override
            public int value() { return blockPosition.getX(); }
        };
    }

    public static IHudElement blockY(JsonObject json) {
        return new AbsIntegerElement(json) {
            @Override
            public int value() { return blockPosition.getY(); }
        };
    }

    public static IHudElement blockZ(JsonObject json) {
        return new AbsIntegerElement(json) {
            @Override
            public int value() { return blockPosition.getZ(); }
        };
    }

    public static IHudElement isSlimeChunk(JsonObject json) {
        return new AbsBooleanElement(json) {
            @Override
            public boolean value() {
                // TODO arnicalib
                return (SeedGetter.hasSeed())
                    ? WorldgenRandom.seedSlimeChunk(chunkPosition.x, chunkPosition.z, SeedGetter.get(), 987234911L).nextInt(10) == 0
                    : false;
            }
        };
    }

    public static IHudElement chunkX(JsonObject json) {
        return new AbsIntegerElement(json) {
            @Override
            public int value() { return chunkPosition.x; }
        };
    }

    public static IHudElement chunkY(JsonObject json) {
        return new AbsIntegerElement(json) {
            @Override
            public int value() { return blockPosition.getY() >> 4; }
        };
    }

    public static IHudElement chunkZ(JsonObject json) {
        return new AbsIntegerElement(json) {
            @Override
            public int value() { return chunkPosition.z; }
        };
    }

    public static IHudElement regionLocalX(JsonObject json) {
        return new AbsIntegerElement(json) {
            @Override
            public int value() { return chunkPosition.getRegionLocalX(); }
        };
    }

    public static IHudElement regionLocalZ(JsonObject json) {
        return new AbsIntegerElement(json) {
            @Override
            public int value() { return chunkPosition.getRegionLocalZ(); }
        };
    }

    public static IHudElement regionX(JsonObject json) {
        return new AbsIntegerElement(json) {
            @Override
            public int value() { return chunkPosition.getRegionX(); }
        };
    }

    public static IHudElement regionZ(JsonObject json) {
        return new AbsIntegerElement(json) {
            @Override
            public int value() { return chunkPosition.getRegionZ(); }
        };
    }

}
