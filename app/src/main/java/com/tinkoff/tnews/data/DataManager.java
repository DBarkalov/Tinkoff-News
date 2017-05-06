package com.tinkoff.tnews.data;

import android.content.Context;

import com.tinkoff.tnews.data.network.INewsRemoteApi;
import com.tinkoff.tnews.data.sql.INewsDatabase;
import com.tinkoff.tnews.data.sql.NewsDatabase;
import com.tinkoff.tnews.data.network.NewsRemoteApi;
import com.tinkoff.tnews.model.NewsDetailEntity;
import com.tinkoff.tnews.model.NewsEntity;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

/**
 * Created by Dmitry Barkalov on 05.05.17.
 */

public class DataManager implements IDataManager {

    private final INewsDatabase mNewsDatabase;
    private final INewsRemoteApi mNewsRemoteApi;

    public DataManager(Context context) {
        mNewsDatabase = new NewsDatabase(context);
        mNewsRemoteApi = new NewsRemoteApi();
    }

    @Override
    public List<NewsEntity> getNewstList(boolean forceUpdate) throws IOException, JSONException {
        LoadNewsListStrategy strategy = new LoadNewsListStrategy();
        return strategy.getData(forceUpdate);
    }

    @Override
    public NewsDetailEntity getNewsDetail(String newsId, boolean forceUpdate) throws IOException, JSONException {
        LoadNewsDetailStrategy strategy = new LoadNewsDetailStrategy(newsId);
        return strategy.getData(forceUpdate);
    }

    private class LoadNewsListStrategy extends BaseLoadStrategy<List<NewsEntity>> {

        @Override
        protected List<NewsEntity> getFromDatabase() {
            return mNewsDatabase.getNewsList();
        }

        @Override
        protected List<NewsEntity> getFromNetwork() throws IOException, JSONException {
            return mNewsRemoteApi.loadNewsList();
        }

        @Override
        protected void updateDatabase(List<NewsEntity> data) {
            mNewsDatabase.updateNewsList(data);
        }

        @Override
        protected boolean isEmpty(List<NewsEntity> data) {
            return data.isEmpty();
        }
    }

    private class LoadNewsDetailStrategy extends BaseLoadStrategy<NewsDetailEntity> {

        private final String mId;

        private LoadNewsDetailStrategy(String mId) {
            this.mId = mId;
        }

        @Override
        protected NewsDetailEntity getFromDatabase() {
            return mNewsDatabase.getNewsDetailEntity(mId);
        }

        @Override
        protected NewsDetailEntity getFromNetwork() throws IOException, JSONException {
            return mNewsRemoteApi.loadNewsDetailEntity(mId);
        }

        @Override
        protected void updateDatabase(NewsDetailEntity data) {
            mNewsDatabase.updateNewsDetailEntity(data);
        }

        @Override
        protected boolean isEmpty(NewsDetailEntity data) {
            return data == null;
        }
    }

}
