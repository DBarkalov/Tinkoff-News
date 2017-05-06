package com.tinkoff.tnews.data.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.tinkoff.tnews.model.NewsDetailEntity;
import com.tinkoff.tnews.model.NewsEntity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Dmitry Barkalov on 05.05.17.
 */

public class NewsDatabase implements INewsDatabase {

    private static final String TAG = "NewsDatabase";
    private final NewsDbHelper mDbHelper;

    public NewsDatabase(Context context) {
        mDbHelper = new NewsDbHelper(context);
    }

    @Override
    public List<NewsEntity> getNewsList() {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        String[] projection = {
                NewsEntity.COLUMN_NAME_ENTRY_ID,
                NewsEntity.COLUMN_NAME_NAME,
                NewsEntity.COLUMN_NAME_TEXT,
                NewsEntity.COLUMN_NAME_DATE
        };

        String sortOrder =
                NewsEntity.COLUMN_NAME_DATE + " DESC";

        Cursor cursor = db.query(
                NewsEntity.TABLE_NAME,  // The table to query
                projection,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );

        Log.d(TAG, "getNewsList() cursor.getCount()=" + cursor.getCount());

        List<NewsEntity> list = new ArrayList<>();
        cursor.moveToFirst();
        try {
            while (cursor.moveToNext()) {
                String newsId = cursor.getString(cursor.getColumnIndexOrThrow(NewsEntity.COLUMN_NAME_ENTRY_ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(NewsEntity.COLUMN_NAME_NAME));
                String text = cursor.getString(cursor.getColumnIndexOrThrow(NewsEntity.COLUMN_NAME_TEXT));
                long date = cursor.getLong(cursor.getColumnIndexOrThrow(NewsEntity.COLUMN_NAME_DATE));
                list.add(new NewsEntity(newsId, name, text, date));

            }
        } finally {
            cursor.close();
            db.close();
        }
        Log.d(TAG, "getNewsList() size=" + list.size());
        return list;
    }


    @Override
    public void updateNewsList(List<NewsEntity> list) {
        Set<String> existIsdMap = getNewsId();
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        db.beginTransaction();
        try {
            for (NewsEntity entity : list) {
                if (!existIsdMap.contains(entity.getNewsId())) {
                    insetNewsEntity(db, entity);
                }
                //TODO  merge new items - update if exist
                //     - delete if not
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            db.close();
        }
    }

    private Set<String> getNewsId() {
        Set<String> set = new HashSet<>();
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        String[] projection = {
                NewsEntity.COLUMN_NAME_ENTRY_ID,
        };
        Cursor cursor = db.query(
                NewsEntity.TABLE_NAME,  // The table to query
                projection,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );

        cursor.moveToFirst();
        try {
            while (cursor.moveToNext()) {
                String newsId = cursor.getString(cursor.getColumnIndexOrThrow(NewsEntity.COLUMN_NAME_ENTRY_ID));
                set.add(newsId);

            }
        } finally {
            cursor.close();
            db.close();
        }
        return set;
    }

    private void insetNewsEntity(SQLiteDatabase db, NewsEntity entity) {
        ContentValues values = new ContentValues();
        values.put(NewsEntity.COLUMN_NAME_ENTRY_ID, entity.getNewsId());
        values.put(NewsEntity.COLUMN_NAME_NAME, entity.getName());
        values.put(NewsEntity.COLUMN_NAME_TEXT, entity.getText());
        values.put(NewsEntity.COLUMN_NAME_DATE, entity.getDate());
        long newRowId = db.insert(
                NewsEntity.TABLE_NAME,
                null,
                values);
        Log.d(TAG, "insetNewsEntity id=" +newRowId);
    }

    @Override
    public NewsDetailEntity getNewsDetailEntity(String newsId) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                NewsDetailEntity.COLUMN_NAME_ENTRY_ID,
                NewsDetailEntity.COLUMN_NAME_TITLE,
                NewsDetailEntity.COLUMN_NAME_CONTENT
        };

        Cursor cursor = db.query(
                NewsDetailEntity.TABLE_NAME,  // The table to query
                projection,                               // The columns to return
                NewsDetailEntity.COLUMN_NAME_ENTRY_ID + "=?",                                // The columns for the WHERE clause
                new String[]{newsId},                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );
        NewsDetailEntity entity = null;
        if (cursor != null && cursor.moveToFirst()) {
            try {
                final String title = cursor.getString(cursor.getColumnIndexOrThrow(NewsDetailEntity.COLUMN_NAME_TITLE));
                final String content = cursor.getString(cursor.getColumnIndexOrThrow(NewsDetailEntity.COLUMN_NAME_CONTENT));
                entity = new NewsDetailEntity(newsId, title, content);

            } finally {
                cursor.close();
                db.close();
            }
        }
        return entity;
    }

    @Override
    public void updateNewsDetailEntity(NewsDetailEntity entity) {
        //TODO use lastModify field for update
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NewsDetailEntity.COLUMN_NAME_ENTRY_ID, entity.getNewsId());
        values.put(NewsDetailEntity.COLUMN_NAME_TITLE, entity.getTitle());
        values.put(NewsDetailEntity.COLUMN_NAME_CONTENT, entity.getContent());
        long newRowId = db.insert(
                NewsDetailEntity.TABLE_NAME,
                null,
                values);
        db.close();
    }

}
