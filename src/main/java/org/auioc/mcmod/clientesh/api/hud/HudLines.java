package org.auioc.mcmod.clientesh.api.hud;

import java.util.ArrayList;
import java.util.Collection;

public class HudLines {

    protected static final ArrayList<HudInfo> L = new ArrayList<>();
    protected static final ArrayList<HudInfo> R = new ArrayList<>();

    public static ArrayList<HudInfo> getLeft() {
        return L;
    }

    public static ArrayList<HudInfo> getRight() {
        return R;
    }

    public static void loadLeft(Collection<HudInfo> l) {
        L.clear();
        L.addAll(l);
    }

    public static void loadRight(Collection<HudInfo> r) {
        R.clear();
        R.addAll(r);
    }

    public static void load(Collection<HudInfo> l, Collection<HudInfo> r) {
        loadLeft(l);
        loadRight(r);
    }

}
