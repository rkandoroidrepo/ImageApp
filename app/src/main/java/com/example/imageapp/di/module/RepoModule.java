package com.example.imageapp.di.module;

import com.example.imageapp.repo.ImageDataSource;
import com.example.imageapp.repo.ImageRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RepoModule {

    @Singleton
    @Provides
    ImageDataSource provideImageRepository(ImageRepository imageRepository) {
        return imageRepository;
    }
}
