package com.rrain.calculator4.dialog;

import android.content.Context;

import androidx.appcompat.app.AlertDialog;

import com.rrain.calculator4.R;

public class ConfirmDialog {

    public static void show(Context context, String msg, ConfirmAction action) {
        AlertDialog.Builder confirmationDialog = new AlertDialog.Builder(context);
        confirmationDialog.setMessage(msg);
        confirmationDialog.setNegativeButton(context.getString(R.string.cancel), (dialog, id) -> dialog.cancel());
        confirmationDialog.setPositiveButton(context.getString(R.string.ok), (dialog, id) -> {
            action.run();
            dialog.dismiss();
        });

        confirmationDialog.create().show();
    }

}
