package com.rrain.calculator4.dialog;

import android.content.Context;

import androidx.appcompat.app.AlertDialog;

import com.rrain.calculator4.App;
import com.rrain.calculator4.R;

public class InfoDialog {

    public static void show(Context context, String msg){
        AlertDialog.Builder infoDialog = new AlertDialog.Builder(context);

        infoDialog.setMessage(msg);
        infoDialog.setNegativeButton(App.Companion.getApp().getText(R.string.back), (dialog, id) -> dialog.cancel());
        infoDialog.create().show();
    }

}
