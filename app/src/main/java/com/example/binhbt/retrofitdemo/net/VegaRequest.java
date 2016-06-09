package com.example.binhbt.retrofitdemo.net;

import retrofit.Callback;

/**
 * Created by binhbt on 6/8/2016.
 */
public abstract class VegaRequest <T> implements Callback<T> {
    public VegaRequest(){
        onStart();
    }
    public abstract void onStart();
}
