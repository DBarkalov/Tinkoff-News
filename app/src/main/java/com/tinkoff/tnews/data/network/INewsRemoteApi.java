package com.tinkoff.tnews.data.network;

import com.tinkoff.tnews.model.NewsDetailEntity;
import com.tinkoff.tnews.model.NewsEntity;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

/**
 * Created by Dmitry Barkalov on 06.05.17.
 */

public interface INewsRemoteApi {
    List<NewsEntity> loadNewsList() throws IOException, JSONException;
    NewsDetailEntity loadNewsDetailEntity(String newsId) throws IOException, JSONException;
}
