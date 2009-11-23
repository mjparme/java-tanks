package com.mike.tanks;

import java.util.Map;
import java.util.HashMap;

/**
 * User: mjparme
 * Date: Nov 23, 2009
 * Time: 9:08:52 AM
 */
public enum Images {
    TANK("tank.png");

    private String imageName;
    private static Map<String, Images> imageNames;

    static {
        imageNames = new HashMap<String, Images>();
        storeValuesInMap();
    }

    Images(String imageName) {
        this.imageName = imageName;
    }

    public String getImageName() {
        return imageName;
    }

    public static Images getEnumForImageName(String displayName) {
        return imageNames.get(displayName);
    }

    private static void storeValuesInMap() {
        Images[] values = Images.values();
        for (Images value : values) {
            imageNames.put(value.imageName, value);
        }
    }
}