package com.example.imageapp.viewmodal;

import androidx.lifecycle.LiveData;

import com.example.imageapp.modal.Photo;

import java.util.List;
import java.util.Set;

public interface MainViewModal {
    LiveData<List<Photo>> getImages();

    LiveData<Boolean> getProgressStatus();

    LiveData<String> getError();

    void getSizes(Photo photo);

    void loadImages();

    Set<String> getRequest();
}
