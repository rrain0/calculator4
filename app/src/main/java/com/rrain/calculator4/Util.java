package com.rrain.calculator4;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class Util {
    public static int[] ints(int... ints){ return ints; }

    public static String round3String(double d){
        return roundString(d, 3);
    }
    public static String roundString(double d, int precision){
        return new BigDecimal(d).setScale(precision, RoundingMode.HALF_UP).stripTrailingZeros().toPlainString();
    }


    public static float round3(float d){
        return round(d, 3);
    }
    public static float round(float d, int precision){
        return new BigDecimal(d).setScale(precision, RoundingMode.HALF_UP).floatValue();
    }


    public static boolean equal3(float f1, float f2){
        return equal(f1, f2, 0.001f);
    }
    public static boolean equal(float f1, float f2, float eps){
        return Math.abs(Float.compare(f1, f2)) <= eps;
    }
}
