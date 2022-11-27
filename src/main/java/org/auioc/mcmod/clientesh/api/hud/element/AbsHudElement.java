package org.auioc.mcmod.clientesh.api.hud.element;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.auioc.mcmod.arnicalib.game.chat.TextUtils;
import org.auioc.mcmod.arnicalib.game.entity.EntityUtils;
import org.auioc.mcmod.clientesh.api.hud.value.IOperableValue.IBooleanValue;
import org.auioc.mcmod.clientesh.api.hud.value.IOperableValue.IDoubleValue;
import org.auioc.mcmod.clientesh.api.hud.value.IOperableValue.IIntegerValue;
import org.auioc.mcmod.clientesh.api.hud.value.IOperableValue.IStringValue;
import com.google.gson.JsonObject;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class AbsHudElement implements IHudElement {

    protected static final Minecraft MC = Minecraft.getInstance();

    protected static Level level;
    protected static LocalPlayer player;
    protected static Vec3 position;
    protected static BlockPos blockPosition;
    protected static ChunkPos chunkPosition;
    @Nullable
    protected static LevelChunk chunk;
    protected static Vec3 velocity;
    protected static Entity cameraEntity;
    protected static boolean waiting;

    public static void setLevel(Level level) { AbsHudElement.level = level; }

    public static void setPlayer(LocalPlayer player) {
        AbsHudElement.player = player;
        AbsHudElement.position = player.position();
        AbsHudElement.blockPosition = player.blockPosition();
        AbsHudElement.velocity = EntityUtils.calcVelocity(player);
        int chunkX = blockPosition.getX() >> 4;
        int chunkZ = blockPosition.getZ() >> 4;
        AbsHudElement.chunkPosition = new ChunkPos(chunkX, chunkZ);
        AbsHudElement.chunk = level.getChunkSource().getChunk(chunkX, chunkZ, false);
    }

    public static void setCameraEntity(Entity cameraEntity) { AbsHudElement.cameraEntity = cameraEntity; }

    public static void setWaiting(boolean waiting) { AbsHudElement.waiting = waiting; }

    public static boolean isWaiting() { return waiting; }

    protected static MutableComponent format(String format, Object... args) {
        return TextUtils.literal(String.format(format, args));
    }

    protected static String translatable(String key, Object... args) {
        return TextUtils.translatable(key, args).getString();
    }

    protected static String translatable(String key) {
        return TextUtils.translatable(key).getString();
    }

    // ====================================================================== //

    protected final boolean requiresChunk;

    @Nullable
    private Style style = null;


    public AbsHudElement(Style style, boolean requiresChunk) { this.style = style; this.requiresChunk = requiresChunk; }

    public AbsHudElement(Style style) { this(style, false); }

    public AbsHudElement() { this.requiresChunk = false; }

    public AbsHudElement(JsonObject json) { this(json, false); }

    public AbsHudElement(JsonObject json, boolean requiresChunk) {
        if (json != null && json.has("style")) {
            this.style = TextUtils.deserializeStyle(json.get("style"));
        }
        this.requiresChunk = requiresChunk;
    }

    protected boolean isAvailable() {
        return !(requiresChunk && chunk == null);
    }

    @Nonnull
    protected abstract MutableComponent getRawText();

    @Override
    @Nonnull
    public Component getText() {
        if (isAvailable()) {
            if (this.style != null) {
                return getRawText().withStyle(style);
            } else {
                return getRawText();
            }
        }
        AbsHudElement.waiting = true;
        return new TextComponent("§7?");
    }

    public void setStyle(Style style) { this.style = style; }

    @Nullable
    public Style getStyle() { return this.style; }

    // ============================================================================================================== //

    @OnlyIn(Dist.CLIENT)
    public static abstract class AbsBooleanElement extends AbsHudElement implements IBooleanValue {

        protected final boolean defaultValue;

        public AbsBooleanElement() { this(null); }

        public AbsBooleanElement(JsonObject json) { super(json); this.defaultValue = false; }

        public AbsBooleanElement(JsonObject json, boolean requiresChunk, boolean defaultValue) { super(json, requiresChunk); this.defaultValue = defaultValue; }

        protected abstract boolean value();

        @Override
        public boolean booleanValue() { return (isAvailable()) ? value() : defaultValue; }

        @Override
        @Nonnull
        public MutableComponent getRawText() { return new TextComponent(String.valueOf(booleanValue())); }

    }

    @OnlyIn(Dist.CLIENT)
    public static abstract class AbsIntegerElement extends AbsHudElement implements IIntegerValue {

        protected final int defaultValue;

        public AbsIntegerElement() { this(null); }

        public AbsIntegerElement(JsonObject json) { super(json); this.defaultValue = 0; }

        public AbsIntegerElement(JsonObject json, boolean requiresChunk, int defaultValue) { super(json, requiresChunk); this.defaultValue = defaultValue; }

        protected abstract int value();

        @Override
        public int intValue() { return (isAvailable()) ? value() : defaultValue; }

        @Override
        @Nonnull
        public MutableComponent getRawText() { return new TextComponent(String.valueOf(intValue())); }

    }

    @OnlyIn(Dist.CLIENT)
    public static abstract class AbsDoubleElement extends AbsHudElement implements IDoubleValue {

        protected final double defaultValue;

        public AbsDoubleElement() { this(null); }

        public AbsDoubleElement(JsonObject json) { super(json); this.defaultValue = 0.0D; }

        public AbsDoubleElement(JsonObject json, boolean requiresChunk, double defaultValue) { super(json, requiresChunk); this.defaultValue = defaultValue; }

        protected abstract double value();

        @Override
        public double doubleValue() { return (isAvailable()) ? value() : defaultValue; }

        @Override
        @Nonnull
        public MutableComponent getRawText() { return new TextComponent(String.valueOf(doubleValue())); }

    }

    @OnlyIn(Dist.CLIENT)
    public static abstract class AbsStringElement extends AbsHudElement implements IStringValue {

        protected final String defaultValue;

        public AbsStringElement() { this(null); }

        public AbsStringElement(JsonObject json) { super(json); this.defaultValue = ""; }

        public AbsStringElement(JsonObject json, boolean requiresChunk, String defaultValue) { super(json, requiresChunk); this.defaultValue = defaultValue; }

        protected abstract String value();

        @Override
        public String stringValue() { return (isAvailable()) ? value() : defaultValue; }

        @Override
        @Nonnull
        public MutableComponent getRawText() { return new TextComponent(stringValue()); }

    }

}
