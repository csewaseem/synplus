package com.syncplus.weather.di.component;


import com.syncplus.weather.HomeScreenActivity;
import com.syncplus.weather.di.module.ContextModule;
import com.syncplus.weather.di.module.NetworkModule;
import com.syncplus.weather.ui.home.HomeScreenFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {NetworkModule.class, ContextModule.class})
public interface ApplicationComponent {

    void inject(HomeScreenActivity homeScreenActivity);
    void inject(HomeScreenFragment homeScreenFragment);
//    void inject(HomeScreenViewModel homeScreenViewModel);
//void inject(CityWeatherRepository cityWeatherRepository);
//    void inject(CityWeatherService cityWeatherService);

//    void inject(CityWeatherRepository cityWeatherRepository);
//    ViewModelFactory viewModelFactory();
//    CityWeatherRepository cityWeatherRepository();

}
