package com.rrain.calculator4;

import android.content.Context;
import android.content.SharedPreferences;

import lombok.Getter;

public class SettingsManager {

    private static final String SETTINGS = "Settings";// имя файла настроек

    private static final String SETTINGS_ENABLE_AUTOCALCULATION = "Enable Autocalculation";
    private static final String SETTINGS_ENABLE_SECOND_FIELD = "Enable Second Field";
    private static final String SETTINGS_ENABLE_ROUNDING = "Enable Rounding";
    private static final String SETTINGS_ROUNDING_ACCURACY = "Rounding Accuracy";
    private static final String SETTINGS_NUMBER_FORMAT = "Number Format";
    private static final String SETTINGS_DIGIT_SEPARATOR = "Digit Separator";
    private static final String SETTINGS_ENABLE_CONVERSION_TOAST = "Enable conversion toast";
    private static final String SETTINGS_ENABLE_SAVING_BY_EQUALS = "Enable saving by equals";


    // TODO: 29.08.2020 old
    /*private static final String SETTINGS_TOP_ROW_HEIGHT_PORTRAIT = "Top Row Height Portrait"; //remove
    private static final String SETTINGS_MAIN_ROWS_HEIGHT_PORTRAIT = "Main Rows Height Portrait"; //remove
    private static final String SETTINGS_TOP_ROW_WIDTH_PORTRAIT = "Top Row Width Portrait"; //remove
    private static final String SETTINGS_MAIN_ROWS_WIDTH_PORTRAIT = "Main Rows Width Portrait"; //remove
    private static final String SETTINGS_ROWS_HEIGHT_LANDSCAPE = "Rows Height Landscape"; //remove
    private static final String SETTINGS_ROWS_WIDTH_LANDSCAPE = "Rows Width Landscape"; //remove*/
    // TODO: 29.08.2020 new
    private static final String SETTINGS_TOP_ROW_WIDTH_PORTRAIT = "Top row width portrait";
    private static final String SETTINGS_TOP_ROW_RATIO_WIDTH_PORTRAIT = "Top row ratio width portrait";
    private static final String SETTINGS_TOP_ROW_RATIO_HEIGHT_PORTRAIT = "Top row ratio height portrait";
    private static final String SETTINGS_MAIN_ROW_WIDTH_PORTRAIT = "Main row width portrait";
    private static final String SETTINGS_MAIN_ROW_RATIO_WIDTH_PORTRAIT = "Main row ratio width portrait";
    private static final String SETTINGS_MAIN_ROW_RATIO_HEIGHT_PORTRAIT = "Main row ratio height portrait";
    private static final String SETTINGS_ROW_WIDTH_LANDSCAPE = "Row width landscape";
    private static final String SETTINGS_ROW_RATIO_WIDTH_LANDSCAPE = "Row ratio width landscape";
    private static final String SETTINGS_ROW_RATIO_HEIGHT_LANDSCAPE = "Row ratio height landscape";


    private static final String SETTINGS_ANGLE_UNIT = "Angle Unit";
    private static final String SETTINGS_RADIX = "Radix";
    private static final String SETTINGS_FOCUSED_ELEM = "Focused Elem";
    private static final String SETTINGS_EXPRESSION1 = "Expression1";
    private static final String SETTINGS_EXPRESSION1_SEL_START = "Expression1 Sel Start";
    private static final String SETTINGS_EXPRESSION1_SEL_END = "Expression1 Sel End";
    private static final String SETTINGS_RESULT1 = "Result1";
    private static final String SETTINGS_RESULT1_IS_COLOR_PRIMARY = "Is Result1 Color Primary";
    private static final String SETTINGS_EXPRESSION2 = "Expression2";
    private static final String SETTINGS_EXPRESSION2_SEL_START = "Expression2 Sel Start";
    private static final String SETTINGS_EXPRESSION2_SEL_END = "Expression2 Sel End";
    private static final String SETTINGS_RESULT2 = "Result2";
    private static final String SETTINGS_RESULT2_IS_COLOR_PRIMARY = "Is Result2 Color Primary";

    private static final String SETTINGS_HISTORY_LIMIT = "Max number of history entries";
    private static final String SETTINGS_ENABLE_OLD_HISTORY_DELETING = "Enable autodelete of old history entries";

    private static final String SETTINGS_THEME = "Theme";

    private static final String APP_VERSION = "app version";


    @Getter private SharedPreferences sharedPreferences;

    public SettingsManager() {
        sharedPreferences = App.getApp().getSharedPreferences(SETTINGS, Context.MODE_PRIVATE);
    }

    public SharedPreferences.Editor getEditor(){
        return sharedPreferences.edit();
    }
    public void apply(SharedPreferences.Editor editor){
        editor.apply();
    }

