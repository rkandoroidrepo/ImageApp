package com.example.imageapp.repo;


import com.example.imageapp.modal.Photo;
import com.example.imageapp.modal.PhotoReponse;
import com.example.imageapp.modal.SizeResponse;
import com.example.imageapp.utils.DataCallbackListener;
import com.example.imageapp.utils.ErrorCode;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Singleton
public class ImageRepository implements ImageDataSource {

    private APIServices apiServices;
    private List<Photo> photoList;

    @Inject
    ImageRepository(APIServices apiServices) {
        this.apiServices = apiServices;
    }

    private static int getErrorCode(Throwable t) {
        if (t instanceof IOException) {
            return ErrorCode.NETWORK_ERROR;
        } else {
            return ErrorCode.SERVER_ERROR;
        }
    }

    @Override
    public void getImages(final DataCallbackListener<List<Photo>> callbackListener) {
        if (photoList != null && !photoList.isEmpty()) {
            callbackListener.onSuccess(photoList);
        } else {
            String method = "flickr.people.getPublicPhotos";
            String apiKey = "227be805b3d6e934926d049533bb938a";
            String userId = "135487628%40N06";
            String format = "json";
            String jsoncallback = "1";
            apiServices.getPhotos(method, apiKey, userId, format,
                    jsoncallback).enqueue(new Callback<PhotoReponse>() {
                @Override
                public void onResponse(Call<PhotoReponse> call, Response<PhotoReponse> response) {
                    if (response.isSuccessful()) {
                        photoList = response.body().getPhotos().getPhoto();
                        callbackListener.onSuccess(photoList);
                    } else {
                        callbackListener.onError(ErrorCode.SERVER_ERROR);
                    }
                }

                @Override
                public void onFailure(Call<PhotoReponse> call, Throwable t) {
                    callbackListener.onError(getErrorCode(t));

                }
            });
        }
    }

    @Override
    public Flowable<SizeResponse> getSizes(String photoId) {
        String method = "flickr.photos.getSizes";
        String apiKey = "227be805b3d6e934926d049533bb938a";
        String userId = "135487628%40N06";
        String format = "json";
        String jsoncallback = "1";
        return apiServices.getSize2(method, photoId, apiKey,
                userId, format, jsoncallback)
                .delay(600, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread());

    }
}
