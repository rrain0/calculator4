package com.rrain.calculator4.calculator;

import android.content.SharedPreferences;

import com.rrain.calculator4.DisplayManager;
import com.rrain.calculator4.Util;

public class BtnSizePort {
    public final float tw, trw, trh; //top width, top ratio width, top ratio height
    public final float th; //top row height
    public final float mw, mrw, mrh; //main width, main ratio width, main ratio height
    public final float mh; //main row height

    public float textTop1, textTop2, textTop3, textTop4, textMain1, textMain2, textMain3, textMain4; //textSizes

    //Default values
    public BtnSizePort(Calculator calc) {
        trw = 16; trh = 12;
        mrw = 1; mrh = 1;
        float k = 1f/2f;
        tw = (DisplayManager.getHeight(calc) * k) / (1*(trh/trw) + 5*(mrh/mrw));
        th = tw * (trh/trw);
        mw = tw;
        mh = mw * (mrh/mrw);
        calcTextSize();
    }
    public BtnSizePort(float topWidth, float topWidthRatio, float topHeightRatio, float mainWidth, float mainWidthRatio, float mainHeightRatio) {
        trw = topWidthRatio; trh = topHeightRatio;
        tw = topWidth;
        th = tw * (trh/trw);
        mrw = mainWidthRatio; mrh = mainHeightRatio;
        mw = mainWidth;
        mh = mw * (mrh/mrw);
        calcTextSize();
    }
    public BtnSizePort(float topWidth, float topHeight, float mainWidth, float mainHeight) {
        tw = topWidth;
        th = topHeight;
        trw = 16f;
        trh = Util.round3(topHeight*trw / topWidth);

        mw = mainWidth;
        mh = mainHeight;
        mrw = 16f;
        mrh = Util.round3(mainHeight*mrw / mainWidth);

        calcTextSize();
    }

    private void calcTextSize(){
        textTop1 = th * 0.28f;
        textTop2 = th * 0.45f;
        textTop3 = th * 0.2f;
        textTop4 = th * 0.7f;
        textMain1 = mh * 0.45f;
        textMain2 = mh * 0.35f;
        textMain3 = mh * 0.25f;
        textMain4 = th * 0.20f;
    }

    public boolean isSameWidth(){ return Util.equal3(tw, mw); }

    public BtnSizePort buildNew(boolean top, float width){
        return new BtnSizePort(!top?tw:width, trw, trh, top?mw:width, mrw, mrh);
    }
    public BtnSizePort buildNew(boolean top, float ratioW, float ratioH){
        return new BtnSizePort(tw, !top?trw:ratioW, !top?trh:ratioH, mw, top?mrw:ratioW, top?mrh:ratioH);
    }
    public BtnSizePort buildNew(float widthBoth){
        return new BtnSizePort(widthBoth, trw, trh, widthBoth, mrw, mrh);
    }

    public static BtnSizePort load(Calculator calc){
        BtnSizePort b = new BtnSizePort(calc); //default
        return new BtnSizePort(
                calc.settings.getTopRowWidthPortrait(b.tw),
                calc.settings.getTopRowRatioWidthPortrait(b.trw),
                calc.settings.getTopRowRatioHeightPortrait(b.trh),
                calc.settings.getMainRowWidthPortrait(b.mw),
                calc.settings.getMainRowRatioWidthPortrait(b.mrw),
                calc.settings.getMainRowRatioHeightPortrait(b.mrh)
        );
    }
    public void save(Calculator calc){
        SharedPreferences.Editor editor = calc.settings.getEditor();
        calc.settings.setTopRowWidthPortrait(editor, tw);
        calc.settings.setTopRowRatioWidthPortrait(editor, trw);
        calc.settings.setTopRowRatioHeightPortrait(editor, trh);
        calc.settings.setMainRowWidthPortrait(editor, mw);
        calc.settings.setMainRowRatioWidthPortrait(editor, mrw);
        calc.settings.setMainRowRatioHeightPortrait(editor, mrh);
        calc.settings.apply(editor);
    }

    public static BtnSizePort get(Calculator calc){ return calc.binding.getPortSize(); }
    public void set(Calculator calc){ calc.binding.setPortSize(this); }
}
