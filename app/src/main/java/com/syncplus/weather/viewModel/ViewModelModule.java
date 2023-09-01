package com.syncplus.weather.viewModel;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.syncplus.weather.ViewModelKey;

import java.util.Map;

import javax.inject.Provider;
import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoMap;


@Module
 public abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(HomeScreenViewModel.class)
    abstract ViewModel provideHomeViewModule(HomeScreenViewModel homeScreenViewModel);

// @Provides
// @Singleton
// ViewModelProvider.Factory provideViewModelFactory(Map<Class<? extends ViewModel>, Provider<ViewModel>> viewModelProviders) {
//  return new ViewModelFactory(viewModelProviders);
// }
}
