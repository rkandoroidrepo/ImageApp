package com.example.imageapp.utils;

public interface DataCallbackListener<T> {

    void onSuccess(T response);

    void onError(int errorCode);
}
