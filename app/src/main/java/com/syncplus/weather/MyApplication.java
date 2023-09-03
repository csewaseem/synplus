package com.syncplus.weather;

import android.app.Application;

import com.syncplus.weather.di.component.ApplicationComponent;
import com.syncplus.weather.di.component.DaggerApplicationComponent;

public class MyApplication extends Application {

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        applicationComponent = DaggerApplicationComponent.create();
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}
