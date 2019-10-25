package com.example.imageapp.di.builder;

import com.example.imageapp.di.ActivityScoped;
import com.example.imageapp.view.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public interface ActivityBuildersModule {
    @ActivityScoped
    @ContributesAndroidInjector()
    MainActivity contributeMainActivity();
}
