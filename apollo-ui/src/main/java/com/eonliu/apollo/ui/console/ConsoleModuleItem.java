package com.eonliu.apollo.ui.console;

import java.util.List;

/**
 * @author Eon Liu
 */
public class ConsoleModuleItem {

    private int itemType;
    private int moduleName;
    private List<ConsoleItem> consoleItems;

    public ConsoleModuleItem(int moduleName, List<ConsoleItem> consoleItems) {
        this(moduleName, consoleItems, ConsoleAdapter.ITEM_TYPE_GRID);
    }

    public ConsoleModuleItem(int moduleName, List<ConsoleItem> consoleItems, int itemType) {
        this.moduleName = moduleName;
        this.consoleItems = consoleItems;
        this.itemType = itemType;
    }

    public int getItemType() {
        return itemType;
    }

    public int getModuleName() {
        return moduleName;
    }

    public List<ConsoleItem> getConsoleItems() {
        return consoleItems;
    }
}
