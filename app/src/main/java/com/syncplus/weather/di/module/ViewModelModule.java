package com.syncplus.weather.di.module;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.syncplus.weather.ViewModelKey;
import com.syncplus.weather.viewModel.HomeScreenViewModel;
import com.syncplus.weather.viewModel.ViewModelFactory;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(HomeScreenViewModel.class)
    abstract ViewModel provideHomeViewModule(HomeScreenViewModel homeScreenViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory factory);
}
