package org.auioc.mcmod.clientesh.api.hud.element;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.auioc.mcmod.arnicalib.game.entity.EntityUtils;
import org.auioc.mcmod.clientesh.api.hud.value.IOperableValue;
import com.google.gson.JsonObject;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.Entity;
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
        AbsHudElement.chunk = level.getChunkSource().getChunk(blockPosition.getX() >> 4, blockPosition.getZ() >> 4, false);
    }

    public static void setCameraEntity(Entity cameraEntity) { AbsHudElement.cameraEntity = cameraEntity; }

    public static void setWaiting(boolean waiting) { AbsHudElement.waiting = waiting; }

    public static boolean isWaiting() { return waiting; }

    protected static MutableComponent format(String format, Object... args) {
        return new TextComponent(String.format(format, args));
    }

    // ====================================================================== //

    private static final Style.Serializer STYLE = new Style.Serializer();

    protected final boolean requiresChunk;

    @Nullable
    private Style style = null;

    public AbsHudElement() { this.requiresChunk = false; }

    public AbsHudElement(JsonObject json) { this(json, false); }

    public AbsHudElement(JsonObject json, boolean requiresChunk) {
        if (json != null && json.has("style")) {
            this.style = STYLE.deserialize(json.get("style"), null, null);
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
    public static abstract class BooleanElement extends AbsHudElement implements IOperableValue.BooleanValue {

        private final boolean defaultValue;

        public BooleanElement() { this(null); }

        public BooleanElement(JsonObject json) { super(json); this.defaultValue = false; }

        public BooleanElement(JsonObject json, boolean requiresChunk, boolean defaultValue) { super(json, requiresChunk); this.defaultValue = defaultValue; }

        protected abstract boolean booleanValue(boolean defaultValue);

        @Override
        public boolean booleanValue() { return (isAvailable()) ? booleanValue(defaultValue) : defaultValue; }

        @Override
        @Nonnull
        public MutableComponent getRawText() { return new TextComponent(String.valueOf(booleanValue())); }

    }

    @OnlyIn(Dist.CLIENT)
    public static abstract class IntegerElement extends AbsHudElement implements IOperableValue.IntegerValue {

        private final int defaultValue;

        public IntegerElement() { this(null); }

        public IntegerElement(JsonObject json) { super(json); this.defaultValue = 0; }

        public IntegerElement(JsonObject json, boolean requiresChunk, int defaultValue) { super(json, requiresChunk); this.defaultValue = defaultValue; }

        public abstract int intValue(int defaultValue);

        @Override
        public int intValue() { return (isAvailable()) ? intValue(defaultValue) : defaultValue; }

        @Override
        @Nonnull
        public MutableComponent getRawText() { return new TextComponent(String.valueOf(intValue())); }

    }

    @OnlyIn(Dist.CLIENT)
    public static abstract class DoubleElement extends AbsHudElement implements IOperableValue.DoubleValue {

        private final double defaultValue;

        public DoubleElement() { this(null); }

        public DoubleElement(JsonObject json) { super(json); this.defaultValue = 0.0D; }

        public DoubleElement(JsonObject json, boolean requiresChunk, double defaultValue) { super(json, requiresChunk); this.defaultValue = defaultValue; }

        public abstract double doubleValue(double defaultValue);

        @Override
        public double doubleValue() { return (isAvailable()) ? doubleValue(defaultValue) : defaultValue; }

        @Override
        @Nonnull
        public MutableComponent getRawText() { return new TextComponent(String.valueOf(doubleValue())); }

    }

    @OnlyIn(Dist.CLIENT)
    public static abstract class StringElement extends AbsHudElement implements IOperableValue.StringValue {

        private final String defaultValue;

        public StringElement() { this(null); }

        public StringElement(JsonObject json) { super(json); this.defaultValue = ""; }

        public StringElement(JsonObject json, boolean requiresChunk, String defaultValue) { super(json, requiresChunk); this.defaultValue = defaultValue; }

        public abstract String stringValue(String defaultValue);

        @Override
        public String stringValue() { return (isAvailable()) ? stringValue(defaultValue) : defaultValue; }

        @Override
        @Nonnull
        public MutableComponent getRawText() { return new TextComponent(stringValue()); }

    }

}
