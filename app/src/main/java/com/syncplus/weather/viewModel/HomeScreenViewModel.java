package com.syncplus.weather.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.syncplus.weather.model.CityWeather;
import com.syncplus.weather.repository.CityWeatherRepository;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;


public class HomeScreenViewModel extends ViewModel {

//    CityWeatherService cityWeatherService;
   private CityWeatherRepository cityWeatherRepository;

        private final CompositeDisposable disposables = new CompositeDisposable();

        private final MutableLiveData<CityWeather> data = new MutableLiveData<>();
        private final MutableLiveData<Boolean> loading = new MutableLiveData<>();
    private final MutableLiveData<ArrayList<String>> location = new MutableLiveData<>();

    public MutableLiveData<ArrayList<String>> getLocation() {
        return location;
    }
    @Inject
    public HomeScreenViewModel(CityWeatherRepository cityWeatherRepository) {
        this.cityWeatherRepository = cityWeatherRepository;
    }

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
                            .doOnSubscribe(disposable -> {
                        // This is called when the subscription is made
                        // You can show loading indicators here
                                System.out.println("disposable: " + disposable);

                            })
//                            .Next(response -> {
//                                // This is called when a successful response is received
//                                // You can access the response data here
//                                Log.d( "Response received: " + response.toString());
//                            })
                            .doOnError(error -> {
                                // This is called when an error occurs
                                // You can handle the error here
                                System.out.println("Error: " + error.getMessage());
                            })
                            .subscribe(
                                    response -> {
                                        System.out.println("subscribe: " + response);
                                        data.setValue(response);
                                        loading.setValue(false);
                                    },
                                    throwable -> {
                                        System.out.println("throwable: " + throwable);

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