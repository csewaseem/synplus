Weather Android application 
Implemented in Java with MVVM architecture, RxJava, Daggar2, Retrofit2, Jetpack libraries:( Navigation, Databinding, Lifecycles) & Mockito.

Home Screen:

Add and Remove Locations (City Names):

Create a UI that allows users to input city names and add them to a list.
Implement functionality to remove city names from the list.
Store the list of city names in local storage using SharedPreferences.
Display List of Saved Locations:

Retrieve the list of saved city names from SharedPreferences.
Display the list of locations on the Home Screen.

City Screen:

Weather Information:
Create a separate screen (Activity or Fragment) for displaying weather information.
Use a weather API (e.g., OpenWeatherMap) to fetch weather data based on the selected city.
Display the current weather information such as temperature, humidity, rain chances, and wind details on this screen.

Help Screen:

User Guidance:
Create a Help screen that provides guidance and instructions on how to use the application.
Explain how to add and remove locations, navigate to the City screen, and any other key features.
Implementation Details:

For adding and removing city names on the Home Screen, you can use a RecyclerView to display the list and provide buttons or actions to add and remove items.

To store the list of city names in SharedPreferences, you can convert the list to a JSON string and save it as a string preference. When retrieving, parse the JSON string back to a list.

For the City Screen, you'll need to make API requests to a weather service. Retrofit can be used to make API calls, and RxJava can be used for handling asynchronous operations.

You may want to consider using a ViewModel to manage the data and business logic for both the Home and City screens, and LiveData for observing changes in data.

To navigate between screens, you can use the Android Navigation Component, which simplifies navigation and handles the back stack for you.

The Help screen can be a simple layout with explanatory text and images.

Remember to handle error cases gracefully, such as when the weather data cannot be fetched, the user enters an invalid city name, or there is no saved location in SharedPreferences.

This is a high-level overview, and the actual implementation details may vary based on your project's architecture and design preferences.


![Home_first_screen](https://github.com/csewaseem/synplus/assets/7565969/86ba949d-83aa-4f03-972b-45ba6b052a69) ![Home_screen](https://github.com/csewaseem/synplus/assets/7565969/58c6ea13-5ae0-4cef-93a2-d2058a531f50)

![city_screen](https://github.com/csewaseem/synplus/assets/7565969/0e52a164-0946-4a17-810c-24bdd1d07170)![help_screen](https://github.com/csewaseem/synplus/assets/7565969/4537e1be-a864-439a-940f-29371d1c24e2)
