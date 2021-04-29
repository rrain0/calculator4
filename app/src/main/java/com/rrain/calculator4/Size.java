package com.rrain.calculator4;

import lombok.Getter;
import lombok.Setter;

public class Size {
    @Getter @Setter private int w, h;

    public Size() { }

    public Size(int w, int h) {
        this.w = w;
        this.h = h;
    }
}
