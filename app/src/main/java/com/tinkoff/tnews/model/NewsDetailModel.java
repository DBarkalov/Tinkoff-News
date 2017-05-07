package com.tinkoff.tnews.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Dmitry Barkalov on 06.05.17.
 */

public class NewsDetailModel {
    //// TODO: use rlu cache
    private final Map<String, NewsDetailEntity> mMap;
    private final Set<String> mProgressMap;
    private DataListener mListener;

    public NewsDetailModel() {
        mMap = new HashMap<>();
        mProgressMap = new HashSet<>();
    }

    public void setListener(DataListener listener) {
        mListener = listener;
    }

    public NewsDetailEntity getDetail(String key) {
        return mMap.get(key);
    }

    public void put(String key, NewsDetailEntity entity) {
        mMap.put(key, entity);
        if (mListener != null) {
            mListener.onUpdate(key, entity);
        }
    }

    public boolean inProgress(String id) {
        return mProgressMap.contains(id);
    }

    public void setProgress(String key, boolean isProgress) {
        if (isProgress) {
            mProgressMap.add(key);
        } else {
            mProgressMap.remove(key);
        }
        if (mListener != null) {
            mListener.onProgressUpdate(key, isProgress);
        }
    }

    public void setError(String key, Exception e) {
        if (mListener != null) {
            mListener.onError(key, e);
        }
    }

    public static interface DataListener {
        void onProgressUpdate(String key, boolean progress);

        void onError(String key, Exception e);

        void onUpdate(String key, NewsDetailEntity entity);
    }
}
