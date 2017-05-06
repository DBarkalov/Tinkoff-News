package com.tinkoff.tnews.data;

import com.tinkoff.tnews.model.NewsDetailEntity;
import com.tinkoff.tnews.model.NewsEntity;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

/**
 * Created by Dmitry Barkalov on 05.05.17.
 */

public interface IDataManager {
    List<NewsEntity> getNewstList(boolean forceUpdate) throws IOException, JSONException;
    NewsDetailEntity getNewsDetail(String newsId, boolean forceUpdate) throws IOException, JSONException;
}
