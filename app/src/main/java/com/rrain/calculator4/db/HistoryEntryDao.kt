package com.rrain.calculator4.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface HistoryEntryDao {
  @get:Query("SELECT * FROM " + HistoryEntry.TABLE_NAME)
  val allNoSort: MutableList<HistoryEntry>
  
  @get:Query("SELECT * FROM " + HistoryEntry.TABLE_NAME + " ORDER BY time DESC")
  val all: MutableList<HistoryEntry>
  
  @get:Query("SELECT * FROM " + HistoryEntry.TABLE_NAME + " ORDER BY time DESC")
  val allLive: LiveData<List<HistoryEntry>>
  
  @Query("SELECT * FROM " + HistoryEntry.TABLE_NAME + " WHERE id = :id")
  fun getById(id: Long): HistoryEntry?
  
  @get:Query("SELECT * FROM " + HistoryEntry.TABLE_NAME + " ORDER BY time ASC LIMIT 1")
  val last: HistoryEntry?
  
  @get:Query("SELECT * FROM " + HistoryEntry.TABLE_NAME + " ORDER BY time DESC LIMIT 1")
  val first: HistoryEntry?
  
  @Insert
  fun insert(entry: HistoryEntry)
  
  @Insert
  fun insertList(entries: MutableList<HistoryEntry>)
  
  @Update
  fun update(entry: HistoryEntry)
  
  @Delete
  fun delete(entry: HistoryEntry)
  
  @Query("DELETE FROM " + HistoryEntry.TABLE_NAME)
  fun clear()
  
  @Query("SELECT COUNT(*) FROM " + HistoryEntry.TABLE_NAME)
  fun size(): Int
}
