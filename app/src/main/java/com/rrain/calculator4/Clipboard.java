package com.rrain.calculator4;

import android.content.ClipData;
import android.content.ClipboardManager;

import com.annimon.stream.Objects;

import static android.content.Context.CLIPBOARD_SERVICE;

public class Clipboard {

    private ClipboardManager clipboardManager;

    public Clipboard() {
        clipboardManager = (ClipboardManager)App.getApp().getSystemService(CLIPBOARD_SERVICE);
    }

    public void add(String s){
        clipboardManager.setPrimaryClip(ClipData.newPlainText("text", s));
    }

    public String getLast(){
        return Objects.requireNonNullElse(clipboardManager.getPrimaryClip().getItemAt(0).getText().toString(), "");
    }
}
