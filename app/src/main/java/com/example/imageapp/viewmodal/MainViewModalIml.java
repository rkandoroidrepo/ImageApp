package com.example.imageapp.viewmodal;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.imageapp.modal.Photo;
import com.example.imageapp.repo.ImageRepository;
import com.example.imageapp.utils.DataCallbackListener;
import com.example.imageapp.utils.ErrorCode;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class MainViewModalIml extends ViewModel implements MainViewModal {

    private ImageRepository imageRepository;
    private MutableLiveData<List<Photo>> photos = new MutableLiveData<>();
    private MutableLiveData<Boolean> progressStatus = new MutableLiveData<>();
    private MutableLiveData<String> error = new MutableLiveData<>();
    private Set<String> sizeRequest = new HashSet<>();
    private CompositeDisposable compositeDisposable;

    @Inject
    public MainViewModalIml(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
        this.compositeDisposable = new CompositeDisposable();
    }

    @Override
    public Set<String> getRequest() {
        return sizeRequest;
    }

    @Override
    public LiveData<List<Photo>> getImages() {
        return photos;
    }

    @Override
    public LiveData<Boolean> getProgressStatus() {
        return progressStatus;
    }

    @Override
    public LiveData<String> getError() {
        return error;
    }

    @Override
    public void loadImages() {
        progressStatus.setValue(true);
        imageRepository.getImages(new DataCallbackListener<List<Photo>>() {
            @Override
            public void onSuccess(List<Photo> response) {
                photos.setValue(response);
                progressStatus.setValue(false);
            }

            @Override
            public void onError(int errorCode) {
                error.setValue(errorCode == ErrorCode.NETWORK_ERROR ? "Network error" : "Server error");
                progressStatus.setValue(false);
            }
        });
    }

    @Override
    public void getSizes(Photo photo) {
        if (!sizeRequest.contains(photo.getId())) {
            sizeRequest.add(photo.getId());
            Disposable disposable = imageRepository.getSizes(photo.getId()).subscribe(sizeResponse -> {
                photo.setSizes(sizeResponse.getSizes());
                photos.setValue(photos.getValue());
            }, throwable -> error.setValue(throwable.getMessage()));
            compositeDisposable.add(disposable);
        }
    }


    @Override
    protected void onCleared() {
        super.onCleared();
        if (compositeDisposable != null) {
            compositeDisposable.clear();
            compositeDisposable = null;
        }
    }
}