    public boolean isAutocalculationEnabled(){
        return sharedPreferences.getBoolean(SETTINGS_ENABLE_AUTOCALCULATION, true);
    }
    public void enableAutocalculation(SharedPreferences.Editor editor, boolean enable){
        editor.putBoolean(SETTINGS_ENABLE_AUTOCALCULATION, enable);
    }

    public boolean isSecondFieldEnabled(){
        return sharedPreferences.getBoolean(SETTINGS_ENABLE_SECOND_FIELD, false);
    }
    public void enableSecondField(SharedPreferences.Editor editor, boolean enable){
        editor.putBoolean(SETTINGS_ENABLE_SECOND_FIELD, enable);
    }

    public boolean isRoundingEnabled(){
        return sharedPreferences.getBoolean(SETTINGS_ENABLE_ROUNDING, true);
    }
    public void enableRounding(SharedPreferences.Editor editor, boolean enable){
        editor.putBoolean(SETTINGS_ENABLE_ROUNDING, enable);
    }

    public int getRoundingAccuracy(){
        return sharedPreferences.getInt(SETTINGS_ROUNDING_ACCURACY, 13);
    }
    public void setRoundingAccuracy(SharedPreferences.Editor editor, int accuracy){
        editor.putInt(SETTINGS_ROUNDING_ACCURACY, accuracy);
    }

    public int getNumberFormat(){
        return sharedPreferences.getInt(SETTINGS_NUMBER_FORMAT, 0);
    }
    public void setNumberFormat(SharedPreferences.Editor editor, int format){
        editor.putInt(SETTINGS_NUMBER_FORMAT, format);
    }

    public String getDigitSeparator(){
        return sharedPreferences.getString(SETTINGS_DIGIT_SEPARATOR, " ");
    }
    public void setDigitSeparator(SharedPreferences.Editor editor, String digitSeparator){
        editor.putString(SETTINGS_DIGIT_SEPARATOR, digitSeparator);
    }

    public boolean isConversionToastEnabled(){
        return sharedPreferences.getBoolean(SETTINGS_ENABLE_CONVERSION_TOAST, false);
    }
    public void enableConversionToast(SharedPreferences.Editor editor, boolean enable){
        editor.putBoolean(SETTINGS_ENABLE_CONVERSION_TOAST, enable);
    }

    public boolean isSavingByEqualsEnabled(){
        return sharedPreferences.getBoolean(SETTINGS_ENABLE_SAVING_BY_EQUALS, true);
    }
    public void enableSavingByEquals(SharedPreferences.Editor editor, boolean enable){
        editor.putBoolean(SETTINGS_ENABLE_SAVING_BY_EQUALS, enable);
    }


    // TODO: 29.08.2020 old
    /*public int getTopRowHeightPortrait(){
        return savedSettings.getInt(SETTINGS_TOP_ROW_HEIGHT_PORTRAIT, App.getApp().getResources().getDimensionPixelSize(R.dimen.number_buttons0_side));
    }
    public void setTopRowHeightPortrait(SharedPreferences.Editor editor, int height){
        editor.putInt(SETTINGS_TOP_ROW_HEIGHT_PORTRAIT, height);
    }

    public int getMainRowsHeightPortrait(){
        return savedSettings.getInt(SETTINGS_MAIN_ROWS_HEIGHT_PORTRAIT, App.getApp().getResources().getDimensionPixelSize(R.dimen.number_buttons_side));
    }
    public void setMainRowsHeightPortrait(SharedPreferences.Editor editor, int height){
        editor.putInt(SETTINGS_MAIN_ROWS_HEIGHT_PORTRAIT, height);
    }

    public int getTopRowWidthPortrait(){
        return savedSettings.getInt(SETTINGS_TOP_ROW_WIDTH_PORTRAIT, App.getApp().getResources().getDimensionPixelSize(R.dimen.number_buttons_side));
    }
    public void setTopRowWidthPortrait(SharedPreferences.Editor editor, int width){
        editor.putInt(SETTINGS_TOP_ROW_WIDTH_PORTRAIT, width);
    }

    public int getMainRowsWidthPortrait(){
        return savedSettings.getInt(SETTINGS_MAIN_ROWS_WIDTH_PORTRAIT, App.getApp().getResources().getDimensionPixelSize(R.dimen.number_buttons_side));
    }
    public void setMainRowsWidthPortrait(SharedPreferences.Editor editor, int width){
        editor.putInt(SETTINGS_MAIN_ROWS_WIDTH_PORTRAIT, width);
    }

    public int getRowsHeightLandscape(){
        return savedSettings.getInt(SETTINGS_ROWS_HEIGHT_LANDSCAPE, App.getApp().getResources().getDimensionPixelSize(R.dimen.number_buttons0_side));
    }
    public void setRowsHeightLandscape(SharedPreferences.Editor editor, int height){
        editor.putInt(SETTINGS_ROWS_HEIGHT_LANDSCAPE, height);
    }

    public int getRowsWidthLandscape(){
        return savedSettings.getInt(SETTINGS_ROWS_WIDTH_LANDSCAPE, App.getApp().getResources().getDimensionPixelSize(R.dimen.number_buttons_side));
    }
    public void setRowsWidthLandscape(SharedPreferences.Editor editor, int width){
        editor.putInt(SETTINGS_ROWS_WIDTH_LANDSCAPE, width);
    }*/

