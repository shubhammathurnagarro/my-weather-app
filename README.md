# My Weather

This Android application provides real-time weather information and a 5-day forecast using the OpenWeatherMap API. Developed in Kotlin, it adheres to the MVVM architectural pattern for optimal code organization and maintainability. The app utilizes LiveData to observe data changes and automatically update the UI, providing a real-time view of the weather information. The app is built using Retrofit for network requests, and Coroutines for asynchronous operations.


### Key Features

- **Current Weather:** Displays current temperature, weather condition, humidity, wind speed, and other relevant details.
- **5-Day Forecast:** Provides a forecast for the next 5 days, including temperature, weather condition, and precipitation.
- **City Search:** Allows users to search for weather information in different cities.


### Technologies Used

- **Android Studio:** IDE for Android app development.
- **Kotlin:** Programming language for Android app development.
- **MVVM (Model-View-ViewModel):** Architectural pattern for separating concerns in the app.
- **Retrofit:** A type-safe REST client for Android and Java.
- **Coroutines:** A concurrency library for writing asynchronous code in Kotlin.
- **OpenWeatherMap API:** Weather data provider.


## Steps to setup

1. **Clone the repository:** Open your terminal or command prompt and navigate to your desired project directory. Then, run the following command to clone this repository:
    ```bash
    get clone https://github.com/shubhammathurnagarro/my-weather-app
    ```
2. **Open in Android Studio:** Launch Android Studio and go to File > Open. Navigate to the folder containing the cloned project (`MyWeather`) and select it.Open the cloned project in Android Studio.
3. **Run the app:** Click the **Run** button (typically a green play icon) in the toolbar. Android Studio will build and deploy the app onto your chosen device or emulator.


## Usage

- **Home Screen:** The app will display the weather for the default location.
- **Search:** Use the search bar to find weather information for other cities.
- **Forecast:** Scroll down to view the 5-day forecast.


### Screenshots

App Screenshot 1 | App Screenshot 2
:-:|:-:
![App Screenshot 1](https://i.imgur.com/X14lyaU.png) | ![App Screenshot 1](https://i.imgur.com/bHvSTaV.png)


## License

This project is licensed under the [MIT](https://choosealicense.com/licenses/mit/)

