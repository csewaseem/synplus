package com.syncplus.weather.service;

import com.syncplus.weather.model.CityWeather;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CityWeatherService {

    @GET("weather")
    Single<CityWeather> getCityWeather(@Query("q") String location, @Query("units") String units, @Query("appid") String apiKey);

}
