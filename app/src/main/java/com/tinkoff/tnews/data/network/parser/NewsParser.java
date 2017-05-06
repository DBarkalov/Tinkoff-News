package com.tinkoff.tnews.data.network.parser;

import com.tinkoff.tnews.model.NewsEntity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dmitry Barkalov on 05.05.17.
 */

public class NewsParser implements IJsonParser<List<NewsEntity>> {
    @Override
    public List<NewsEntity> parse(String str) throws JSONException {
        List<NewsEntity> list = new ArrayList<>();
        JSONObject jsonObject = new JSONObject(str);
        String result = jsonObject.getString("resultCode");
        if (!"OK".equals(result)) {
            throw new JSONException("result not OK, result = " + result);
        }
        JSONArray array = jsonObject.getJSONArray("payload");
        for (int i = 0; i < array.length(); i++) {
            JSONObject object = array.getJSONObject(i);
            final String id = object.getString("id");
            final String name = object.getString("name");
            //final String text = object.getString("text");
            final String text = android.text.Html.fromHtml(object.getString("text")).toString();
            JSONObject publicationDate = object.getJSONObject("publicationDate");
            final long date = publicationDate.getLong("milliseconds");
            list.add(new NewsEntity(id, name, text, date));
        }
        return list;
    }
}
