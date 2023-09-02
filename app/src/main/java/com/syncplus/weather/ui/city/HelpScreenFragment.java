package com.syncplus.weather.ui.city;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.syncplus.weather.databinding.FragmentHelpBinding;

public class HelpScreenFragment extends Fragment {

    FragmentHelpBinding binding;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHelpBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

                // Enable JavaScript (if needed)
        WebSettings webSettings = binding.webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

// Define your HTML content
        String htmlContent = "<html><body>" +
                "<p>This application is designed to check the current weather of a city based on user input.</p><p>Home Screen</p><p> It allows users to add and remove locations (city names) in local storage. When a location (city name) is clicked, the app navigates to the City screen, where users can view current weather information, including forecasts, temperature, humidity, rainfall chances, and wind details.</p></body></html>";

// Load HTML content
        binding.webView.loadData(htmlContent, "text/html", "UTF-8");

// Or, you can load with a base URL (useful for relative URLs, styles, and images)
        binding.webView.loadDataWithBaseURL(null, htmlContent, "text/html", "UTF-8", null);
        return root;
    }
}
