package com.tinkoff.tnews.data.network.parser;

import org.json.JSONException;

/**
 * Created by Dmitry Barkalov on 05.05.17.
 */

public interface IJsonParser<T> {
    T parse (String str) throws JSONException;
}
