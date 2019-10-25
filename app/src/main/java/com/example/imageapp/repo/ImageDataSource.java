package com.example.imageapp.repo;

import com.example.imageapp.modal.Photo;
import com.example.imageapp.modal.SizeResponse;
import com.example.imageapp.utils.DataCallbackListener;

import java.util.List;

import io.reactivex.Flowable;

public interface ImageDataSource {

    void getImages(final DataCallbackListener<List<Photo>> callbackListener);

    Flowable<SizeResponse> getSizes(String photoId);
}
