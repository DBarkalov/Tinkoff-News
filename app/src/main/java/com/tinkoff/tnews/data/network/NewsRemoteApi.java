package com.tinkoff.tnews.data.network;

import android.util.Log;

import com.tinkoff.tnews.model.NewsDetailEntity;
import com.tinkoff.tnews.model.NewsEntity;
import com.tinkoff.tnews.data.network.parser.IJsonParser;
import com.tinkoff.tnews.data.network.parser.NewsDetailParser;
import com.tinkoff.tnews.data.network.parser.NewsParser;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;


/**
 * Created by Dmitry Barkalov on 05.05.17.
 */

public class NewsRemoteApi implements INewsRemoteApi {

    private static final String TAG = "NewsRemoteApi";
    private final String NEWS_LIST_URL = "https://api.tinkoff.ru/v1/news";
    private final String NEWS_DETAIL_BASE_URL = "https://api.tinkoff.ru/v1/news_content?id=";

    @Override
    public List<NewsEntity> loadNewsList() throws IOException, JSONException {
        List<NewsEntity> list = execute(buildListRequest(), new NewsParser());
        Log.d(TAG, "loadNewsList() size=" + list.size());
        return list;
    }

    @Override
    public NewsDetailEntity loadNewsDetailEntity(String newsId) throws IOException, JSONException {
        return execute(buildDetailRequest(newsId), new NewsDetailParser());
    }

    private <T> T execute(NetworkGetRequest request, IJsonParser<T> parser) throws IOException, JSONException {
        return parser.parse(request.execute());
    }

    private NetworkGetRequest buildListRequest() {
        return new NetworkGetRequest(NEWS_LIST_URL);
    }

    private NetworkGetRequest buildDetailRequest(String newsId) {
        return new NetworkGetRequest(NEWS_DETAIL_BASE_URL + newsId);
    }

}
