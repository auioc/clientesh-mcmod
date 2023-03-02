package org.auioc.mcmod.clientesh.config;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.function.FailableConsumer;
import org.apache.logging.log4j.Marker;
import org.auioc.mcmod.arnicalib.base.log.LogUtil;
import org.auioc.mcmod.arnicalib.game.config.ConfigUtils;
import org.auioc.mcmod.clientesh.ClientEsh;
import org.auioc.mcmod.clientesh.api.config.CEConfigAt;
import org.objectweb.asm.Type;
import com.electronwill.nightconfig.core.CommentedConfig;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.loading.moddiscovery.ModAnnotation;
import net.minecraftforge.forgespi.language.ModFileScanData.AnnotationData;

@OnlyIn(Dist.CLIENT)
public class CEConfig {

    private static final Marker MARKER = LogUtil.getMarker(CEConfig.class);

    private static final String BUILDER_METHOD_NAME = "build";
    private static final String LOADING_LISTENER_METHOD_NAME = "onLoad";

    public static final ForgeConfigSpec SPEC;

    private static final List<SimpleEntry<Class<?>, FailableConsumer<CommentedConfig, Exception>>> LOADING_LISTENERS = new ArrayList<>();

    static {
        final var builder = new ForgeConfigSpec.Builder();
        build(builder);
        SPEC = builder.build();
    }

    public static void onLoad(CommentedConfig config) {
        for (var listener : LOADING_LISTENERS) {
            try {
                listener.getValue().accept(config);
            } catch (Exception e) {
                ClientEsh.LOGGER.error(MARKER, "Failed while applying loading listener in {}", listener.getKey(), e);
            }
        }
    }

    private static void build(ForgeConfigSpec.Builder builder) {
        final var atType = Type.getType(CEConfigAt.class);
        for (var data : ClientEsh.getScanData().getAnnotations()) {
            if (atType.equals(data.annotationType())) {
                Class<?> clazz;
                try {
                    clazz = Class.forName(data.memberName());
                } catch (ClassNotFoundException e) {
                    ClientEsh.LOGGER.error(MARKER, "Failed to load class {}", data.memberName(), e);
                    continue;
                }
                buildConfigFromClass(builder, getPathFromAnnotationData(data), clazz);
                addLoadingListenerFromClass(clazz);
            }
        }
    }

    private static List<String> getPathFromAnnotationData(AnnotationData data) {
        return new ArrayList<>() {
            {
                add(
                    CEConfigAt.Type
                        .valueOf(((ModAnnotation.EnumHolder) data.annotationData().get("type")).getValue())
                        .getPath()
                );

                var subPath = (String) data.annotationData().get("path");
                if (subPath != null && !subPath.isEmpty()) {
                    addAll(ConfigUtils.split(subPath));
                }
            }
        };
    }

    private static void buildConfigFromClass(ForgeConfigSpec.Builder builder, List<String> path, Class<?> clazz) {
        Method method;
        try {
            method = clazz.getMethod(BUILDER_METHOD_NAME, ForgeConfigSpec.Builder.class);
        } catch (NoSuchMethodException | SecurityException e) {
            ClientEsh.LOGGER.error(MARKER, "Failed to find 'build' method in {}", clazz, e);
            return;
        }
        if (Modifier.isStatic(method.getModifiers())) {
            builder.push(path);
            try {
                method.invoke(null, builder);
                ClientEsh.LOGGER.info(MARKER, "Building config from {}", clazz);
            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                ClientEsh.LOGGER.error(MARKER, "Failed to invoke 'build' method in {}", clazz, e);
            } finally {
                builder.pop(path.size());
            }
        }
    }

    private static void addLoadingListenerFromClass(Class<?> clazz) {
        Method method;
        try {
            method = clazz.getMethod(LOADING_LISTENER_METHOD_NAME, CommentedConfig.class);
        } catch (NoSuchMethodException e) {
            return;
        }
        if (Modifier.isStatic(method.getModifiers())) {
            ClientEsh.LOGGER.info(MARKER, "Found loading listener in {}", clazz);
            LOADING_LISTENERS.add(new SimpleEntry<>(clazz, (config) -> method.invoke(null, config)));
        }
    }

}
