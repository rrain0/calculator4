package com.rrain.calculator4;

import android.app.Activity;
import android.util.DisplayMetrics;

public class DisplayManager {

    public static int getWidth(Activity activity){
        return getSize(activity).getW();
    }

    public static int getHeight(Activity activity){
        return getSize(activity).getH();
    }

    //получить ширину и высоту дисплея в пикселях
    public static Size getSize(Activity activity){
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return new Size(metrics.widthPixels, metrics.heightPixels);
    }

}
