package com.tinkoff.tnews.model;

import android.content.Context;
import android.provider.BaseColumns;

import java.text.DateFormat;
import java.util.Date;

/**
 * Created by Dmitry Barkalov on 05.05.17.
 */

public class NewsEntity implements BaseColumns {
    public static final String TABLE_NAME = "news_titles";
    public static final String COLUMN_NAME_ENTRY_ID = "news_id";
    public static final String COLUMN_NAME_NAME = "news_name";
    public static final String COLUMN_NAME_TEXT = "news_text";
    public static final String COLUMN_NAME_DATE = "news_date";

    private String mId;
    private String mName;
    private String mText;
    long mDate;

    public NewsEntity(String id, String name, String text, long date) {
        mId = id;
        mName = name;
        mText = text;
        mDate = date;
    }

    public String getNewsId(){
        return mId;
    }

    public String getName(){
        return mName;
    }

    public String getText(){
        return mText;
    }

    public long getDate(){
        return mDate;
    }

    public String getDateString(Context context){
        Date date = new Date(mDate);
        DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(context);
        return  dateFormat.format(date);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NewsEntity that = (NewsEntity) o;

        if (mDate != that.mDate) return false;
        if (mId != null ? !mId.equals(that.mId) : that.mId != null) return false;
        if (mName != null ? !mName.equals(that.mName) : that.mName != null) return false;
        return mText != null ? mText.equals(that.mText) : that.mText == null;

    }

    @Override
    public int hashCode() {
        int result = mId != null ? mId.hashCode() : 0;
        result = 31 * result + (mName != null ? mName.hashCode() : 0);
        result = 31 * result + (mText != null ? mText.hashCode() : 0);
        result = 31 * result + (int) (mDate ^ (mDate >>> 32));
        return result;
    }
}
