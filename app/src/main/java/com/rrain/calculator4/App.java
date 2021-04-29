package com.rrain.calculator4;

import android.app.Application;

import com.jakewharton.threetenabp.AndroidThreeTen;
import com.rrain.calculator4.db.DBManager;

import lombok.Getter;

public class App extends Application {

    @Getter private static App app;
    @Getter private DBManager dbManager;
    @Getter private SettingsManager settingsManager;
    @Getter private HistoryManager historyManager;
    @Getter private ThemeManager themeManager;
    @Getter private Clipboard clipboard;
    //@Getter private DisplayManager displayManager;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        AndroidThreeTen.init(this);

        dbManager = new DBManager();
        settingsManager = new SettingsManager();
        themeManager = new ThemeManager();
        historyManager = new HistoryManager(dbManager.getHistoryEntryDao());
        clipboard = new Clipboard();
        //displayManager = new DisplayManager();

        AppElevator.upgrade();
    }
}
