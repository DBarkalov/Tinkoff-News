package com.tinkoff.tnews.presenter;

import com.tinkoff.tnews.ui.INewsDetailView;

/**
 * Created by dima on 06.05.17.
 */

public interface INewsDetailPresenter {
    void setView(INewsDetailView view);
    void onResume(String id);
    void onRefresh(String id);
}
