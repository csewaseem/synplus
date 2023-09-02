package com.syncplus.weather.ui.city;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.syncplus.weather.R;
import com.syncplus.weather.databinding.FragmentCityBinding;
import com.syncplus.weather.model.CityWeather;
import com.syncplus.weather.model.Main;
import com.syncplus.weather.model.Weather;

import java.util.ArrayList;

public class CityScreenFragment extends Fragment {

    private FragmentCityBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCityBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        CityWeather weatherArgs = CityScreenFragmentArgs.fromBundle(getArguments()).getWeatherArg();
        if (weatherArgs != null) {
            ArrayList<Weather> weather = weatherArgs.weather;
            switch (weather.get(0).main) {
                case "Clouds":
                    binding.weatherIcon.setImageResource(R.drawable.cloudy_sun);
                    break;
                case "Sun":
                    binding.weatherIcon.setImageResource(R.drawable.sun);
                    break;
                case "Rain":
                    binding.weatherIcon.setImageResource(R.drawable.raining);
                    break;
                case "Haze":
                    binding.weatherIcon.setImageResource(R.drawable.sun);
                    break;
                default:
                    binding.weatherIcon.setImageResource(R.drawable.clouds_and_sun);
                    break;
            }
            binding.CityName.setText(weatherArgs.name);
            Main main = weatherArgs.main;
            binding.temperatureText.setText(main.temp + "°C");
            binding.highTempature.setText("High "+main.temp_max + "° | Low " + main.temp_min+"°");
            binding.humidity.setText("Humidity : "+String.valueOf(main.humidity));
            binding.weatherDescriptionText.setText(weather.get(0).description);

        }
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
