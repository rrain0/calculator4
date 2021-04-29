package com.rrain.calculator4.calculator;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.drawable.GradientDrawable;
import android.widget.Button;
import android.widget.TextView;

import com.rrain.calculator4.R;

public class ViewModel {
    private Button radixBtn;
    private Button angleUnitButton;

    private int toolbarBtnColor;
    private int toolbarBtnColorLight;
    private int toolbarBtnColorWarn;

    private int onToolbarBtnColor;
    private int onToolbarBtnColorLight;
    private int onToolbarBtnColorWarn;


    private TextView resultTV1;
    private TextView resultTV2;

    private int resultTextColorActual;
    private int resultTextColor;


    public ViewModel(Calculator calc) {
        angleUnitButton = (Button) calc.findViewById(R.id.btn_angle_unit);
        radixBtn = (Button) calc.findViewById(R.id.btn_radix);
        resultTV1 = (TextView)calc.findViewById(R.id.result_text_view_1);
        resultTV2 = (TextView)calc.findViewById(R.id.result_text_view_2);
        //получение атрибутов
        TypedArray ta = calc.getTheme().obtainStyledAttributes(new int[]{
                R.attr.toolbarBtnColor,
                R.attr.toolbarBtnColorLight,
                R.attr.toolbarBtnColorWarn,

                R.attr.onToolbarBtnColor,
                R.attr.onToolbarBtnColorLight,
                R.attr.onToolbarBtnColorWarn,

                R.attr.resultColor,
                R.attr.resultColorVariant,
                R.attr.resultColorLand
        });
        toolbarBtnColor = ta.getColor(0, calc.getResources().getColor(R.color.black));
        toolbarBtnColorLight = ta.getColor(1, calc.getResources().getColor(R.color.darkGreen));
        toolbarBtnColorWarn = ta.getColor(2, calc.getResources().getColor(R.color.darkRed));

        onToolbarBtnColor = ta.getColor(3, calc.getResources().getColor(R.color.white));
        onToolbarBtnColorLight = ta.getColor(4, calc.getResources().getColor(R.color.white));
        onToolbarBtnColorWarn = ta.getColor(5, calc.getResources().getColor(R.color.white));

        resultTextColorActual = ta.getColor(
                calc.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT?6:8,
            calc.getResources().getColor(R.color.black)
        );
        resultTextColor = ta.getColor(7, calc.getResources().getColor(R.color.dark_gray_3));

        /*Log.e("width", "ViewModel: "+(((GridLayout)calc.findViewById(R.id.griddd)).width ));
        Log.e("width", "ViewModel: "+(((GridLayout.LayoutParams)calc.findViewById(R.id.result_scroll_1).getLayoutParams())).getMarginEnd());*/
        /*Log.e("width", "ViewModel: "+calc.findViewById(R.id.btn_delete_from_1).getWidth());
        resultTV1.setMinWidth(calc.findViewById(R.id.btn_delete_from_1).getWidth());*/
    }

    @SuppressLint("SetTextI18n")
    public void updateRadix(int radix){
        switch (radix) {
            case 2:
                radixBtn.setText("bin");
                ((GradientDrawable)radixBtn.getBackground()).setColor(toolbarBtnColorWarn);
                radixBtn.setTextColor(onToolbarBtnColorWarn);
                break;
            case 8:
                radixBtn.setText("oct");
                ((GradientDrawable)radixBtn.getBackground()).setColor(toolbarBtnColorWarn);
                radixBtn.setTextColor(onToolbarBtnColorWarn);
                break;
            case 10:
                radixBtn.setText("dec");
                ((GradientDrawable)radixBtn.getBackground()).setColor(toolbarBtnColor);
                radixBtn.setTextColor(onToolbarBtnColor);
                break;
            case 16:
                radixBtn.setText("hex");
                ((GradientDrawable)radixBtn.getBackground()).setColor(toolbarBtnColorWarn);
                radixBtn.setTextColor(onToolbarBtnColorWarn);
                break;
            default:
                radixBtn.setText(radix);
                ((GradientDrawable)radixBtn.getBackground()).setColor(toolbarBtnColorWarn);
                radixBtn.setTextColor(onToolbarBtnColorWarn);
                break;
        }
    }

    @SuppressLint("SetTextI18n")
    public void updateAngleUnit(int angleUnit){
        switch (angleUnit) {
            default: case 0:
                angleUnitButton.setText("Rad");
                ((GradientDrawable)angleUnitButton.getBackground()).setColor(toolbarBtnColorLight);
                angleUnitButton.setTextColor(onToolbarBtnColorLight);
                break;
            case 1:
                angleUnitButton.setText("Deg");
                ((GradientDrawable)angleUnitButton.getBackground()).setColor(toolbarBtnColorWarn);
                angleUnitButton.setTextColor(onToolbarBtnColorWarn);
                break;
        }
    }

    // TODO: 03.09.2020  actual -> primary text color
    public void updateResultTV1TextColor(boolean actual){
        resultTV1.setTextColor(actual?resultTextColorActual:resultTextColor);
    }

    public void updateResultTV2TextColor(boolean actual){
        resultTV2.setTextColor(actual?resultTextColorActual:resultTextColor);
    }

    public boolean isResultTV1TextColorActual(){
        return resultTV1.getCurrentTextColor()==resultTextColorActual;
    }

    public boolean isResultTV2TextColorActual(){
        return resultTV2.getCurrentTextColor()==resultTextColorActual;
    }

}
