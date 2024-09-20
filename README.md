# My Weather

This Android application provides real-time weather information and a 5-day forecast using the OpenWeatherMap API. Developed in Kotlin, it adheres to the MVVM architectural pattern for optimal code organization and maintainability. The app utilizes LiveData to observe data changes and automatically update the UI, providing a real-time view of the weather information. The app is built using Retrofit for network requests, and Coroutines for asynchronous operations.


### Key Features

- **Current Weather:** Displays current temperature, weather condition, humidity, wind speed, and other relevant details.
- **5-Day Forecast:** Provides a forecast for the next 5 days, including temperature, weather condition, and precipitation.
- **City Search:** Allows users to search for weather information in different cities.
- **Recent Searches:** History for quick access to previously checked locations.
- **Caching:** Caching of weather data for offline access and improved performance.


### Technologies Used

- **Android Studio:** IDE for Android app development.
- **Kotlin:** Programming language for Android app development.
- **MVVM (Model-View-ViewModel):** Architectural pattern for separating concerns in the app.
- **Retrofit:** A type-safe REST client for Android and Java.
- **Coroutines:** A concurrency library for writing asynchronous code in Kotlin.
- **OpenWeatherMap API:** Weather data provider.


## Steps to Setup

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
- **Recent Searches:** Access the list of recently searched locations and tap on any location to view its weather details.


### Screenshots

App Screenshot 1 | App Screenshot 2
:-:|:-:
![App Screenshot 1](https://i.imgur.com/X14lyaU.png) | ![App Screenshot 1](https://i.imgur.com/bHvSTaV.png)


## Design/Approach

#### MVVM Architecture:
This weather app adopts the Model-View-ViewModel (MVVM) architectural pattern, which promotes separation of concerns and improves code maintainability and testability.
- **Model:** Represents the data layer, handling data retrieval from the OpenWeatherMap API and data storage.
- **View:** Encapsulates the user interface components, such as activities and fragments, responsible for displaying data and handling user interactions.
- **ViewModel:** Acts as a bridge between the Model and View, managing data, handling UI updates, and exposing data to the View in an observable manner.

#### LiveData:
LiveData is a lifecycle-aware observable data holder class from the Android Architecture Components library. It ensures that data updates are delivered to the correct lifecycle state of the observer component (activity or fragment). This eliminates the need for manual lifecycle management and prevents memory leaks.

#### Key benefits of using LiveData:
- **Observes lifecycle:** LiveData automatically updates observers when the data changes and only when the observer is active.
- **Avoids null safety issues:** LiveData prevents null pointer exceptions by ensuring that data is only observed by active components.
- **Simplifies UI updates:** LiveData simplifies the process of updating UI elements based on data changes.

#### Implementation:
- **Model:** The Model layer uses Retrofit to make network requests to the OpenWeatherMap API and retrieves weather data.
- **ViewModel:** The ViewModel layer exposes LiveData objects to the View, representing the weather data and the recent searches history. When the data changes, the ViewModel updates the LiveData objects, triggering UI updates in the View.
- **View:** The View layer observes the LiveData objects exposed by the ViewModel and updates the UI elements accordingly. This ensures that the UI always reflects the latest weather data and the recent searches history.


## License

This project is licensed under the [MIT](https://choosealicense.com/licenses/mit/)

