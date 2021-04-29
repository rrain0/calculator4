package com.rrain.calculator4.calculator;

import android.content.SharedPreferences;

import com.rrain.calculator4.DisplayManager;
import com.rrain.calculator4.Util;

public class BtnSizeLand {
    public final float lw, lrw, lrh; //land width, land ratio, land ratio height
    public final float lh; //land height

    public float textTop1, textTop2, textTop3, textTop4, text1, text2, text3; //text sizes

    //Default values
    public BtnSizeLand(Calculator calc) {
        lrw = 16; lrh = 12;
        float k = 1f/2f;
        lw = (DisplayManager.getHeight(calc) * k) / (4*(lrh/lrw));
        lh = lw * (lrh/lrw);
        calcTextSize();
    }
    public BtnSizeLand(float width, float widthRatio, float heightRatio) {
        lrw = widthRatio; lrh = heightRatio;
        lw = width;
        lh = lw * (lrh/lrw);
        calcTextSize();
    }
    public BtnSizeLand(float width, float height){
        lw = width;
        lh = height;
        lrw = 16f;
        lrh = Util.round3(height*lrw / width);
        calcTextSize();
    }

    private void calcTextSize(){
        textTop1 = lh * 0.28f;
        textTop2 = lh * 0.45f;
        textTop3 = lh * 0.2f;
        textTop4 = lh * 0.7f;
        text1 = lh * 0.45f;
        text2 = lh * 0.4f;
        text3 = lh * 0.25f;
    }


    public BtnSizeLand buildNew(float width){
        return new BtnSizeLand(width, lrw, lrh);
    }
    public BtnSizeLand buildNew(float ratioW, float ratioH){
        return new BtnSizeLand(lw, ratioW, ratioH);
    }

    public static BtnSizeLand load(Calculator calc){
        BtnSizeLand b = new BtnSizeLand(calc);//default
        return new BtnSizeLand(
                calc.settings.getRowWidthLandscape(b.lw),
                calc.settings.getRowRatioWidthLandscape(b.lrw),
                calc.settings.getRowRatioHeightLandscape(b.lrh)
        );
    }
    public void save(Calculator calc){
        SharedPreferences.Editor editor = calc.settings.getEditor();
        calc.settings.setRowWidthLandscape(editor, lw);
        calc.settings.setRowRatioWidthLandscape(editor, lrw);
        calc.settings.setRowRatioHeightLandscape(editor, lrh);
        calc.settings.apply(editor);
    }

    public static BtnSizeLand get(Calculator calc){ return calc.binding.getLandSize(); }
    public void set(Calculator calc){ calc.binding.setLandSize(this); }
}
