package org.auioc.mcmod.clientesh.api.hud.layout;

import org.auioc.mcmod.arnicalib.base.word.WordUtils;

public enum HudAlignment {

    TOP_LEFT(true, true),
    TOP_RIGHT(true, false),
    BOTTOM_LEFT(false, true),
    BOTTOM_RIGHT(false, false);

    public final boolean top;
    public final boolean left;
    private int x = 2;
    private int y = 2;

    private HudAlignment(boolean top, boolean left) {
        this.top = top;
        this.left = left;
    }

    public String camelName() {
        return WordUtils.toCamelCase(name().toLowerCase());
    }

    public void xOffset(int x) {
        this.x = x;
    }

    public void yOffset(int y) {
        this.y = y;
    }

    public int offsetX(int w) {
        return left ? this.x : w - this.x;
    }

    public int offsetY(int h) {
        return top ? this.y : h - this.y;
    }

}
