package org.auioc.mcmod.clientesh.content.hud.element.simple;

import org.auioc.mcmod.arnicalib.game.entity.EntityUtils;
import org.auioc.mcmod.arnicalib.game.world.LevelUtils;
import org.auioc.mcmod.clientesh.api.hud.element.AbsHudElement.AbsBooleanElement;
import org.auioc.mcmod.clientesh.api.hud.element.AbsHudElement.AbsDoubleElement;
import org.auioc.mcmod.clientesh.api.hud.element.AbsHudElement.AbsIntegerElement;
import org.auioc.mcmod.clientesh.api.hud.element.AbsHudElement.AbsStringElement;
import org.auioc.mcmod.clientesh.api.hud.element.IHudElement;
import org.auioc.mcmod.clientesh.content.adapter.SeedGetter;
import com.google.gson.JsonObject;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
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
                return (SeedGetter.hasSeed())
                    ? LevelUtils.isSlimeChunk(chunkPosition, SeedGetter.get())
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

    public static IHudElement yaw(JsonObject json) {
        return new AbsDoubleElement(json) {
            @Override
            public double value() { return Mth.wrapDegrees(player.getYRot()); }
        };
    }

    public static IHudElement pitch(JsonObject json) {
        return new AbsDoubleElement(json) {
            @Override
            public double value() { return Mth.wrapDegrees(player.getXRot()); }
        };
    }

    public static IHudElement facingAxis(JsonObject json) {
        return new AbsStringElement(json) {
            @Override
            public String value() {
                var d4 = player.getDirection();
                return ((d4.getAxisDirection() == Direction.AxisDirection.POSITIVE) ? "+" : "-") + (d4.getAxis().getName().toUpperCase());
            }
        };
    }

    public static IHudElement facingDirection(JsonObject json) {
        return new AbsStringElement(json) {
            @Override
            public String value() { return EntityUtils.getFacing8WindDirection(player).getString(); }
        };
    }

}
