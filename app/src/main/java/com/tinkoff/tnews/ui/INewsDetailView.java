package com.tinkoff.tnews.ui;

import com.tinkoff.tnews.model.NewsDetailEntity;
import com.tinkoff.tnews.model.NewsEntity;

import java.util.List;

/**
 * Created by Dmitry Barkalov on 06.05.17.
 */

public interface INewsDetailView {
    void updateText(String id, NewsDetailEntity detailEntity);
    void showProgress(String id);
    void hideProgress(String id);
    void showError(String id, Exception error);
}
