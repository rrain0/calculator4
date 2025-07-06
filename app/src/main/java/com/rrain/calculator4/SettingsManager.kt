package com.rrain.calculator4

import android.content.Context
import android.content.SharedPreferences
import com.rrain.calculator4.App.Companion.getApp

class SettingsManager {
  private val sharedPreferences: SharedPreferences
  fun getSharedPreferences() = sharedPreferences
  
  init {
    sharedPreferences = getApp()!!.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE)
  }
  
  val editor: SharedPreferences.Editor?
    get() = sharedPreferences.edit()
  
  fun apply(editor: SharedPreferences.Editor) {
    editor.apply()
  }
  
  val isAutocalculationEnabled: Boolean
    get() = sharedPreferences.getBoolean(
      SETTINGS_ENABLE_AUTOCALCULATION,
      true
    )
  
  fun enableAutocalculation(editor: SharedPreferences.Editor, enable: Boolean) {
    editor.putBoolean(SETTINGS_ENABLE_AUTOCALCULATION, enable)
  }
  
  val isSecondFieldEnabled: Boolean
    get() = sharedPreferences.getBoolean(
      SETTINGS_ENABLE_SECOND_FIELD,
      false
    )
  
  fun enableSecondField(editor: SharedPreferences.Editor, enable: Boolean) {
    editor.putBoolean(SETTINGS_ENABLE_SECOND_FIELD, enable)
  }
  
  val isRoundingEnabled: Boolean
    get() = sharedPreferences.getBoolean(SETTINGS_ENABLE_ROUNDING, true)
  
  fun enableRounding(editor: SharedPreferences.Editor, enable: Boolean) {
    editor.putBoolean(SETTINGS_ENABLE_ROUNDING, enable)
  }
  
  val roundingAccuracy: Int
    get() = sharedPreferences.getInt(SETTINGS_ROUNDING_ACCURACY, 13)
  
  fun setRoundingAccuracy(editor: SharedPreferences.Editor, accuracy: Int) {
    editor.putInt(SETTINGS_ROUNDING_ACCURACY, accuracy)
  }
  
  val numberFormat: Int
    get() = sharedPreferences.getInt(SETTINGS_NUMBER_FORMAT, 0)
  
  fun setNumberFormat(editor: SharedPreferences.Editor, format: Int) {
    editor.putInt(SETTINGS_NUMBER_FORMAT, format)
  }
  
  val digitSeparator: String
    get() = sharedPreferences.getString(SETTINGS_DIGIT_SEPARATOR, " ")!!
  
  fun setDigitSeparator(editor: SharedPreferences.Editor, digitSeparator: String?) {
    editor.putString(SETTINGS_DIGIT_SEPARATOR, digitSeparator)
  }
  
  val isConversionToastEnabled: Boolean
    get() = sharedPreferences.getBoolean(
      SETTINGS_ENABLE_CONVERSION_TOAST,
      false
    )
  
  fun enableConversionToast(editor: SharedPreferences.Editor, enable: Boolean) {
    editor.putBoolean(SETTINGS_ENABLE_CONVERSION_TOAST, enable)
  }
  
  val isSavingByEqualsEnabled: Boolean
    get() = sharedPreferences.getBoolean(
      SETTINGS_ENABLE_SAVING_BY_EQUALS,
      true
    )
  
  fun enableSavingByEquals(editor: SharedPreferences.Editor, enable: Boolean) {
    editor.putBoolean(SETTINGS_ENABLE_SAVING_BY_EQUALS, enable)
  }
  
  
  // TODO: 29.08.2020 old
  /*public int getTopRowHeightPortrait(){
        return savedSettings.getInt(SETTINGS_TOP_ROW_HEIGHT_PORTRAIT, App.Companion.getApp().getResources().getDimensionPixelSize(R.dimen.number_buttons0_side));
    }
    public void setTopRowHeightPortrait(SharedPreferences.Editor editor, int height){
        editor.putInt(SETTINGS_TOP_ROW_HEIGHT_PORTRAIT, height);
    }

    public int getMainRowsHeightPortrait(){
        return savedSettings.getInt(SETTINGS_MAIN_ROWS_HEIGHT_PORTRAIT, App.Companion.getApp().getResources().getDimensionPixelSize(R.dimen.number_buttons_side));
    }
    public void setMainRowsHeightPortrait(SharedPreferences.Editor editor, int height){
        editor.putInt(SETTINGS_MAIN_ROWS_HEIGHT_PORTRAIT, height);
    }

    public int getTopRowWidthPortrait(){
        return savedSettings.getInt(SETTINGS_TOP_ROW_WIDTH_PORTRAIT, App.Companion.getApp().getResources().getDimensionPixelSize(R.dimen.number_buttons_side));
    }
    public void setTopRowWidthPortrait(SharedPreferences.Editor editor, int width){
        editor.putInt(SETTINGS_TOP_ROW_WIDTH_PORTRAIT, width);
    }

    public int getMainRowsWidthPortrait(){
        return savedSettings.getInt(SETTINGS_MAIN_ROWS_WIDTH_PORTRAIT, App.Companion.getApp().getResources().getDimensionPixelSize(R.dimen.number_buttons_side));
    }
    public void setMainRowsWidthPortrait(SharedPreferences.Editor editor, int width){
        editor.putInt(SETTINGS_MAIN_ROWS_WIDTH_PORTRAIT, width);
    }

    public int getRowsHeightLandscape(){
        return savedSettings.getInt(SETTINGS_ROWS_HEIGHT_LANDSCAPE, App.Companion.getApp().getResources().getDimensionPixelSize(R.dimen.number_buttons0_side));
    }
    public void setRowsHeightLandscape(SharedPreferences.Editor editor, int height){
        editor.putInt(SETTINGS_ROWS_HEIGHT_LANDSCAPE, height);
    }

    public int getRowsWidthLandscape(){
        return savedSettings.getInt(SETTINGS_ROWS_WIDTH_LANDSCAPE, App.Companion.getApp().getResources().getDimensionPixelSize(R.dimen.number_buttons_side));
    }
    public void setRowsWidthLandscape(SharedPreferences.Editor editor, int width){
        editor.putInt(SETTINGS_ROWS_WIDTH_LANDSCAPE, width);
    }*/
  // TODO: 29.08.2020 new
  fun getTopRowWidthPortrait(def: Float): Float {
    return sharedPreferences.getFloat(SETTINGS_TOP_ROW_WIDTH_PORTRAIT, def)
  }
  
  fun setTopRowWidthPortrait(editor: SharedPreferences.Editor, width: Float) {
    editor.putFloat(SETTINGS_TOP_ROW_WIDTH_PORTRAIT, width)
  }
  
  fun getTopRowRatioWidthPortrait(def: Float): Float {
    return sharedPreferences.getFloat(SETTINGS_TOP_ROW_RATIO_WIDTH_PORTRAIT, def)
  }
  
  fun setTopRowRatioWidthPortrait(editor: SharedPreferences.Editor, ratioW: Float) {
    editor.putFloat(SETTINGS_TOP_ROW_RATIO_WIDTH_PORTRAIT, ratioW)
  }
  
  fun getTopRowRatioHeightPortrait(def: Float): Float {
    return sharedPreferences.getFloat(SETTINGS_TOP_ROW_RATIO_HEIGHT_PORTRAIT, def)
  }
  
  fun setTopRowRatioHeightPortrait(editor: SharedPreferences.Editor, ratioH: Float) {
    editor.putFloat(SETTINGS_TOP_ROW_RATIO_HEIGHT_PORTRAIT, ratioH)
  }
  
  fun getMainRowWidthPortrait(def: Float): Float {
    return sharedPreferences.getFloat(SETTINGS_MAIN_ROW_WIDTH_PORTRAIT, def)
  }
  
  fun setMainRowWidthPortrait(editor: SharedPreferences.Editor, width: Float) {
    editor.putFloat(SETTINGS_MAIN_ROW_WIDTH_PORTRAIT, width)
  }
  
  fun getMainRowRatioWidthPortrait(def: Float): Float {
    return sharedPreferences.getFloat(SETTINGS_MAIN_ROW_RATIO_WIDTH_PORTRAIT, def)
  }
  
  fun setMainRowRatioWidthPortrait(editor: SharedPreferences.Editor, ratioW: Float) {
    editor.putFloat(SETTINGS_MAIN_ROW_RATIO_WIDTH_PORTRAIT, ratioW)
  }
  
  fun getMainRowRatioHeightPortrait(def: Float): Float {
    return sharedPreferences.getFloat(SETTINGS_MAIN_ROW_RATIO_HEIGHT_PORTRAIT, def)
  }
  
  fun setMainRowRatioHeightPortrait(editor: SharedPreferences.Editor, ratioH: Float) {
    editor.putFloat(SETTINGS_MAIN_ROW_RATIO_HEIGHT_PORTRAIT, ratioH)
  }
  
  fun getRowWidthLandscape(def: Float): Float {
    return sharedPreferences.getFloat(SETTINGS_ROW_WIDTH_LANDSCAPE, def)
  }
  
  fun setRowWidthLandscape(editor: SharedPreferences.Editor, width: Float) {
    editor.putFloat(SETTINGS_ROW_WIDTH_LANDSCAPE, width)
  }
  
  fun getRowRatioWidthLandscape(def: Float): Float {
    return sharedPreferences.getFloat(SETTINGS_ROW_RATIO_WIDTH_LANDSCAPE, def)
  }
  
  fun setRowRatioWidthLandscape(editor: SharedPreferences.Editor, ratioW: Float) {
    editor.putFloat(SETTINGS_ROW_RATIO_WIDTH_LANDSCAPE, ratioW)
  }
  
  fun getRowRatioHeightLandscape(def: Float): Float {
    return sharedPreferences.getFloat(SETTINGS_ROW_RATIO_HEIGHT_LANDSCAPE, def)
  }
  
  fun setRowRatioHeightLandscape(editor: SharedPreferences.Editor, ratioH: Float) {
    editor.putFloat(SETTINGS_ROW_RATIO_HEIGHT_LANDSCAPE, ratioH)
  }
  
  
  
  
  val angleUnit: Int
    get() = sharedPreferences.getInt(SETTINGS_ANGLE_UNIT, 0)
  
  fun setAngleUnit(editor: SharedPreferences.Editor, angle: Int) {
    editor.putInt(SETTINGS_ANGLE_UNIT, angle)
  }
  
  val radix: Int
    get() = sharedPreferences.getInt(SETTINGS_RADIX, 10)
  
  fun setRadix(editor: SharedPreferences.Editor, radix: Int) {
    editor.putInt(SETTINGS_RADIX, radix)
  }
  
  val focusedElem: Int
    get() = sharedPreferences.getInt(SETTINGS_FOCUSED_ELEM, 0)
  
  fun setFocusedElem(editor: SharedPreferences.Editor, elem: Int) {
    editor.putInt(SETTINGS_FOCUSED_ELEM, elem)
  }
  
  val expression1: String
    get() = sharedPreferences.getString(SETTINGS_EXPRESSION1, "")!!
  
  fun setExpression1(editor: SharedPreferences.Editor, expression: String?) {
    editor.putString(SETTINGS_EXPRESSION1, expression)
  }
  
  val expression1SelStart: Int
    get() = sharedPreferences.getInt(
      SETTINGS_EXPRESSION1_SEL_START,
      0
    ) // TODO: 22.08.2020  getExpressionEditText1().getText().length()
  
  fun setExpression1SelStart(editor: SharedPreferences.Editor, sel: Int) {
    editor.putInt(SETTINGS_EXPRESSION1_SEL_START, sel)
  }
  
  val expression1SelEnd: Int
    get() = sharedPreferences.getInt(
      SETTINGS_EXPRESSION1_SEL_END,
      0
    ) // TODO: 22.08.2020  getExpressionEditText1().getText().length()
  
  fun setExpression1SelEnd(editor: SharedPreferences.Editor, sel: Int) {
    editor.putInt(SETTINGS_EXPRESSION1_SEL_END, sel)
  }
  
  val result1: String
    get() = sharedPreferences.getString(SETTINGS_RESULT1, "")!!
  
  fun setResult1(editor: SharedPreferences.Editor, result: String?) {
    editor.putString(SETTINGS_RESULT1, result)
  }
  
  val isResult1ColorPrimary: Boolean
    get() = sharedPreferences.getBoolean(
      SETTINGS_RESULT1_IS_COLOR_PRIMARY,
      true
    )
  
  fun setResult1ColorPrimary(editor: SharedPreferences.Editor, primary: Boolean) {
    editor.putBoolean(SETTINGS_RESULT1_IS_COLOR_PRIMARY, primary)
  }
  
  val expression2: String
    get() = sharedPreferences.getString(SETTINGS_EXPRESSION2, "")!!
  
  fun setExpression2(editor: SharedPreferences.Editor, expression: String?) {
    editor.putString(SETTINGS_EXPRESSION2, expression)
  }
  
  val expression2SelStart: Int
    get() = sharedPreferences.getInt(
      SETTINGS_EXPRESSION2_SEL_START,
      0
    ) // TODO: 22.08.2020  getExpressionEditText2().getText().length()
  
  fun setExpression2SelStart(editor: SharedPreferences.Editor, sel: Int) {
    editor.putInt(SETTINGS_EXPRESSION2_SEL_START, sel)
  }
  
  val expression2SelEnd: Int
    get() = sharedPreferences.getInt(
      SETTINGS_EXPRESSION2_SEL_END,
      0
    ) // TODO: 22.08.2020  getExpressionEditText2().getText().length()
  
  fun setExpression2SelEnd(editor: SharedPreferences.Editor, sel: Int) {
    editor.putInt(SETTINGS_EXPRESSION2_SEL_END, sel)
  }
  
  val result2: String
    get() = sharedPreferences.getString(SETTINGS_RESULT2, "")!!
  
  fun setResult2(editor: SharedPreferences.Editor, result: String?) {
    editor.putString(SETTINGS_RESULT2, result)
  }
  
  val isResult2ColorPrimary: Boolean
    get() = sharedPreferences.getBoolean(
      SETTINGS_RESULT2_IS_COLOR_PRIMARY,
      true
    )
  
  fun setResult2ColorPrimary(editor: SharedPreferences.Editor, primary: Boolean) {
    editor.putBoolean(SETTINGS_RESULT2_IS_COLOR_PRIMARY, primary)
  }
  
  
  val historyLimit: Int
    get() = sharedPreferences.getInt(SETTINGS_HISTORY_LIMIT, 200)
  
  fun setHistoryLimit(editor: SharedPreferences.Editor, limit: Int) {
    editor.putInt(SETTINGS_HISTORY_LIMIT, limit)
  }
  
  val isOldHistoryDeletingEnabled: Boolean
    get() = sharedPreferences.getBoolean(
      SETTINGS_ENABLE_OLD_HISTORY_DELETING,
      true
    )
  
  fun enableOldHistoryDeleting(editor: SharedPreferences.Editor, delete: Boolean) {
    editor.putBoolean(SETTINGS_ENABLE_OLD_HISTORY_DELETING, delete)
  }
  
  
  val theme: String
    get() = sharedPreferences.getString(
      com.rrain.calculator4.SettingsManager.Companion.SETTINGS_THEME,
      ThemeManager.defaultTheme
    )!!
  
  fun setTheme(editor: SharedPreferences.Editor, theme: String?) {
    editor.putString(SETTINGS_THEME, theme)
  }
  
  
  val appVersion: String
    get() = sharedPreferences.getString(APP_VERSION, "1.8.1")!!
  
  fun setAppVersion(editor: SharedPreferences.Editor, appVersion: String?) {
    editor.putString(APP_VERSION, appVersion)
  }
  
  companion object {
    private const val SETTINGS = "Settings" // имя файла настроек
    
    private const val SETTINGS_ENABLE_AUTOCALCULATION = "Enable Autocalculation"
    private const val SETTINGS_ENABLE_SECOND_FIELD = "Enable Second Field"
    private const val SETTINGS_ENABLE_ROUNDING = "Enable Rounding"
    private const val SETTINGS_ROUNDING_ACCURACY = "Rounding Accuracy"
    private const val SETTINGS_NUMBER_FORMAT = "Number Format"
    private const val SETTINGS_DIGIT_SEPARATOR = "Digit Separator"
    private const val SETTINGS_ENABLE_CONVERSION_TOAST = "Enable conversion toast"
    private const val SETTINGS_ENABLE_SAVING_BY_EQUALS = "Enable saving by equals"
    
    
    // TODO: 29.08.2020 old
    /*private static final String SETTINGS_TOP_ROW_HEIGHT_PORTRAIT = "Top Row Height Portrait"; //remove
    private static final String SETTINGS_MAIN_ROWS_HEIGHT_PORTRAIT = "Main Rows Height Portrait"; //remove
    private static final String SETTINGS_TOP_ROW_WIDTH_PORTRAIT = "Top Row Width Portrait"; //remove
    private static final String SETTINGS_MAIN_ROWS_WIDTH_PORTRAIT = "Main Rows Width Portrait"; //remove
    private static final String SETTINGS_ROWS_HEIGHT_LANDSCAPE = "Rows Height Landscape"; //remove
    private static final String SETTINGS_ROWS_WIDTH_LANDSCAPE = "Rows Width Landscape"; //remove*/
    // TODO: 29.08.2020 new
    private const val SETTINGS_TOP_ROW_WIDTH_PORTRAIT = "Top row width portrait"
    private const val SETTINGS_TOP_ROW_RATIO_WIDTH_PORTRAIT = "Top row ratio width portrait"
    private const val SETTINGS_TOP_ROW_RATIO_HEIGHT_PORTRAIT = "Top row ratio height portrait"
    private const val SETTINGS_MAIN_ROW_WIDTH_PORTRAIT = "Main row width portrait"
    private const val SETTINGS_MAIN_ROW_RATIO_WIDTH_PORTRAIT = "Main row ratio width portrait"
    private const val SETTINGS_MAIN_ROW_RATIO_HEIGHT_PORTRAIT = "Main row ratio height portrait"
    private const val SETTINGS_ROW_WIDTH_LANDSCAPE = "Row width landscape"
    private const val SETTINGS_ROW_RATIO_WIDTH_LANDSCAPE = "Row ratio width landscape"
    private const val SETTINGS_ROW_RATIO_HEIGHT_LANDSCAPE = "Row ratio height landscape"
    
    
    private const val SETTINGS_ANGLE_UNIT = "Angle Unit"
    private const val SETTINGS_RADIX = "Radix"
    private const val SETTINGS_FOCUSED_ELEM = "Focused Elem"
    private const val SETTINGS_EXPRESSION1 = "Expression1"
    private const val SETTINGS_EXPRESSION1_SEL_START = "Expression1 Sel Start"
    private const val SETTINGS_EXPRESSION1_SEL_END = "Expression1 Sel End"
    private const val SETTINGS_RESULT1 = "Result1"
    private const val SETTINGS_RESULT1_IS_COLOR_PRIMARY = "Is Result1 Color Primary"
    private const val SETTINGS_EXPRESSION2 = "Expression2"
    private const val SETTINGS_EXPRESSION2_SEL_START = "Expression2 Sel Start"
    private const val SETTINGS_EXPRESSION2_SEL_END = "Expression2 Sel End"
    private const val SETTINGS_RESULT2 = "Result2"
    private const val SETTINGS_RESULT2_IS_COLOR_PRIMARY = "Is Result2 Color Primary"
    
    private const val SETTINGS_HISTORY_LIMIT = "Max number of history entries"
    private const val SETTINGS_ENABLE_OLD_HISTORY_DELETING = "Enable autodelete of old history entries"
    
    private const val SETTINGS_THEME = "Theme"
    
    private const val APP_VERSION = "app version"
  }
}
