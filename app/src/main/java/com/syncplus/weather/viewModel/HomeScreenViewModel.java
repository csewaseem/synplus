package com.syncplus.weather.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.syncplus.weather.model.CityWeather;
import com.syncplus.weather.repository.CityWeatherRepository;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class HomeScreenViewModel extends ViewModel {

    private CityWeatherRepository cityWeatherRepository;
    private final CompositeDisposable disposables = new CompositeDisposable();
    private final MutableLiveData<CityWeather> data = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loading = new MutableLiveData<>();

    @Inject
    public HomeScreenViewModel(CityWeatherRepository cityWeatherRepository) {
        this.cityWeatherRepository = cityWeatherRepository;
    }

    public LiveData<CityWeather> getWeather() {
        return data;
    }

    public LiveData<Boolean> isLoading() {
        return loading;
    }

    public void fetchCityWeather(String cityName) {
        loading.setValue(true);
        disposables.add(cityWeatherRepository.getCityWeather(cityName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        response -> {
                            if (response != null) {
                                data.setValue(response);
                            }
                            loading.setValue(false);
                        },
                        throwable -> {
                            // Handle error
                            loading.setValue(false);
                        }
                )
        );
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposables.clear();
    }
}