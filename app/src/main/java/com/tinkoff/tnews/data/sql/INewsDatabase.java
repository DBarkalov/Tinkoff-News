package com.tinkoff.tnews.data.sql;

import com.tinkoff.tnews.model.NewsDetailEntity;
import com.tinkoff.tnews.model.NewsEntity;

import java.util.List;

/**
 * Created by Dmitry Barkalov on 06.05.17.
 */

public interface INewsDatabase {
    List<NewsEntity> getNewsList();
    void updateNewsList(List<NewsEntity> list);
    NewsDetailEntity getNewsDetailEntity(String newsId);
    void updateNewsDetailEntity(NewsDetailEntity entity);
}
