package com.syncplus.weather.ui.city;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.syncplus.weather.databinding.FragmentCityBinding;
import com.syncplus.weather.model.CityWeather;
import com.syncplus.weather.model.Main;
import com.syncplus.weather.model.Weather;

import java.util.ArrayList;

public class CityScreenFragment extends Fragment {

    private FragmentCityBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState){
        binding = FragmentCityBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        CityWeather weatherArgs = CityScreenFragmentArgs.fromBundle(getArguments()).getWeatherArg();

        binding.cityNameText.setText(weatherArgs.name);
        Main main = weatherArgs.main;
        binding.temperatureText.setText(String.valueOf(main.temp));
        ArrayList<Weather> weather = weatherArgs.weather;
        binding.weatherDescriptionText.setText(weather.get(0).description);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
