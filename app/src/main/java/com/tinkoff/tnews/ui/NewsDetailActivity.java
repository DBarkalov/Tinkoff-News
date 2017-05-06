package com.tinkoff.tnews.ui;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.widget.Toast;

import com.tinkoff.tnews.NewsApp;
import com.tinkoff.tnews.R;
import com.tinkoff.tnews.model.NewsDetailEntity;
import com.tinkoff.tnews.presenter.INewsDetailPresenter;
import com.tinkoff.tnews.presenter.NewsDetailPresenter;

public class NewsDetailActivity extends AppCompatActivity implements INewsDetailView, SwipeRefreshLayout.OnRefreshListener {

    public static final String NEWS_ID = "NEWS_ID";
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private INewsDetailPresenter mPresenter;
    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = ((NewsApp) getApplication()).getDetailPresenter();
        setContentView(R.layout.activity_news_detail);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mWebView = (WebView) findViewById(R.id.webview);
    }

    private String getID() {
        return getIntent().getExtras().getString(NEWS_ID);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.setView(this);
        mPresenter.onResume(getID());
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPresenter.setView(null);
    }

    @Override
    public void updateText(String id, NewsDetailEntity text) {
        if (id.equals(getID())) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("<br><b>");
            stringBuilder.append(text.getTitle());
            stringBuilder.append("</b><br>");
            stringBuilder.append(text.getContent());
            mWebView.loadData(stringBuilder.toString(), "text/html; charset=utf-8", "utf-8");
        }
    }

    @Override
    public void showProgress(String id) {
        if (id.equals(getID())) {
            mSwipeRefreshLayout.setRefreshing(true);
        }
    }

    @Override
    public void hideProgress(String id) {
        if (id.equals(getID())) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void showError(String id, String error) {
        if (id.equals(getID())) {
            Toast.makeText(this, error, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onRefresh() {
        mPresenter.onRefresh(getID(), true);
    }
}
