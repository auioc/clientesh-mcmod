package org.auioc.mcmod.clientesh.api.hud.layout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import javax.annotation.Nullable;
import org.apache.commons.lang3.tuple.Pair;
import org.auioc.mcmod.clientesh.api.hud.element.IHudElement;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class HudLayout {

    private static final List<List<IHudElement>> L = new ArrayList<>(new ArrayList<>());
    private static final List<List<IHudElement>> R = new ArrayList<>(new ArrayList<>());

    public static List<List<IHudElement>> getLeft() {
        return Collections.unmodifiableList(L);
    }

    public static List<List<IHudElement>> getRight() {
        return Collections.unmodifiableList(R);
    }

    public synchronized static void clear() {
        L.clear();
        R.clear();
    }

    public synchronized static void load(Optional<List<List<IHudElement>>> left, Optional<List<List<IHudElement>>> right) {
        clear();
        left.ifPresent(L::addAll);
        right.ifPresent(R::addAll);
    }

    public static void load(Pair<List<List<IHudElement>>, List<List<IHudElement>>> pair) {
        load(Optional.ofNullable(pair.getLeft()), Optional.ofNullable(pair.getRight()));
    }

    public static void load(@Nullable List<List<IHudElement>> left, @Nullable List<List<IHudElement>> right) {
        load(Optional.ofNullable(left), Optional.ofNullable(right));
    }

}
