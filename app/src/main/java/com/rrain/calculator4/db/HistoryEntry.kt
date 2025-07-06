package com.rrain.calculator4.db

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import org.threeten.bp.ZoneId
import org.threeten.bp.ZonedDateTime
import org.threeten.bp.format.DateTimeFormatter
import java.util.Locale


// имя класса - имя таблицы, если не указать явно
// имена полей - имена столбцов, если не указать явно
@Entity(tableName = HistoryEntry.Companion.TABLE_NAME)
class HistoryEntry {
  @PrimaryKey(autoGenerate = true)
  private var id: Long = 0
  fun getId() = id
  fun setId(id: Long) { this.id = id }
  
  private var tag: String? = null // null сохраняется как есть
  fun getTag() = tag
  fun setTag(tag: String?) { this.tag = tag }
  
  private var expression: String? = null
  fun getExpression() = expression
  fun setExpression(expression: String?) { this.expression = expression }
  
  private var result: String? = null
  fun getResult() = result
  fun setResult(result: String?) { this.result = result }
  
  private var time: String? = null
  fun getTime() = time
  fun setTime(time: String?) { this.time = time }
  
  private var favorite = false
  fun isFavorite() = favorite
  fun setFavorite(favorite: Boolean) { this.favorite = favorite }
  
  constructor()
  
  @Ignore
  constructor(expression: String?, result: String?, time: String?) {
    tag = null
    this.expression = expression
    this.result = result
    this.time = time
    this.favorite = false
  }
  
  @Ignore
  constructor(expression: String?, result: String?) {
    tag = null
    this.expression = expression
    this.result = result
    val time = ZonedDateTime.now(ZoneId.systemDefault())
    this.time = time.format(dateTimeFormatter)
    this.favorite = false
  }
  
  override fun toString(): String {
    return String.format(
      "id = %s tag = %s expression = %s result = %s time = %s favorite = %s",
      id,
      tag,
      expression,
      result,
      time,
      favorite
    )
  }
  
  
  companion object {
    @Ignore
    const val TABLE_NAME: String = "history"
    
    @Ignore
    private val dateTimeFormatter: DateTimeFormatter = (
      DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.ENGLISH) // 2020-08-26T06:53:27.609+0000
    )
    fun getDateTimeFormatter() = dateTimeFormatter
  }
}
