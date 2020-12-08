package com.gerardoleonel.projectuts_eventorganizer.fragment.home.content;

public class Location {
    int image;
    String name, desc;

    public Location (int image, String name, String desc) {
        this.image = image;
        this.name = name;
        this.desc = desc;

    }

    public int getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

}
