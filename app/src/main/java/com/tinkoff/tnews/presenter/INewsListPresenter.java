package com.tinkoff.tnews.presenter;

import com.tinkoff.tnews.ui.INewsListView;

/**
 * Created by dima on 06.05.17.
 */

public interface INewsListPresenter {
    void setView(INewsListView view);
    void onResume();
    void onRefresh();
}
