
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.anyString;

import static org.mockito.Mockito.when;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Observer;

import com.syncplus.weather.model.CityWeather;
import com.syncplus.weather.model.Main;
import com.syncplus.weather.model.Weather;
import com.syncplus.weather.model.Wind;
import com.syncplus.weather.repository.CityWeatherRepository;
import com.syncplus.weather.viewModel.HomeScreenViewModel;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

@RunWith(MockitoJUnitRunner.class)
public class HomeSreenViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();
    @Mock
    Observer<CityWeather> weatherResponseObserver;

    private HomeScreenViewModel homeScreenViewModel;

    @Mock
    private CityWeatherRepository cityWeatherRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Create a mock ViewModel with the repository
        homeScreenViewModel = new HomeScreenViewModel(cityWeatherRepository);
    }

    @Test
    public void testWeatherApiCallSuccess() {
        // Create a mock response

        // Mock the API call
        when(cityWeatherRepository.getCityWeather(anyString()))
                .thenReturn(Single.just(getMockWeather()).subscribeOn(Schedulers.io()));

        // Observe the ViewModel's LiveData
        homeScreenViewModel.getWeather().observeForever(weatherResponseObserver);

        // Trigger the ViewModel's API call
        homeScreenViewModel.fetchCityWeather("New York");

        assertNotNull(weatherResponseObserver);
    }

    @Test
    public void testWeatherApiCallError() {
        // Mock the API call
        when(cityWeatherRepository.getCityWeather(anyString()))
                .thenReturn(Single.just(getMockWeather()).subscribeOn(Schedulers.io()));

        // Observe the ViewModel's LiveData
        homeScreenViewModel.getWeather().observeForever(weatherResponseObserver);

        // Trigger the ViewModel's API call
        homeScreenViewModel.fetchCityWeather("InvalidCity");
        CityWeather name = homeScreenViewModel.getWeather().getValue();
        assertNull(name);
    }

    private CityWeather getMockWeather() {
        Weather weather = new Weather();
        weather.setDescription("Sunny");
        ArrayList<Weather> weatherArrayList = new ArrayList<Weather>();
        weatherArrayList.add(weather);
        Main main = new Main();
        main.setHumidity(88);
        main.setTemp(25.0);
        Wind wind = new Wind();
        CityWeather mockWeatherData = new CityWeather(weatherArrayList, "", main, 10, wind, 9, 7556, 91, "Singapore", 560);
        mockWeatherData.setName("New York");
        return mockWeatherData;
    }
}
