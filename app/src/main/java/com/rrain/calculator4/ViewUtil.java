package com.rrain.calculator4;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.lang.reflect.Method;

public class ViewUtil {

    public static String getTxt(Activity activity, int viewId){
        return ((TextView)activity.findViewById(viewId)).getText().toString();
    }

    public static void setTxt(Activity activity, int viewId, String txt){
        ((TextView)activity.findViewById(viewId)).setText(txt);
    }

    public static void showBackBtn(AppCompatActivity activity, boolean show){
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(show);
        activity.getSupportActionBar().setDisplayShowHomeEnabled(show);
    }

    public static void toastShort(Context context, CharSequence txt){
        Toast.makeText(context, txt, Toast.LENGTH_SHORT).show();
    }

    public static void toastLong(Context context, CharSequence txt){
        Toast.makeText(context, txt, Toast.LENGTH_SHORT).show();
    }

    public static void toastShort(CharSequence txt){
        Toast.makeText(App.getApp(), txt, Toast.LENGTH_SHORT).show();
    }

    public static void toastLong(CharSequence txt){
        Toast.makeText(App.getApp(), txt, Toast.LENGTH_SHORT).show();
    }



    // TODO: 20.08.2018 Полезный код
    /*imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);*/

    /*imm.toggleSoftInput(InputMethodManager.HIDE_NOT_ALWAYS,InputMethodManager.SHOW_FORCED);*/
    /*Button btn = (Button)findViewById(R.id.control_kbd);*///только прятать клаву
    /*imm.hideSoftInputFromWindow(btn.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);*/
    /*getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);*/

    public static void showHideKbd (Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);
    }

    public static void showKeyboard(Context context){
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    public static void hideKeyboard(Context context){
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }


    public static void showKeyboardOnFocus(boolean showKeyboardOnFocus, EditText editText) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) editText.setShowSoftInputOnFocus(showKeyboardOnFocus);
        else try {
            final Method method = EditText.class.getMethod("setShowSoftInputOnFocus", boolean.class);
            method.setAccessible(true);
            method.invoke(editText, showKeyboardOnFocus);
        } catch (Exception e) {}
    }


}
