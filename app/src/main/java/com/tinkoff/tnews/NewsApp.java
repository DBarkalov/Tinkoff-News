package com.tinkoff.tnews;

import android.app.Application;

import com.tinkoff.tnews.presenter.INewsDetailPresenter;
import com.tinkoff.tnews.presenter.INewsListPresenter;
import com.tinkoff.tnews.presenter.NewsDetailPresenter;
import com.tinkoff.tnews.presenter.NewsListPresenter;
import com.tinkoff.tnews.ui.INewsDetailView;

/**
 * Created by Dmitry Barkalov on 06.05.17.
 */

public class NewsApp extends Application {
    private INewsListPresenter mNewsPresenter;
    private INewsDetailPresenter mNewsDetailPresenter;

    public INewsListPresenter getNewsPresenter() {
        if (mNewsPresenter == null) {
            mNewsPresenter = new NewsListPresenter(this);
        }
        return mNewsPresenter;
    }

    public INewsDetailPresenter getDetailPresenter() {
        if (mNewsDetailPresenter == null) {
            mNewsDetailPresenter = new NewsDetailPresenter(this);
        }
        return mNewsDetailPresenter;
    }
}
