package com.syncplus.weather.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class HomeScreenViewModel extends ViewModel {

    private final MutableLiveData<List<String>> mTexts;

    public HomeScreenViewModel() {
        mTexts = new MutableLiveData<>();
        List<String> texts = new ArrayList<>();
        for (int i = 1; i <= 16; i++) {
            texts.add("This is item # " + i);
        }
        mTexts.setValue(texts);
    }

    public LiveData<List<String>> getTexts() {
        return mTexts;
    }
}