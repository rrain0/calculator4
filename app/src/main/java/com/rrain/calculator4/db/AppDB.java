package com.rrain.calculator4.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {HistoryEntry.class}, version = 1)
public abstract class AppDB extends RoomDatabase {
    public abstract HistoryEntryDao historyEntryDao();
}
