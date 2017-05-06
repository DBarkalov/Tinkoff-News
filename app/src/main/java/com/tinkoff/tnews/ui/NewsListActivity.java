package com.tinkoff.tnews.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.tinkoff.tnews.NewsApp;
import com.tinkoff.tnews.R;
import com.tinkoff.tnews.model.NewsEntity;
import com.tinkoff.tnews.presenter.INewsListPresenter;

import java.util.ArrayList;
import java.util.List;

public class NewsListActivity extends AppCompatActivity implements INewsListView, SwipeRefreshLayout.OnRefreshListener {

    private final static String TAG = "NewsListActivity";
    private INewsListPresenter mPresenter;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView.LayoutManager mLayoutManager;
    private NewsAdapter mNewsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = ((NewsApp) getApplication()).getNewsPresenter();
        setContentView(R.layout.activity_news_list);

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mNewsAdapter = new NewsAdapter();
        mRecyclerView.setAdapter(mNewsAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.setView(this);
        mPresenter.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPresenter.setView(null);
    }

    @Override
    public void updateList(List<NewsEntity> list) {
        Log.d(TAG, "onRefresh size=" + list.size());
        mNewsAdapter.updateDataset(list);
    }

    @Override
    public void showProgress() {
        Log.d(TAG, "showProgress()");
        mSwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideProgress() {
        Log.d(TAG, "hideProgress()");
        if(mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void showError(String error) {
        Log.d(TAG, "showError " + error);
        Toast.makeText(this, error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRefresh() {
        mPresenter.onRefresh(true);
    }


    public static class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
        private List<NewsEntity> mDataset = new ArrayList<>();

        public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            private TextView mTextView;
            private TextView mDateView;
            private String mId;


            public ViewHolder(View v) {
                super(v);
                mTextView = (TextView) v.findViewById(R.id.text);
                mDateView = (TextView) v.findViewById(R.id.date);
                v.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), NewsDetailActivity.class);
                intent.putExtra(NewsDetailActivity.NEWS_ID, mId);
                v.getContext().startActivity(intent);
            }
        }

        public NewsAdapter() {
        }

        public void updateDataset(List<NewsEntity> list) {
            //todo merge data
            mDataset.clear();
            mDataset.addAll(list);
            notifyDataSetChanged();
        }

        @Override
        public NewsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_view, parent, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.mTextView.setText(mDataset.get(position).getText());
            holder.mDateView.setText(String.valueOf(mDataset.get(position).getDateString(holder.mDateView.getContext().getApplicationContext())));
            holder.mId = mDataset.get(position).getNewsId();
        }

        @Override
        public int getItemCount() {
            return mDataset.size();
        }
    }


}
