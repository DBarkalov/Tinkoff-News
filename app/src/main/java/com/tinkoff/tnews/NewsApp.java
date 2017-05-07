package com.tinkoff.tnews;

import android.app.Application;

import com.tinkoff.tnews.data.DataManager;
import com.tinkoff.tnews.data.IDataManager;
import com.tinkoff.tnews.model.NewsDetailModel;
import com.tinkoff.tnews.model.NewsModel;
import com.tinkoff.tnews.presenter.INewsDetailPresenter;
import com.tinkoff.tnews.presenter.INewsListPresenter;
import com.tinkoff.tnews.presenter.NewsDetailPresenter;
import com.tinkoff.tnews.presenter.NewsListPresenter;

/**
 * Created by Dmitry Barkalov on 06.05.17.
 */

public class NewsApp extends Application {
    private INewsListPresenter mNewsPresenter;
    private INewsDetailPresenter mNewsDetailPresenter;
    private IDataManager mDataManager;

    public INewsListPresenter getNewsPresenter() {
        if (mNewsPresenter == null) {
            mNewsPresenter = new NewsListPresenter(new NewsModel(), getDataManager());
        }
        return mNewsPresenter;
    }

    public INewsDetailPresenter getDetailPresenter() {
        if (mNewsDetailPresenter == null) {
            mNewsDetailPresenter = new NewsDetailPresenter(new NewsDetailModel(), getDataManager());
        }
        return mNewsDetailPresenter;
    }

    private IDataManager getDataManager() {
        if (mDataManager == null) {
            mDataManager = new DataManager(this);
        }
        return mDataManager;
    }
}
