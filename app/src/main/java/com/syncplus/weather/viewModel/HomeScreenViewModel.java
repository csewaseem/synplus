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

    @Inject
    CityWeatherRepository cityWeatherRepository;
        private final CompositeDisposable disposables = new CompositeDisposable();

        private final MutableLiveData<CityWeather> data = new MutableLiveData<>();
        private final MutableLiveData<Boolean> loading = new MutableLiveData<>();

//    public HomeScreenViewModel(CityWeatherRepository cityWeatherRepository) {
//        this.cityWeatherRepository = cityWeatherRepository;
//    }

    //    public MutableLiveData<List<String>> getmTexts() {
//        return mTexts;
//    }

//    @Inject
    /*public HomeScreenViewModel(MutableLiveData<List<String>> mTexts, CityWeatherRepository cityWeatherRepository) {
        this.mTexts = mTexts;
        this.cityWeatherRepository = cityWeatherRepository;
    }*/

    /*@Inject
        public HomeScreenViewModel(MutableLiveData<List<String>> mTexts, CityWeatherRepository cityWeatherRepository) {
            this.mTexts = mTexts;
            this.cityWeatherRepository = cityWeatherRepository;
        }*/
//        @Inject
        /*public HomeScreenViewModel(CityWeatherRepository cityWeatherRepository) {
            mTexts = new MutableLiveData<>();
            this.cityWeatherRepository = cityWeatherRepository;
        }*/
        public LiveData<CityWeather> getCityWeather() {
            return data;
        }

        public LiveData<Boolean> isLoading() {
            return loading;
        }

        public void fetchCityWeather() {
            loading.setValue(true);
            disposables.add(cityWeatherRepository.getCityWeather("")
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    response -> {
                                        data.setValue(response);
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