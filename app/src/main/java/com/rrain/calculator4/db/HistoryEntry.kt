package com.rrain.calculator4.db;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import org.threeten.bp.ZoneId;
import org.threeten.bp.ZonedDateTime;
import org.threeten.bp.format.DateTimeFormatter;

import java.util.Locale;

import lombok.Getter;
import lombok.Setter;

//имя класса - имя таблицы, если не указать явно
//имена полей - имена столбцов, если не указать явно
@Entity(tableName = HistoryEntry.TABLE_NAME)
public class HistoryEntry {

    @Ignore
    public static final String TABLE_NAME = "history";

    @Ignore
    @Getter
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.ENGLISH); //2020-08-26T06:53:27.609+0000

    @PrimaryKey(autoGenerate = true)
    @Getter @Setter
    private long id;

    @Getter @Setter
    private String tag; //null сохраняется как есть

    @Getter @Setter
    private String expression;

    @Getter @Setter
    private String result;

    @Getter @Setter
    private String time;

    @Getter @Setter
    private boolean favorite;

    public HistoryEntry() { }

    @Ignore
    public HistoryEntry(String expression, String result, String time) {
        tag = null;
        this.expression = expression;
        this.result = result;
        this.time = time;
        this.favorite = false;
    }

    @Ignore
    public HistoryEntry(String expression, String result) {
        tag = null;
        this.expression = expression;
        this.result = result;
        ZonedDateTime time = ZonedDateTime.now(ZoneId.systemDefault());
        this.time = time.format(dateTimeFormatter);
        this.favorite = false;
    }

    @NonNull
    @Override
    public String toString() {
        return String.format("id = %s tag = %s expression = %s result = %s time = %s favorite = %s", id, tag, expression, result, time, favorite);
    }


}
