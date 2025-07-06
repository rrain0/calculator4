package com.rrain.calculator4.db;

import androidx.room.Room;

import com.rrain.calculator4.App;

public class DBManager {

    public static final String APP_DB_FILENAME = "appDB";

    private AppDB appDB;

    public DBManager() {
        appDB = Room.databaseBuilder(App.Companion.getApp(), AppDB.class, APP_DB_FILENAME)
                //.allowMainThreadQueries()
                .build();
    }

    public HistoryEntryDao getHistoryEntryDao(){
        return appDB.historyEntryDao();
    }
}
