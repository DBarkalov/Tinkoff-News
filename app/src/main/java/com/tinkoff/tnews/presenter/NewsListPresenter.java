package com.tinkoff.tnews.presenter;

import android.content.Context;
import android.os.AsyncTask;

import com.tinkoff.tnews.data.DataManager;
import com.tinkoff.tnews.data.IDataManager;
import com.tinkoff.tnews.model.NewsModel;
import com.tinkoff.tnews.model.NewsEntity;
import com.tinkoff.tnews.ui.INewsListView;

import java.util.List;

/**
 * Created by Dmitry Barkalov on 06.05.17.
 */

public class NewsListPresenter implements INewsListPresenter {
    private final NewsModel mModel;
    private INewsListView mView;
    private final Context mContext;

    public NewsListPresenter(Context context, NewsModel model) {
        mContext = context;
        mModel = model;
        mModel.setListener(new NewsModel.DataListener() {
            @Override
            public void onProgressUpdate(boolean progress) {
                if (mView != null) {
                    if (progress) {
                        mView.showProgress();
                    } else {
                        mView.hideProgress();
                    }
                }
            }

            @Override
            public void onError(Exception error) {
                if (mView != null) {
                    mView.showError(error);
                }
            }

            @Override
            public void onListUpdate(List<NewsEntity> list) {
                if (mView != null) {
                    mView.updateList(list);
                }
            }
        });
    }

    @Override
    public void setView(INewsListView view) {
        mView = view;
    }

    @Override
    public void onResume() {
        if (mModel.inProgress()) {
            mView.showProgress();
        } else {
            mView.hideProgress();
        }
        refresh(false);
    }

    @Override
    public void onRefresh() {
        refresh(true);
    }

    private void refresh(boolean force) {
        if (!mModel.inProgress()) {
            if (force) {
                startLoadNews(force);
            } else {
                if (!mModel.getNewsEntities().isEmpty()) {
                    mView.updateList(mModel.getNewsEntities());
                } else {
                    startLoadNews(force);
                }
            }
        }
    }

    private void startLoadNews(boolean force) {
        mModel.setProgress(true);
        new LoadTask(mContext, force).execute();
    }


    private class LoadTask extends AsyncTask<Void, Void, NewsResult> {

        private final Context mContext;
        private final boolean mForce;

        private LoadTask(Context context, boolean force) {
            this.mContext = context;
            this.mForce = force;
        }

        @Override
        protected NewsResult doInBackground(Void... params) {
            IDataManager dataManager = new DataManager(mContext);
            NewsResult result = null;
            try {
                List<NewsEntity> list = dataManager.getNewstList(mForce);
                result = new NewsResult(list);
            } catch (Exception e) {
                result = new NewsResult(e);
            }
            return result;
        }

        @Override
        protected void onPostExecute(NewsResult result) {
            onNewsLoaded(result);
        }
    }

    private void onNewsLoaded(NewsResult result) {
        mModel.setProgress(false);
        if (result.isOK()) {
            mModel.setNewsEntities(result.getData());
        } else {
            mModel.setError(result.getError());
        }
    }

    private static class NewsResult extends Result<List<NewsEntity>> {

        public NewsResult(List<NewsEntity> data) {
            super(data);
        }

        public NewsResult(Exception e) {
            super(e);
        }
    }
}
