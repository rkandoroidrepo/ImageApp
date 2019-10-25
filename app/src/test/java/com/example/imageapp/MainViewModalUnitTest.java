package com.example.imageapp;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Observer;

import com.example.imageapp.modal.Photo;
import com.example.imageapp.repo.ImageRepository;
import com.example.imageapp.utils.DataCallbackListener;
import com.example.imageapp.utils.ErrorCode;
import com.example.imageapp.viewmodal.MainViewModal;
import com.example.imageapp.viewmodal.MainViewModalIml;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.schedulers.Schedulers;

@RunWith(JUnit4.class)
public class MainViewModalUnitTest {

    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();
    private MainViewModal mainViewModal;
    @Mock
    private ImageRepository imageRepository;
    @Mock
    private Observer<List<Photo>> imageListObserver;
    @Mock
    private Observer<Boolean> progressStatusObserver;
    @Mock
    private Observer<String> errorObserver;
    @Captor
    private ArgumentCaptor<DataCallbackListener<List<Photo>>> callbackCaptor;

    @BeforeClass
    public static void setupClass() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(__ -> Schedulers.trampoline());
    }

    @AfterClass
    public static void tearDownClass() {
        RxAndroidPlugins.reset();
    }

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mainViewModal = new MainViewModalIml(imageRepository);
        mainViewModal.getImages().observeForever(imageListObserver);
        mainViewModal.getProgressStatus().observeForever(progressStatusObserver);
        mainViewModal.getError().observeForever(errorObserver);
    }

    @Test
    public void testLoadImages() {
        mainViewModal.loadImages();
        Mockito.verify(imageRepository).getImages(callbackCaptor.capture());
        List<Photo> photoList = new ArrayList<>();
        callbackCaptor.getValue().onSuccess(photoList);
        Mockito.verify(imageListObserver).onChanged(photoList);
        Mockito.verify(progressStatusObserver).onChanged(false);
    }

    @Test
    public void testLoadImagesError() {
        mainViewModal.loadImages();
        Mockito.verify(imageRepository).getImages(callbackCaptor.capture());
        callbackCaptor.getValue().onError(ErrorCode.NETWORK_ERROR);
        Mockito.verify(progressStatusObserver).onChanged(false);
        Mockito.verify(errorObserver).onChanged("Network error");
    }
}
