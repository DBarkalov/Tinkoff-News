package com.tinkoff.tnews.data.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.tinkoff.tnews.model.NewsDetailEntity;
import com.tinkoff.tnews.model.NewsEntity;

/**
 * Created by Dmitry Barkalov on 05.05.17.
 */

public class NewsDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "news.db";

    private static final String TEXT_TYPE = " TEXT";
    private static final String DATE_TYPE = " INTEGER";
    private static final String COMMA_SEP = ",";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + NewsEntity.TABLE_NAME + " (" +
                    NewsEntity._ID + " INTEGER PRIMARY KEY," +
                    NewsEntity.COLUMN_NAME_ENTRY_ID + TEXT_TYPE + " UNIQUE" + COMMA_SEP +
                    NewsEntity.COLUMN_NAME_NAME + TEXT_TYPE + COMMA_SEP +
                    NewsEntity.COLUMN_NAME_TEXT + TEXT_TYPE + COMMA_SEP +
                    NewsEntity.COLUMN_NAME_DATE + DATE_TYPE +
                    " )";

    private static final String SQL_CREATE_ENTRIES_2 =
                    "CREATE TABLE " + NewsDetailEntity.TABLE_NAME + " (" +
                    NewsDetailEntity._ID + " INTEGER PRIMARY KEY," +
                    NewsDetailEntity.COLUMN_NAME_ENTRY_ID + TEXT_TYPE + " UNIQUE" + COMMA_SEP +
                    NewsDetailEntity.COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEP +
                    NewsDetailEntity.COLUMN_NAME_CONTENT + TEXT_TYPE  +
                    " )";


    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + NewsEntity.TABLE_NAME;

    private static final String SQL_DELETE_ENTRIES_2 =
                    "DROP TABLE IF EXISTS " + NewsDetailEntity.TABLE_NAME;


    public NewsDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
        db.execSQL(SQL_CREATE_ENTRIES_2);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        db.execSQL(SQL_DELETE_ENTRIES_2);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
