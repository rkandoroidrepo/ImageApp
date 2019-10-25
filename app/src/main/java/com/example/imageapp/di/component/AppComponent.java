package com.example.imageapp.di.component;

import com.example.imageapp.BaseApplication;
import com.example.imageapp.di.builder.ActivityBuildersModule;
import com.example.imageapp.di.module.NetworkingModule;
import com.example.imageapp.di.module.RepoModule;
import com.example.imageapp.di.module.ViewModelModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {
        AndroidSupportInjectionModule.class,
        ActivityBuildersModule.class,
        NetworkingModule.class,
        RepoModule.class,
        ViewModelModule.class})
public interface AppComponent extends AndroidInjector<BaseApplication> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(BaseApplication application);

        AppComponent build();
    }
}
