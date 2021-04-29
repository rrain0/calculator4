package com.rrain.calculator4.dialog;

import android.app.Activity;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.google.android.flexbox.FlexboxLayout;
import com.rrain.calculator4.R;
import com.rrain.calculator4.ViewUtil;

public class InputDialog {
    public static final int INPUT_STRING = 0;
    public static final int INPUT_POSITIVE_INTEGER = 1;
    public static final int INPUT_INTEGER = 2;
    //public static final int INPUT_DECIMAL = 3;
    public static final int INPUT_TIME = 4;
    public static final int INPUT_TIME_DECIMAL = 5;


    public static void show(Activity activity, String msg, int inputType, InputAction action, String... presets) {
        LayoutInflater inflater = (LayoutInflater) activity.getLayoutInflater();
        final LinearLayout inputLayout = (LinearLayout)inflater.inflate(R.layout.alert_dialog_input, null);
        final TextView msgTV = (TextView)inputLayout.findViewById(R.id.msg);
        EditText input = (EditText)inputLayout.findViewById(R.id.input);
        final FlexboxLayout flex = inputLayout.findViewById(R.id.input_dialog_flex_layout);

        //editText.setRawInputType(InputType.TYPE_CLASS_NUMBER)
        //editText.setRawInputType(Configuration.KEYBOARD_QWERTY);

        msgTV.setText(msg);
        switch (inputType){
            case INPUT_STRING: /*input.setInputType(InputType.TYPE_DATETIME_VARIATION_TIME|InputType.TYPE_CLASS_DATETIME);*/break;
            case INPUT_POSITIVE_INTEGER: input.setInputType(InputType.TYPE_CLASS_NUMBER); break;
            case INPUT_INTEGER: input.setInputType(InputType.TYPE_NUMBER_FLAG_SIGNED|InputType.TYPE_CLASS_NUMBER); break;
            //case INPUT_DECIMAL: input.setInputType(InputType.TYPE_CLASS_PHONE); break; // TODO: 30.08.2020 input type
            case INPUT_TIME: input.setInputType(InputType.TYPE_DATETIME_VARIATION_TIME|InputType.TYPE_CLASS_DATETIME); break;
            case INPUT_TIME_DECIMAL:

                inputLayout.removeViewAt(2);
                input = (EditText) inflater.inflate(R.layout.edit_text_decimal_time, null);
                inputLayout.addView(input, 2);

                /*input.setFilters(new InputFilter[]{
                        new InputFilter() {
                            @Override
                            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                                return source.toString().replaceAll("[^\\d:\\.]","");
                            }
                        }
                });
                input.setInputType(InputType.TYPE_DATETIME_VARIATION_TIME|InputType.TYPE_CLASS_DATETIME);*/
                break;
        }


        final EditText inputFinal = input;

        //Log.e("INPUTTT", "show: "+ Arrays.toString(input.getFilters()));

        //DialogInterface.OnClickListener

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity);// TODO: 29.08.2020  подтвердить по энтеру
        dialogBuilder
                .setView(inputLayout)
                .setOnCancelListener((dialogInterface -> ViewUtil.hideKeyboard(activity)))
                .setOnDismissListener((dialogInterface -> ViewUtil.hideKeyboard(activity)))
                .setNegativeButton(activity.getString(R.string.cancel), (dialog, id) -> dialog.cancel())
                .setPositiveButton(activity.getString(R.string.ok), (dialog, id) -> {
                    String txt = inputFinal.getText().toString();
                    switch (inputType){
                        case INPUT_STRING: break; //тут всё ок
                        case INPUT_POSITIVE_INTEGER: break; //тут всё ок
                        case INPUT_INTEGER: break; //тут всё ок
                        //case INPUT_DECIMAL:  break;
                        case INPUT_TIME:  break; //тут всё ок
                        case INPUT_TIME_DECIMAL:  break; //тут всё ок
                    }
                    dialog.dismiss();
                    action.run(txt);
                });

        AlertDialog d = dialogBuilder.create();
        //d.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor();

        View.OnClickListener onClickListener = v -> {
            switch (v.getId()){
                case R.id.rounded_tv:
                    action.run(((TextView)v).getText().toString());
                    d.dismiss();
                    break;
            }
        };
        for(String s : presets){
            TextView tv = (TextView)inflater.inflate(R.layout.rounded_text_view, null);
            tv.setText(s);
            tv.setOnClickListener(onClickListener);
            flex.addView(tv);
        }

        d.show();

        ViewUtil.showKeyboard(activity);// TODO: 29.08.2020 прятание клавы не весгда работает
    }

}
