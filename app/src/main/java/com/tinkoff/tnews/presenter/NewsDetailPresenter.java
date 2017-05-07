package com.tinkoff.tnews.presenter;

import android.content.Context;
import android.os.AsyncTask;

import com.tinkoff.tnews.data.DataManager;
import com.tinkoff.tnews.data.IDataManager;
import com.tinkoff.tnews.model.NewsDetailEntity;
import com.tinkoff.tnews.model.NewsDetailModel;
import com.tinkoff.tnews.ui.INewsDetailView;

/**
 * Created by Dmitry Barkalov on 06.05.17.
 */

public class NewsDetailPresenter implements INewsDetailPresenter {
    private final NewsDetailModel mModel;
    private INewsDetailView mView;
    private final Context mContext;

    public NewsDetailPresenter(Context context, NewsDetailModel model) {
        mContext = context;
        mModel = model;
        mModel.setListener(new NewsDetailModel.DataListener() {
            @Override
            public void onProgressUpdate(String id, boolean progress) {
                if (mView != null) {
                    if (progress) {
                        mView.showProgress(id);
                    } else {
                        mView.hideProgress(id);
                    }
                }
            }

            @Override
            public void onError(String id, Exception error) {
                if (mView != null) {
                    mView.showError(id, error);
                }
            }

            @Override
            public void onUpdate(String id, NewsDetailEntity entity) {
                if (mView != null) {
                    mView.updateText(id, entity);
                }
            }
        });
    }

    @Override
    public void setView(INewsDetailView view) {
        mView = view;
    }

    @Override
    public void onResume(String id) {
        if (mModel.inProgress(id)) {
            mView.showProgress(id);
        } else {
            mView.hideProgress(id);
        }
        refresh(id, false);
    }

    @Override
    public void onRefresh(String id) {
        refresh(id, true);
    }

    private void refresh(String id, boolean force) {
        if (!mModel.inProgress(id)) {
            if (force) {
                startLoadNews(id, force);
            } else {
                if (mModel.getDetail(id) != null) {
                    mView.updateText(id, mModel.getDetail(id));
                } else {
                    startLoadNews(id, force);
                }
            }
        }
    }

    private void startLoadNews(String id, boolean force) {
        mModel.setProgress(id, true);
        new LoadTask(mContext, force, id).execute();
    }

    private class LoadTask extends AsyncTask<Void, Void, DetailResult> {

        private final Context mContext;
        private final boolean mForce;
        private final String mId;

        private LoadTask(Context context, boolean force, String id) {
            this.mContext = context;
            this.mForce = force;
            this.mId = id;
        }

        @Override
        protected DetailResult doInBackground(Void... params) {
            IDataManager dataManager = new DataManager(mContext);
            DetailResult result = null;
            try {
                NewsDetailEntity entity = dataManager.getNewsDetail(mId, mForce);
                result = new DetailResult(mId, entity);
            } catch (Exception e) {
                result = new DetailResult(mId, e);
            }
            return result;
        }

        @Override
        protected void onPostExecute(DetailResult result) {
            onDetailLoaded(result);
        }
    }

    private void onDetailLoaded(DetailResult result) {
        mModel.setProgress(result.getmId(), false);
        if (result.isOK()) {
            mModel.put(result.getmId(), result.getData());
        } else {
            mModel.setError(result.getmId(), result.getError());
        }
    }

    private static class DetailResult extends Result<NewsDetailEntity> {
        private final String mId;

        public DetailResult(String id, NewsDetailEntity result) {
            super(result);
            this.mId = id;
        }

        public DetailResult(String id, Exception e) {
            super(e);
            this.mId = id;
        }

        public String getmId() {
            return mId;
        }
    }
}
