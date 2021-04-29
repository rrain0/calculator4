package com.rrain.calculator4.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface HistoryEntryDao {

    @Query("SELECT * FROM " + HistoryEntry.TABLE_NAME)
    List<HistoryEntry> getAllNoSort();

    @Query("SELECT * FROM " + HistoryEntry.TABLE_NAME + " ORDER BY time DESC")
    List<HistoryEntry> getAll();

    @Query("SELECT * FROM " + HistoryEntry.TABLE_NAME + " ORDER BY time DESC")
    LiveData<List<HistoryEntry>> getAllLive();

    @Query("SELECT * FROM " + HistoryEntry.TABLE_NAME + " WHERE id = :id")
    HistoryEntry getById(long id);

    @Query("SELECT * FROM " + HistoryEntry.TABLE_NAME + " ORDER BY time ASC LIMIT 1")
    HistoryEntry getLast();

    @Query("SELECT * FROM " + HistoryEntry.TABLE_NAME + " ORDER BY time DESC LIMIT 1")
    HistoryEntry getFirst();

    @Insert
    void insert(HistoryEntry entry);

    @Insert
    void insertList(List<HistoryEntry> entries);

    @Update
    void update(HistoryEntry entry);

    @Delete
    void delete(HistoryEntry entry);

    @Query("DELETE FROM " + HistoryEntry.TABLE_NAME)
    void clear();

    @Query("SELECT COUNT(*) FROM " + HistoryEntry.TABLE_NAME)
    int size();


}
