package com.eonliu.apollo.ui.console;

/**
 * @author Eon Liu
 */
public class ConsoleItem {

    private int name;
    private int drawable;
    private int type;

    public ConsoleItem(int name, int drawable, int type) {
        this.name = name;
        this.drawable = drawable;
        this.type = type;
    }

    public int getName() {
        return name;
    }

    public int getDrawable() {
        return drawable;
    }

    public int getType() {
        return type;
    }
}
