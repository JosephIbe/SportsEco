package com.jjoey.sportseco.models;

public class ItemsDrawer {

    public int icon;
    public String name;

    public ItemsDrawer() { }

    public ItemsDrawer(int icon, String name) {
        this.icon = icon;
        this.name = name;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
