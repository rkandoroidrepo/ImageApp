package com.example.imageapp.view;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.imageapp.R;
import com.example.imageapp.modal.Photo;
import com.example.imageapp.viewmodal.MainViewModal;
import com.example.imageapp.viewmodal.MainViewModalIml;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class MainActivity extends DaggerAppCompatActivity implements
        ImageAdapter.AddSubRequest, MainView {

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private ImageAdapter imageAdapter;
    private MainViewModal mainViewModal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainViewModal = ViewModelProviders.of(this, viewModelFactory).get(MainViewModalIml.class);
        if (mainViewModal != null) {
            loadImages();
            iniProgress();
            iniError();
        }
    }

    @Override
    public void loadImages() {
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(imageAdapter);
        imageAdapter = new ImageAdapter(this);
        recyclerView.setLayoutManager(layoutManager);
        imageAdapter.setHasStableIds(true);
        recyclerView.setAdapter(imageAdapter);
        mainViewModal.loadImages();
        mainViewModal.getImages().observe(this, photos -> {
            imageAdapter.setRequestData(mainViewModal.getRequest());
            imageAdapter.setData(photos);
        });

    }

    @Override
    public void iniProgress() {
        final ProgressBar progressBar = findViewById(R.id.progressBar);
        mainViewModal.getProgressStatus().observe(this, isActive ->
                progressBar.setVisibility(isActive ? View.VISIBLE : View.GONE));

    }

    @Override
    public void iniError() {
        mainViewModal.getError().observe(this, errorMessage ->
                Toast.makeText(MainActivity.this, errorMessage, Toast.LENGTH_LONG).show());

    }

    @Override
    public void addRequest(Photo photo) {
        mainViewModal.getSizes(photo);

    }
}
