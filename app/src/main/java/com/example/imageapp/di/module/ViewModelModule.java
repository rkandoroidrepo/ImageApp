package com.example.imageapp.di.module;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.imageapp.viewmodal.MainViewModalIml;
import com.example.imageapp.viewmodal.MyViewModelFactory;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModalIml.class)
    @SuppressWarnings("unused")
    abstract ViewModel bindMainViewModal(MainViewModalIml articleListViewModel);

    @Binds
    @SuppressWarnings("unused")
    abstract ViewModelProvider.Factory bindsViewModelFactory(MyViewModelFactory viewModelFactory);

}