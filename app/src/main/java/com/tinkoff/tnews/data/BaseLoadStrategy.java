package com.tinkoff.tnews.data;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

/**
 * Created by Dmitry Barkalov on 06.05.17.
 */
abstract class BaseLoadStrategy<T> {

    public T getData(boolean forceUpdate) throws IOException, JSONException {
        T data = null;
        if (!forceUpdate) {
            data = getFromDatabase();
            forceUpdate = isEmpty(data);
        }
        if (forceUpdate && refresh()) {
            data = getFromDatabase();
        }
        return data;
    }

    private boolean refresh() throws IOException, JSONException {
        T data = getFromNetwork();
        boolean result = !isEmpty(data);
        if (result) {
            updateDatabase(data);
        }
        return result;
    }

    protected abstract T getFromDatabase();

    protected abstract T getFromNetwork() throws IOException, JSONException;

    protected abstract void updateDatabase(T data);

    protected abstract boolean isEmpty(T data);
}
