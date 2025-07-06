package com.rrain.calculator4;

import lombok.Getter;

public class Theme {
    @Getter private final int themeStyleId;
    @Getter private final String name;
    @Getter private final String displayedName;

    public Theme(String name, int themeStyleId, String displayedName) {
        this.themeStyleId = themeStyleId;
        this.name = name;
        this.displayedName = displayedName;
    }
}
