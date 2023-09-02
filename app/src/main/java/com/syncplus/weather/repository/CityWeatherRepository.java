package com.syncplus.weather.repository;

import com.syncplus.weather.Constant;
import com.syncplus.weather.model.CityWeather;
import com.syncplus.weather.service.CityWeatherService;

import javax.inject.Inject;

import io.reactivex.Single;

public class CityWeatherRepository {

    private CityWeatherService cityWeatherService;

    @Inject
    public CityWeatherRepository(CityWeatherService cityWeatherService) {
        this.cityWeatherService = cityWeatherService;
    }

    public Single<CityWeather> getCityWeather(String cityName) {
        return cityWeatherService.getCityWeather(cityName,"metric", "f38ad5c60ecc7cf92ea655f394c4cf3d");
    }
}
