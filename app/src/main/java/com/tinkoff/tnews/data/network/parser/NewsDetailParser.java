package com.tinkoff.tnews.data.network.parser;

import com.tinkoff.tnews.model.NewsDetailEntity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Dmitry Barkalov on 05.05.17.
 */

public class NewsDetailParser implements IJsonParser<NewsDetailEntity> {
    @Override
    public NewsDetailEntity parse(String str) throws JSONException {
        JSONObject jsonObject = new JSONObject(str);
        String result = jsonObject.getString("resultCode");
        if (!"OK".equals(result)) {
            throw new JSONException("result not OK, result = " + result);
        }
        JSONObject object = jsonObject.getJSONObject("payload");
        JSONObject titleObject = object.getJSONObject("title");
        String id = titleObject.getString("id");
        String title = titleObject.getString("text");
        String content = object.getString("content");
        return new NewsDetailEntity(id, title, content);
    }
}
