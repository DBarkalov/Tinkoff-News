package com.tinkoff.tnews.presenter;

/**
 * Created by Dmitry Barkalov on 06.05.17.
 */

public class Result<T> {
    private final Exception mException;
    private final T mData;

    public Result(T data){
        this(null, data);
    }

    public Result(Exception e){
        this(e, null);
    }
    private Result(Exception exception, T data) {
        this.mException = exception;
        this.mData = data;
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
