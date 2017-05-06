package com.tinkoff.tnews.model;

import android.provider.BaseColumns;

/**
 * Created by Dmitry Barkalov on 05.05.17.
 */

public class NewsDetailEntity implements BaseColumns {
    public static final String TABLE_NAME = "news_detail";
    public static final String COLUMN_NAME_ENTRY_ID = "news_id";
    public static final String COLUMN_NAME_TITLE = "title";
    public static final String COLUMN_NAME_CONTENT = "content";

    private String  mId;
    private String  mTitle;
    private String mContent;

    public NewsDetailEntity(String id, String title, String content) {
        mId = id;
        mTitle = title;
        mContent = content;
    }

    public String getContent() {
        return mContent;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getNewsId() {
        return mId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NewsDetailEntity entity = (NewsDetailEntity) o;

        if (mId != null ? !mId.equals(entity.mId) : entity.mId != null) return false;
        if (mTitle != null ? !mTitle.equals(entity.mTitle) : entity.mTitle != null) return false;
        return mContent != null ? mContent.equals(entity.mContent) : entity.mContent == null;

    }

    @Override
    public int hashCode() {
        int result = mId != null ? mId.hashCode() : 0;
        result = 31 * result + (mTitle != null ? mTitle.hashCode() : 0);
        result = 31 * result + (mContent != null ? mContent.hashCode() : 0);
        return result;
    }
}
