package com.tinkoff.tnews.ui;

import com.tinkoff.tnews.model.NewsEntity;

import java.util.List;

/**
 * Created by Dmitry Barkalov on 06.05.17.
 */

public interface INewsListView {
    void updateList(List<NewsEntity> list);
    void showProgress();
    void hideProgress();
    void showError(String error);
}
