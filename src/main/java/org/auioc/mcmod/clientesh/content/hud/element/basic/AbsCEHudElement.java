package org.auioc.mcmod.clientesh.content.hud.element.basic;

import javax.annotation.Nonnull;
import org.auioc.mcmod.arnicalib.game.entity.EntityUtils;
import org.auioc.mcmod.clientesh.api.hud.element.AbsHudElement;
import org.auioc.mcmod.clientesh.api.hud.value.IOperableValue;
import com.google.gson.JsonObject;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public abstract class AbsCEHudElement extends AbsHudElement {

    protected static Level level;
    protected static LocalPlayer player;
    protected static Vec3 position;
    protected static BlockPos blockPosition;
    protected static LevelChunk chunk;
    protected static Vec3 velocity;
    protected static Entity cameraEntity;
    protected static boolean waiting;

    public static void setLevel(Level level) { AbsCEHudElement.level = level; }

    public static void setPlayer(LocalPlayer player) {
        AbsCEHudElement.player = player;
        AbsCEHudElement.position = player.position();
        AbsCEHudElement.blockPosition = player.blockPosition();
        AbsCEHudElement.velocity = EntityUtils.calcVelocity(player);
        AbsCEHudElement.chunk = level.getChunkSource().getChunk(blockPosition.getX() >> 4, blockPosition.getZ() >> 4, false);
    }

    public static void setCameraEntity(Entity cameraEntity) { AbsCEHudElement.cameraEntity = cameraEntity; }

    public static void setWaiting(boolean waiting) { AbsCEHudElement.waiting = waiting; }

    public static boolean isWaiting() { return waiting; }

    // ====================================================================== //

    protected final boolean requiresChunk;

    public AbsCEHudElement() { this.requiresChunk = false; }

    public AbsCEHudElement(JsonObject json) { super(json); this.requiresChunk = false; }

    public AbsCEHudElement(JsonObject json, boolean requiresChunk) { super(json); this.requiresChunk = requiresChunk; }

    protected boolean isAvailable() {
        return !(requiresChunk && chunk == null);
    }

    @Override
    @Nonnull
    public Component getText() {
        if (isAvailable()) return super.getText();
        AbsCEHudElement.waiting = true;
        return new TextComponent("§7?");
    }

    // ============================================================================================================== //

    @OnlyIn(Dist.CLIENT)
    public static abstract class CEBooleanElement extends AbsCEHudElement implements IOperableValue.BooleanValue {

        private final boolean defaultValue;

        public CEBooleanElement(JsonObject json) { super(json); this.defaultValue = false; }

        public CEBooleanElement(JsonObject json, boolean requiresChunk, boolean defaultValue) { super(json, requiresChunk); this.defaultValue = defaultValue; }

        protected abstract boolean booleanValue(boolean defaultValue);

        @Override
        public boolean booleanValue() { return (isAvailable()) ? booleanValue(defaultValue) : defaultValue; }

        @Override
        @Nonnull
        public MutableComponent getRawText() { return new TextComponent(String.valueOf(booleanValue())); }

    }

    @OnlyIn(Dist.CLIENT)
    public static abstract class CEIntegerElement extends AbsCEHudElement implements IOperableValue.IntegerValue {

        private final int defaultValue;

        public CEIntegerElement(JsonObject json) { super(json); this.defaultValue = 0; }

        public CEIntegerElement(JsonObject json, boolean requiresChunk, int defaultValue) { super(json, requiresChunk); this.defaultValue = defaultValue; }

        public abstract int intValue(int defaultValue);

        @Override
        public int intValue() { return (isAvailable()) ? intValue(defaultValue) : defaultValue; }

        @Override
        @Nonnull
        public MutableComponent getRawText() { return new TextComponent(String.valueOf(intValue())); }

    }

    @OnlyIn(Dist.CLIENT)
    public static abstract class CEDoubleElement extends AbsCEHudElement implements IOperableValue.DoubleValue {

        private final double defaultValue;

        public CEDoubleElement(JsonObject json) { super(json); this.defaultValue = 0.0D; }

        public CEDoubleElement(JsonObject json, boolean requiresChunk, double defaultValue) { super(json, requiresChunk); this.defaultValue = defaultValue; }

        public abstract double doubleValue(double defaultValue);

        @Override
        public double doubleValue() { return (isAvailable()) ? doubleValue(defaultValue) : defaultValue; }

        @Override
        @Nonnull
        public MutableComponent getRawText() { return new TextComponent(String.valueOf(doubleValue())); }

    }

    @OnlyIn(Dist.CLIENT)
    public static abstract class CEStringElement extends AbsCEHudElement implements IOperableValue.StringValue {

        private final String defaultValue;

        public CEStringElement(JsonObject json) { super(json); this.defaultValue = ""; }

        public CEStringElement(JsonObject json, String defaultValue) { super(json); this.defaultValue = defaultValue; }

        public abstract String stringValue(String defaultValue);

        @Override
        public String stringValue() { return (isAvailable()) ? stringValue(defaultValue) : defaultValue; }

        @Override
        @Nonnull
        public MutableComponent getRawText() { return new TextComponent(stringValue()); }

    }

}
