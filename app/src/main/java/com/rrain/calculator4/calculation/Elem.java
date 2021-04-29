package com.rrain.calculator4.calculation;

public class Elem {
    public int s; //start index inclusive
    public int e; //end index exclusive

    public Function f;
    public Num n;

    public Elem(int s, int e) {
        this.s = s;
        this.e = e;
    }

    public Elem(int s, int e, Function f) {
        this.s = s;
        this.e = e;
        this.f = f;
    }

    public Elem(int s, int e, Num n) {
        this.s = s;
        this.e = e;
        this.n = n;
    }

    public boolean isFunction(){ return f!=null; }
    public boolean isNumber(){ return n!=null; }
    public boolean isUndefined(){ return !isNullElem() && f==null && n==null; }
    public boolean isNullElem(){ return s==e; }
}
