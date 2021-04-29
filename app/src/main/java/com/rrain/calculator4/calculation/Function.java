package com.rrain.calculator4.calculation;

import androidx.annotation.NonNull;

import java.util.HashMap;
import java.util.Objects;

public class Function {
    public static final HashMap<String, Function> funcs;
    static {
        funcs = new HashMap<>();
        funcs.put("+", new Function("+", 1, 1, 1, d->d[0]+d[1]));
        funcs.put("-", new Function("-", 1, 1, 1, d->d[0]-d[1]));
        funcs.put("*", new Function("*", 0, 1, 1, d->d[0]*d[1]));
        funcs.put("/", new Function("/", 0, 1, 1, d->d[0]/d[1]));
    }

    public final String name;
    public final int priority;
    public final int preArgs;
    public final int postArgs;
    public final F func;

    public Function(@NonNull String name, int priority, int preArgs, int postArgs, F func) {
        this.name = name;
        this.priority = priority;
        this.preArgs = preArgs;
        this.postArgs = postArgs;
        this.func = func;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Function function = (Function) o;
        return name.equals(function.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
