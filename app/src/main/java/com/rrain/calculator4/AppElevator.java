package com.rrain.calculator4;

import android.content.SharedPreferences;

import com.rrain.calculator4.calculator.BtnSizeLand;
import com.rrain.calculator4.calculator.BtnSizePort;
import com.rrain.calculator4.db.HistoryEntry;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.ListIterator;

public class AppElevator {

    public static void upgrade(){

        SettingsManager settings = App.getApp().getSettingsManager();
        String appVersion = settings.getAppVersion();
        String newVersion;

        newVersion = "1.8.2";
        if (appVersion.compareTo(newVersion) < 0){

            migrateHistoryToDB182();

            newButtonsSize182();

            SharedPreferences.Editor editor = settings.getEditor();
            settings.setAppVersion(editor, appVersion=newVersion);
            settings.apply(editor);
        }

        newVersion = BuildConfig.VERSION_NAME;
        if (appVersion.compareTo(newVersion) < 0){

            // Do something

            SharedPreferences.Editor editor = settings.getEditor();
            settings.setAppVersion(editor, appVersion=newVersion);
            settings.apply(editor);
        }

        //Log.e("ELEVATORRR", "upgrade: ");
    }



    //последняя версия 1.8.1 последний билд 23
    //работает по проверке существования файла со старой историей
    @SuppressWarnings("unchecked")
    private static void migrateHistoryToDB182() {
        final String SAVED_HISTORY_CONTAINER = "History container";
        File h = App.getApp().getFileStreamPath(SAVED_HISTORY_CONTAINER);
        if (h.exists()){
            try {
                FileInputStream fis = App.getApp().openFileInput(SAVED_HISTORY_CONTAINER);
                ObjectInputStream oin = new ObjectInputStream(fis);
                ArrayList<String[]> historyContainer = (ArrayList<String[]>) oin.readObject();
                oin.close();

                //HistoryEntryDao hed = App.getApp().getDbManager().getHistoryEntryDao();
                HistoryManager historyManager = App.getApp().getHistoryManager();

                ListIterator<String[]> it = historyContainer.listIterator(historyContainer.size());


                while (it.hasPrevious()){
                    String[] entry = it.previous();
                    historyManager.add(new HistoryEntry(entry[0], entry[1]));
                    //Log.e("time", "migrateHistoryToDB: " + t);
                }

                //for(HistoryEntry e : hed.getAll()) Log.e("HE", "migrateHistoryToDB: "+e);

                h.delete();
                //ViewUtil.toastLong("История успешно переехала в БД!\nHistory were successfully moved to DB!");
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }



    //последняя версия 1.8.1 последний билд 23
    //новые размеры кнопок
    //работает по проверке существования настройки со старыми значениями
    private static void newButtonsSize182(){
        SettingsManager settings = App.getApp().getSettingsManager();
        SharedPreferences savedSettings = settings.getSharedPreferences();

        final String SETTINGS_TOP_ROW_HEIGHT_PORTRAIT = "Top Row Height Portrait";
        final String SETTINGS_MAIN_ROWS_HEIGHT_PORTRAIT = "Main Rows Height Portrait";
        final String SETTINGS_TOP_ROW_WIDTH_PORTRAIT = "Top Row Width Portrait";
        final String SETTINGS_MAIN_ROWS_WIDTH_PORTRAIT = "Main Rows Width Portrait";
        final String SETTINGS_ROWS_HEIGHT_LANDSCAPE = "Rows Height Landscape";
        final String SETTINGS_ROWS_WIDTH_LANDSCAPE = "Rows Width Landscape";

        if (!savedSettings.contains(SETTINGS_MAIN_ROWS_WIDTH_PORTRAIT)) return;

        int tw = savedSettings.getInt(SETTINGS_TOP_ROW_WIDTH_PORTRAIT, App.getApp().getResources().getDimensionPixelSize(R.dimen.number_buttons_side));
        int th = savedSettings.getInt(SETTINGS_TOP_ROW_HEIGHT_PORTRAIT, App.getApp().getResources().getDimensionPixelSize(R.dimen.number_buttons0_side));
        int mw = savedSettings.getInt(SETTINGS_MAIN_ROWS_WIDTH_PORTRAIT, App.getApp().getResources().getDimensionPixelSize(R.dimen.number_buttons_side));
        int mh = savedSettings.getInt(SETTINGS_MAIN_ROWS_HEIGHT_PORTRAIT, App.getApp().getResources().getDimensionPixelSize(R.dimen.number_buttons_side));
        int lw = savedSettings.getInt(SETTINGS_ROWS_WIDTH_LANDSCAPE, App.getApp().getResources().getDimensionPixelSize(R.dimen.number_buttons_side));
        int lh = savedSettings.getInt(SETTINGS_ROWS_HEIGHT_LANDSCAPE, App.getApp().getResources().getDimensionPixelSize(R.dimen.number_buttons0_side));

        BtnSizePort bp = new BtnSizePort(tw, th, mw, mh);
        BtnSizeLand bl = new BtnSizeLand(lw, lh);

        SharedPreferences.Editor editor = settings.getEditor();

        editor.remove(SETTINGS_TOP_ROW_HEIGHT_PORTRAIT);
        editor.remove(SETTINGS_MAIN_ROWS_HEIGHT_PORTRAIT);
        editor.remove(SETTINGS_TOP_ROW_WIDTH_PORTRAIT);
        editor.remove(SETTINGS_MAIN_ROWS_WIDTH_PORTRAIT);
        editor.remove(SETTINGS_ROWS_HEIGHT_LANDSCAPE);
        editor.remove(SETTINGS_ROWS_WIDTH_LANDSCAPE);

        editor.commit();

        settings.setTopRowWidthPortrait(editor, bp.tw);
        settings.setTopRowRatioWidthPortrait(editor, bp.trw);
        settings.setTopRowRatioHeightPortrait(editor, bp.trh);
        settings.setMainRowWidthPortrait(editor, bp.mw);
        settings.setMainRowRatioWidthPortrait(editor, bp.mrw);
        settings.setMainRowRatioHeightPortrait(editor, bp.mrh);

        settings.setRowWidthLandscape(editor, bl.lw);
        settings.setRowRatioWidthLandscape(editor, bl.lrw);
        settings.setRowRatioHeightLandscape(editor, bl.lrh);

        editor.commit();

        //ViewUtil.toastLong("Настройки размера кнопок успешно преобразованы в новый формат!\nButton's size settins were successfully converted to new format!");
    }

}
