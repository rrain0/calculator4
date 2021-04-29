package com.rrain.calculator4;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.annimon.stream.Stream;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.rrain.calculator4.calculator.Calculator;
import com.rrain.calculator4.dialog.ConfirmDialog;
import com.rrain.calculator4.dialog.InfoDialog;
import com.rrain.calculator4.dialog.InputDialog;
import com.rrain.calculator4.dialog.SelectDialog;

import static com.rrain.calculator4.calculator.Calculator.theme;


public class SettingsActivity extends AppCompatActivity
        implements RadioGroup.OnCheckedChangeListener, View.OnClickListener{

    private SettingsManager settings;
    private HistoryManager history;
    private ThemeManager themeManager;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        settings = App.getApp().getSettingsManager();
        history = App.getApp().getHistoryManager();
        themeManager = App.getApp().getThemeManager();

        setTheme(themeManager.getStyleIdByName(theme));

        super.onCreate(savedInstanceState);

        setContentView(R.layout.settings_1_app_bar);

        Toolbar toolbar = (Toolbar) findViewById(R.id.settings_toolbar);
        setSupportActionBar(toolbar);
        ViewUtil.showBackBtn(this, true);
        toolbar.setNavigationOnClickListener(view->onBackPressed());

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        /*Spinner themeSpinner = (Spinner)findViewById(R.id.spinner_themes);
        switch (theme){
            case "Light": themeSpinner.setSelection(0); break;
            case "LightYellow": themeSpinner.setSelection(1); break;
            case "LightPink": themeSpinner.setSelection(2); break;
        }
        themeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                switch (position){
                    case 0:
                        if (theme.equals("Light")) return;
                        theme = "Light";
                        break;
                    case 1:
                        if (theme.equals("LightYellow")) return;
                        theme = "LightYellow";
                        break;
                    case 2:
                        if (theme.equals("LightPink")) return;
                        theme = "LightPink";
                        break;
                }

                Calculator.doRestart = true;
                restartActivity();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {  }
        });*/

        TextView themeBtn = (TextView) findViewById(R.id.theme_tv);
        themeBtn.setText(themeManager.getThemes().get(theme).getDisplayedName());
        themeBtn.setOnClickListener(this);

        CheckBox enableAutocalc = ((CheckBox) findViewById(R.id.enable_autocalculation_check_box));
        enableAutocalc.setChecked(Calculator.enableAutocalculation);
        enableAutocalc.setOnClickListener(this);

        CheckBox enableSecondField = ((CheckBox) findViewById(R.id.enable_second_field_check_box));
        enableSecondField.setChecked(Calculator.enableSecondField);
        enableSecondField.setOnClickListener(this);

        CheckBox enableRounding = ((CheckBox) findViewById(R.id.enable_rounding_check_box));
        enableRounding.setChecked(Calculator.enableRounding);
        enableRounding.setOnClickListener(this);

        findViewById(R.id.rounding_precision_layout).setOnClickListener(this);

        TextView roundingPrecisionTV = (TextView) findViewById(R.id.rounding_precision_tv);
        roundingPrecisionTV.setText(Calculator.roundingPrecision+"");


       /* Log.e("activated", "onCreate: "+roundingPrecisionTV.isActivated());
        roundingPrecisionTV.setActivated(false);
        Log.e("activated", "onCreate: "+roundingPrecisionTV.isActivated());*/
        //((EditText) findViewById(R.id.rounding_accuracy_edit_text)).setOnFocusChangeListener(this);

        RadioGroup formats = findViewById(R.id.radio_group_format);
        switch (Calculator.numberFormat){
            default: case 0: formats.check(R.id.format_normal); break;
            case 1: formats.check(R.id.format_scientific); break;
            case 3: formats.check(R.id.format_engineering); break;
        }
        formats.setOnCheckedChangeListener(this);

        RadioGroup digitSepators = findViewById(R.id.radio_group_digit_separator);
        switch (Calculator.digitSeparator){
            default: case "": digitSepators.check(R.id.digit_separator_none); break;
            case " ": digitSepators.check(R.id.digit_separator_space); break;
            case "_": digitSepators.check(R.id.digit_separator_low_line); break;
            case "'": digitSepators.check(R.id.digit_separator_apostrophe); break;
        }
        digitSepators.setOnCheckedChangeListener(this);

        /*Spinner digitSeparatorSpinner = (Spinner)findViewById(R.id.spinner_digit_separator);
        switch (Calculator.digitSeparator){
            case "": digitSeparatorSpinner.setSelection(0); break;
            case " ": digitSeparatorSpinner.setSelection(1); break;
            case "_": digitSeparatorSpinner.setSelection(2); break;
        }
        digitSeparatorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        if (Calculator.theme.equals("")) return;
                        Calculator.digitSeparator = "";
                        break;
                    case 1:
                        if (Calculator.theme.equals(" ")) return;
                        Calculator.digitSeparator = " ";
                        break;
                    case 2:
                        if (Calculator.theme.equals("_")) return;
                        Calculator.digitSeparator = "_";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/

        CheckBox enableHistoryLimit = ((CheckBox) findViewById(R.id.enable_autodeleting_of_history_check_box));
        enableHistoryLimit.setChecked(Calculator.enableAutodeleteOfHistory);
        enableHistoryLimit.setOnClickListener(this);

        //((EditText) findViewById(R.id.number_of_entries_edit_text)).setText(String.valueOf(Calculator.historyLimit));

        findViewById(R.id.history_limit_layout).setOnClickListener(this);

        TextView historyLimitTV = findViewById(R.id.history_limit_tv);
        historyLimitTV.setText(Calculator.historyLimit+"");

        ((CheckBox) findViewById(R.id.enable_add_to_history_by_equals)).setChecked(Calculator.enableSavingByEquals);

        findViewById(R.id.clear_history_tv).setOnClickListener(this);

        CheckBox enableAddToHistoryByEquals = ((CheckBox) findViewById(R.id.enable_add_to_history_by_equals));
        enableAddToHistoryByEquals.setChecked(Calculator.enableSavingByEquals);
        enableAddToHistoryByEquals.setOnClickListener(this);

        findViewById(R.id.change_button_size_tv).setOnClickListener(this);

        findViewById(R.id.change_button_size_info_tv).setOnClickListener(this);

        CheckBox enableConversionToast = ((CheckBox) findViewById(R.id.enable_conversion_toast_check_box));
        enableConversionToast.setChecked(Calculator.enableConversionToast);
        enableConversionToast.setOnClickListener(this);


        /*if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP){
            TypedArray ta = getTheme().obtainStyledAttributes(new int[]{R.attr.colorAccent, R.attr.settingsCardBgc});

            ((GradientDrawable)roundingPrecisionTV.getBackground()).setColor(ta.getColor(0, getResources().getColor(R.color.white)));
            ((GradientDrawable)historyLimitTV.getBackground()).setColor(ta.getColor(0, getResources().getColor(R.color.white)));

            ((GradientDrawable)findViewById(R.id.theme_layout).getBackground()).setColor(ta.getColor(1, getResources().getColor(R.color.white)));
            ((GradientDrawable)findViewById(R.id.enable_autocalculation_check_box).getBackground()).setColor(ta.getColor(1, getResources().getColor(R.color.white)));
            ((GradientDrawable)findViewById(R.id.enable_second_field_check_box).getBackground()).setColor(ta.getColor(1, getResources().getColor(R.color.white)));
            ((GradientDrawable)findViewById(R.id.rounding_layout).getBackground()).setColor(ta.getColor(1, getResources().getColor(R.color.white)));
            ((GradientDrawable)findViewById(R.id.number_format_layout).getBackground()).setColor(ta.getColor(1, getResources().getColor(R.color.white)));
            ((GradientDrawable)findViewById(R.id.digit_separator_layout).getBackground()).setColor(ta.getColor(1, getResources().getColor(R.color.white)));
            ((GradientDrawable)findViewById(R.id.history_layout).getBackground()).setColor(ta.getColor(1, getResources().getColor(R.color.white)));
            ((GradientDrawable)findViewById(R.id.button_size_layout).getBackground()).setColor(ta.getColor(1, getResources().getColor(R.color.white)));
            ((GradientDrawable)findViewById(R.id.debug_functions_layout).getBackground()).setColor(ta.getColor(1, getResources().getColor(R.color.white)));

        }*/


        //реклама
        AdView adView = (AdView)findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

    }

    @Override
    protected void onPause() {
        super.onPause();

        // Запоминаем данные настроек
        SharedPreferences.Editor editor = settings.getEditor();

        settings.setTheme(editor, theme);
        settings.enableAutocalculation(editor, Calculator.enableAutocalculation);
        settings.enableSecondField(editor, Calculator.enableSecondField);
        settings.enableRounding(editor, Calculator.enableRounding);
        settings.setRoundingAccuracy(editor, Calculator.roundingPrecision);
        settings.setNumberFormat(editor, Calculator.numberFormat);
        settings.setDigitSeparator(editor, Calculator.digitSeparator);
        settings.enableOldHistoryDeleting(editor, Calculator.enableAutodeleteOfHistory);
        settings.setHistoryLimit(editor, Calculator.historyLimit);
        settings.enableConversionToast(editor, Calculator.enableConversionToast);
        settings.enableSavingByEquals(editor, Calculator.enableSavingByEquals);

        settings.apply(editor);
    }

    private void restartActivity(){
        Intent i = new Intent( SettingsActivity.this , SettingsActivity.this.getClass() );
        SettingsActivity.this.finish();
        SettingsActivity.this.startActivity(i);
    }




    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
        /* найти индекс по элементу
        int radioButtonID = radioButtonGroup.getCheckedRadioButtonId();
        View radioButton = radioButtonGroup.findViewById(radioButtonID);
        int idx = radioButtonGroup.indexOfChild(radioButton);*/

        switch (radioGroup.getId()){
            case R.id.radio_group_format:
                switch (checkedId){
                    default: case R.id.format_normal: Calculator.numberFormat = 0; break;
                    case R.id.format_scientific: Calculator.numberFormat = 1; break;
                    case R.id.format_engineering: Calculator.numberFormat = 3; break;
                }
                break;
            case R.id.radio_group_digit_separator:
                switch (checkedId){
                    default: case R.id.digit_separator_none: Calculator.digitSeparator = ""; break;
                    case R.id.digit_separator_space: Calculator.digitSeparator = " "; break;
                    case R.id.digit_separator_low_line: Calculator.digitSeparator = "_"; break;
                    case R.id.digit_separator_apostrophe: Calculator.digitSeparator = "'"; break;
                }
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.theme_tv:
                String[] elems = Stream.of(themeManager.getThemes()).map(e -> e.getValue().getDisplayedName()).toArray(String[]::new);
                SelectDialog.show(this, "", elems, pos -> {// TODO: 25.08.2020 string R
                    theme = Stream.of(themeManager.getThemes()).skip(pos).findFirst().get().getKey();
                    //Calculator.doRestart = true;
                    Intent intent = new Intent();
                    intent.putExtra(Calculator.INTENT_CODE_RESTART, true);
                    setResult(RESULT_OK, intent);
                    restartActivity();
                });
                break;
            case R.id.enable_autocalculation_check_box:
                Calculator.enableAutocalculation = ((CheckBox)view).isChecked();
                break;
            case R.id.enable_second_field_check_box:
                Calculator.enableSecondField = ((CheckBox)view).isChecked();
                break;
            case R.id.enable_rounding_check_box:
                if (((CheckBox)view).isChecked()) Calculator.enableRounding = true;
                else {
                    Calculator.enableRounding = false;
                    ViewUtil.toastLong(getString(R.string.attention_disable_rounding));
                }
                //findViewById(R.id.rounding_precision_tv).setActivated(((CheckBox)view).isChecked());
                break;
            case R.id.rounding_precision_layout:
                InputDialog.show(this, getString(R.string.rounding_precision), InputDialog.INPUT_INTEGER,
                        input -> {
                            try {
                                Calculator.roundingPrecision = Integer.parseInt(input);
                                ViewUtil.setTxt(SettingsActivity.this, R.id.rounding_precision_tv, input);
                            }
                            catch (NumberFormatException e) {
                                ViewUtil.toastLong(SettingsActivity.this, getText(R.string.incorrect_expression));
                            }
                        },
                        "-6", "-3", "0", "3", "6", "9", "13", "15");
                break;
            case R.id.enable_autodeleting_of_history_check_box:
                Calculator.enableAutodeleteOfHistory = ((CheckBox)view).isChecked();
                break;
            case R.id.history_limit_layout:
                InputDialog.show(this, getString(R.string.delete_if_more_than), InputDialog.INPUT_POSITIVE_INTEGER,
                        input -> {
                            try {
                                int i = Integer.parseInt(input);
                                if (i<0) throw new NumberFormatException();
                                Calculator.historyLimit = i;
                                ViewUtil.setTxt(SettingsActivity.this, R.id.history_limit_tv, input);
                            }
                            catch (NumberFormatException e) {
                                ViewUtil.toastLong(SettingsActivity.this, getText(R.string.incorrect_expression));
                            }
                        },
                        "50", "100", "200", "500", "1000", "2000");
                break;
            case R.id.clear_history_tv:
                ConfirmDialog.show(this, getString(R.string.clear_history)+"?", history::clear);// TODO: 31.08.2020 шторкой снизу
                break;
            case R.id.enable_add_to_history_by_equals:
                Calculator.enableSavingByEquals = ((CheckBox)view).isChecked();
                break;
            case R.id.change_button_size_tv:
                //Calculator.changeButtonsSizeDialogEnabled = true;
                Intent intent = new Intent();
                intent.putExtra(Calculator.INTENT_CODE_CHANGE_BUTTONS_SIZE, true);
                setResult(RESULT_OK, intent);
                onBackPressed();
                break;
            case R.id.change_button_size_info_tv:
                InfoDialog.show(this, getString(R.string.change_dimens_hint));
                break;
            case R.id.enable_conversion_toast_check_box:
                Calculator.enableConversionToast = ((CheckBox)view).isChecked();
                break;
        }
    }
}
