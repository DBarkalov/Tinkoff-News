package com.tinkoff.tnews.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dmitry Barkalov on 06.05.17.
 */

public class NewsModel {
    private final List<NewsEntity> mNewsEntities;
    private boolean mInProgress;
    private DataListener mDataListener;

    public NewsModel() {
        mNewsEntities = new ArrayList<>();
    }

    public List<NewsEntity> getNewsEntities() {
        return mNewsEntities;
    }

    public void setNewsEntities(List<NewsEntity> newsEntities) {
        mNewsEntities.clear();
        mNewsEntities.addAll(newsEntities);
        if(mDataListener != null){
            mDataListener.onListUpdate(mNewsEntities);
        }
    }

    public boolean inProgress() {
        return mInProgress;
    }

    public void setProgress(boolean b) {
        mInProgress = b;
        if(mDataListener != null){
            mDataListener.onProgressUpdate(mInProgress);
        }
    }

    public void setError(Exception error) {
        if(mDataListener != null){
            mDataListener.onError(error);
        }
    }

    public void setListener(DataListener listener) {
        mDataListener = listener;
    }

    public static interface DataListener {
        void onProgressUpdate(boolean progress);
        void onError(Exception error);
        void onListUpdate(List<NewsEntity> list);
    }
}
