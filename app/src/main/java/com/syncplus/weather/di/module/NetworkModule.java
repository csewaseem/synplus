package com.syncplus.weather.di.module;

import com.syncplus.weather.service.CityWeatherService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


@Module(includes = ViewModelModule.class)
public class NetworkModule {

    private static String BASE_URL = "https://api.openweathermap.org/data/2.5/";

    @Singleton
    @Provides
    static Retrofit provideRetrofit() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)) // Enable logging
                .build();

        return new Retrofit.Builder().baseUrl(BASE_URL)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

    }

    @Singleton
    @Provides
    static CityWeatherService provideRetrofitService(Retrofit retrofit) {
        return retrofit.create(CityWeatherService.class);
    }
}
