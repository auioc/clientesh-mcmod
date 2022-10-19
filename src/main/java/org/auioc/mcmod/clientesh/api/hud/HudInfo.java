package org.auioc.mcmod.clientesh.api.hud;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.IExtensibleEnum;

@OnlyIn(Dist.CLIENT)
public enum HudInfo implements IExtensibleEnum {

    _EMPTY_(() -> (List<Component>) null);

    @Nullable
    private final ConfigBuilder configBuilder;
    private final ComponentsSupplier textSupplier;

    private HudInfo(@Nullable ConfigBuilder configBuilder, ComponentsSupplier textSupplier) {
        this.configBuilder = configBuilder;
        this.textSupplier = textSupplier;
    }

    private HudInfo(@Nullable ConfigBuilder configBuilder, ComponentSupplier textSupplier) {
        this(configBuilder, () -> List.of(textSupplier.get()));
    }

    private HudInfo(ComponentsSupplier textSupplier) {
        this(null, textSupplier);
    }

    private HudInfo(ComponentSupplier textSupplier) {
        this(null, textSupplier);
    }

    @Nullable
    public List<Component> getText() {
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


    public static HudInfo create(String name, ConfigBuilder configBuilder, ComponentsSupplier textSupplier) {
        throw new IllegalStateException("Enum not extended");
    }

    public static HudInfo create(String name, ConfigBuilder configBuilder, ComponentSupplier textSupplier) {
        throw new IllegalStateException("Enum not extended");
    }

    public static HudInfo create(String name, ComponentsSupplier textSupplier) {
        throw new IllegalStateException("Enum not extended");
    }

    public static HudInfo create(String name, ComponentSupplier textSupplier) {
        throw new IllegalStateException("Enum not extended");
    }


    @FunctionalInterface
    public static interface ConfigBuilder {

        void accept(ForgeConfigSpec.Builder b);

    }

    @FunctionalInterface
    public static interface ComponentSupplier {

        Component get();

    }

    @FunctionalInterface
    public static interface ComponentsSupplier {

        @Nullable
        List<Component> get();

    }

}