    // TODO: 29.08.2020 new
    public float getTopRowWidthPortrait(float def){
        return sharedPreferences.getFloat(SETTINGS_TOP_ROW_WIDTH_PORTRAIT, def);
    }
    public void setTopRowWidthPortrait(SharedPreferences.Editor editor, float width){
        editor.putFloat(SETTINGS_TOP_ROW_WIDTH_PORTRAIT, width);
    }

    public float getTopRowRatioWidthPortrait(float def){
        return sharedPreferences.getFloat(SETTINGS_TOP_ROW_RATIO_WIDTH_PORTRAIT, def);
    }
    public void setTopRowRatioWidthPortrait(SharedPreferences.Editor editor, float ratioW){
        editor.putFloat(SETTINGS_TOP_ROW_RATIO_WIDTH_PORTRAIT, ratioW);
    }

    public float getTopRowRatioHeightPortrait(float def){
        return sharedPreferences.getFloat(SETTINGS_TOP_ROW_RATIO_HEIGHT_PORTRAIT, def);
    }
    public void setTopRowRatioHeightPortrait(SharedPreferences.Editor editor, float ratioH){
        editor.putFloat(SETTINGS_TOP_ROW_RATIO_HEIGHT_PORTRAIT, ratioH);
    }

    public float getMainRowWidthPortrait(float def){
        return sharedPreferences.getFloat(SETTINGS_MAIN_ROW_WIDTH_PORTRAIT, def);
    }
    public void setMainRowWidthPortrait(SharedPreferences.Editor editor, float width){
        editor.putFloat(SETTINGS_MAIN_ROW_WIDTH_PORTRAIT, width);
    }

    public float getMainRowRatioWidthPortrait(float def){
        return sharedPreferences.getFloat(SETTINGS_MAIN_ROW_RATIO_WIDTH_PORTRAIT, def);
    }
    public void setMainRowRatioWidthPortrait(SharedPreferences.Editor editor, float ratioW){
        editor.putFloat(SETTINGS_MAIN_ROW_RATIO_WIDTH_PORTRAIT, ratioW);
    }

    public float getMainRowRatioHeightPortrait(float def){
        return sharedPreferences.getFloat(SETTINGS_MAIN_ROW_RATIO_HEIGHT_PORTRAIT, def);
    }
    public void setMainRowRatioHeightPortrait(SharedPreferences.Editor editor, float ratioH){
        editor.putFloat(SETTINGS_MAIN_ROW_RATIO_HEIGHT_PORTRAIT, ratioH);
    }

    public float getRowWidthLandscape(float def){
        return sharedPreferences.getFloat(SETTINGS_ROW_WIDTH_LANDSCAPE, def);
    }
    public void setRowWidthLandscape(SharedPreferences.Editor editor, float width){
        editor.putFloat(SETTINGS_ROW_WIDTH_LANDSCAPE, width);
    }

    public float getRowRatioWidthLandscape(float def){
        return sharedPreferences.getFloat(SETTINGS_ROW_RATIO_WIDTH_LANDSCAPE, def);
    }
    public void setRowRatioWidthLandscape(SharedPreferences.Editor editor, float ratioW){
        editor.putFloat(SETTINGS_ROW_RATIO_WIDTH_LANDSCAPE, ratioW);
    }

    public float getRowRatioHeightLandscape(float def){
        return sharedPreferences.getFloat(SETTINGS_ROW_RATIO_HEIGHT_LANDSCAPE, def);
    }
    public void setRowRatioHeightLandscape(SharedPreferences.Editor editor, float ratioH){
        editor.putFloat(SETTINGS_ROW_RATIO_HEIGHT_LANDSCAPE, ratioH);
    }








    public int getAngleUnit(){
        return sharedPreferences.getInt(SETTINGS_ANGLE_UNIT, 0);
    }
    public void setAngleUnit(SharedPreferences.Editor editor, int angle){
        editor.putInt(SETTINGS_ANGLE_UNIT, angle);
    }

    public int getRadix(){
        return sharedPreferences.getInt(SETTINGS_RADIX, 10);
    }
    public void setRadix(SharedPreferences.Editor editor, int radix){
        editor.putInt(SETTINGS_RADIX, radix);
    }

