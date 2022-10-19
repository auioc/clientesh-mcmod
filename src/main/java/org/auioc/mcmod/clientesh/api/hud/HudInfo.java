package org.auioc.mcmod.clientesh.api.hud;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import org.auioc.mcmod.arnicalib.game.chat.TextUtils;
import net.minecraft.network.chat.Component;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.IExtensibleEnum;

public enum HudInfo implements IExtensibleEnum {

    _EMPTY_(() -> TextUtils.empty());

    @Nullable
    private final Consumer<ForgeConfigSpec.Builder> configBuilder;
    private final Supplier<Component> textSupplier;

    private HudInfo(Consumer<ForgeConfigSpec.Builder> configBuilder, Supplier<Component> textSupplier) {
        this.configBuilder = configBuilder;
        this.textSupplier = textSupplier;
    }

    private HudInfo(Supplier<Component> textSupplier) {
        this(null, textSupplier);
    }

    public Component getText() {
        return textSupplier.get();
    }

    public boolean hasConfig() {
        return configBuilder != null;
    }

    public void buildConfig(ForgeConfigSpec.Builder builder) {
        if (hasConfig()) configBuilder.accept(builder);
    }

    public static List<HudInfo> valueOf(List<String> l) {
        return l.stream().map(HudInfo::valueOf).toList();
    }

    public static HudInfo create(String name, Supplier<Component> textSupplier) {
        throw new IllegalStateException("Enum not extended");
    }

    public static HudInfo create(String name, Consumer<ForgeConfigSpec.Builder> configBuilder, Supplier<Component> textSupplier) {
        throw new IllegalStateException("Enum not extended");
    }

}
