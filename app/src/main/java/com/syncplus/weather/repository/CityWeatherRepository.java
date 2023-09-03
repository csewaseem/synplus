package com.syncplus.weather.repository;

import com.syncplus.weather.model.CityWeather;
import com.syncplus.weather.service.CityWeatherService;
import com.syncplus.weather.util.Constant;

import javax.inject.Inject;

import io.reactivex.Single;

public class CityWeatherRepository {

    private CityWeatherService cityWeatherService;

    @Inject
    public CityWeatherRepository(CityWeatherService cityWeatherService) {
        this.cityWeatherService = cityWeatherService;
    }

    public Single<CityWeather> getCityWeather(String cityName) {
        return cityWeatherService.getCityWeather(cityName, Constant.METRIC, Constant.APPID);
    }
}