    public int getFocusedElem(){
        return sharedPreferences.getInt(SETTINGS_FOCUSED_ELEM, 0);
    }
    public void setFocusedElem(SharedPreferences.Editor editor, int elem){
        editor.putInt(SETTINGS_FOCUSED_ELEM, elem);
    }

    public String getExpression1(){
        return sharedPreferences.getString(SETTINGS_EXPRESSION1, "");
    }
    public void setExpression1(SharedPreferences.Editor editor, String expression){
        editor.putString(SETTINGS_EXPRESSION1, expression);
    }

    public int getExpression1SelStart(){
        return sharedPreferences.getInt(SETTINGS_EXPRESSION1_SEL_START, 0); // TODO: 22.08.2020  expressionEditText1.getText().length()
    }
    public void setExpression1SelStart(SharedPreferences.Editor editor, int sel){
        editor.putInt(SETTINGS_EXPRESSION1_SEL_START, sel);
    }

    public int getExpression1SelEnd(){
        return sharedPreferences.getInt(SETTINGS_EXPRESSION1_SEL_END, 0); // TODO: 22.08.2020  expressionEditText1.getText().length()
    }
    public void setExpression1SelEnd(SharedPreferences.Editor editor, int sel){
        editor.putInt(SETTINGS_EXPRESSION1_SEL_END, sel);
    }

    public String getResult1(){
        return sharedPreferences.getString(SETTINGS_RESULT1, "");
    }
    public void setResult1(SharedPreferences.Editor editor, String result){
        editor.putString(SETTINGS_RESULT1, result);
    }

    public boolean isResult1ColorPrimary(){
        return sharedPreferences.getBoolean(SETTINGS_RESULT1_IS_COLOR_PRIMARY, true);
    }
    public void setResult1ColorPrimary(SharedPreferences.Editor editor, boolean primary){
        editor.putBoolean(SETTINGS_RESULT1_IS_COLOR_PRIMARY, primary);
    }

    public String getExpression2(){
        return sharedPreferences.getString(SETTINGS_EXPRESSION2, "");
    }
    public void setExpression2(SharedPreferences.Editor editor, String expression){
        editor.putString(SETTINGS_EXPRESSION2, expression);
    }

    public int getExpression2SelStart(){
        return sharedPreferences.getInt(SETTINGS_EXPRESSION2_SEL_START, 0); // TODO: 22.08.2020  expressionEditText2.getText().length()
    }
    public void setExpression2SelStart(SharedPreferences.Editor editor, int sel){
        editor.putInt(SETTINGS_EXPRESSION2_SEL_START, sel);
    }

    public int getExpression2SelEnd(){
        return sharedPreferences.getInt(SETTINGS_EXPRESSION2_SEL_END, 0); // TODO: 22.08.2020  expressionEditText2.getText().length()
    }
    public void setExpression2SelEnd(SharedPreferences.Editor editor, int sel){
        editor.putInt(SETTINGS_EXPRESSION2_SEL_END, sel);
    }

    public String getResult2(){
        return sharedPreferences.getString(SETTINGS_RESULT2, "");
    }
    public void setResult2(SharedPreferences.Editor editor, String result){
        editor.putString(SETTINGS_RESULT2, result);
    }

    public boolean isResult2ColorPrimary(){
        return sharedPreferences.getBoolean(SETTINGS_RESULT2_IS_COLOR_PRIMARY, true);
    }
    public void setResult2ColorPrimary(SharedPreferences.Editor editor, boolean primary){
        editor.putBoolean(SETTINGS_RESULT2_IS_COLOR_PRIMARY, primary);
    }


    public int getHistoryLimit(){
        return sharedPreferences.getInt(SETTINGS_HISTORY_LIMIT, 200);
    }
    public void setHistoryLimit(SharedPreferences.Editor editor, int limit){
        editor.putInt(SETTINGS_HISTORY_LIMIT, limit);
    }

    public boolean isOldHistoryDeletingEnabled(){
        return sharedPreferences.getBoolean(SETTINGS_ENABLE_OLD_HISTORY_DELETING, true);
    }
    public void enableOldHistoryDeleting(SharedPreferences.Editor editor, boolean delete){
        editor.putBoolean(SETTINGS_ENABLE_OLD_HISTORY_DELETING, delete);
    }


    public String getTheme(){
        return sharedPreferences.getString(SETTINGS_THEME, ThemeManager.defaultTheme);
    }
    public void setTheme(SharedPreferences.Editor editor, String theme){
        editor.putString(SETTINGS_THEME, theme);
    }


    public String getAppVersion(){
        return sharedPreferences.getString(APP_VERSION, "1.8.1");
    }
    public void setAppVersion(SharedPreferences.Editor editor, String appVersion){
        editor.putString(APP_VERSION, appVersion);
    }

}
