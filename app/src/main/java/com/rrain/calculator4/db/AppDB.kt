package com.rrain.calculator4.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [HistoryEntry::class], version = 1)
abstract class AppDB : RoomDatabase() {
  abstract fun historyEntryDao(): HistoryEntryDao?
}
