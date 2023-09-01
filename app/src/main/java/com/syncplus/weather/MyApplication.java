package com.syncplus.weather;

import android.app.Application;

import com.syncplus.weather.di.component.ApplicationComponent;
import com.syncplus.weather.di.component.DaggerApplicationComponent;

public class MyApplication extends Application {

    ApplicationComponent applicationComponent = DaggerApplicationComponent.create();
}
