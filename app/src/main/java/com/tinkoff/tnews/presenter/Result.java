package com.tinkoff.tnews.presenter;

/**
 * Created by Dmitry Barkalov on 06.05.17.
 */

public class Result<T> {
    private final Exception mException;
    private final T mData;

    public Result(Exception exception, T result) {
        this.mException = exception;
        this.mData = result;
    }

    public boolean isOK() {
        return (mException == null);
    }

    T getData(){
       return mData;
    }

    public Exception getError() {
        return mException;
    }
}
