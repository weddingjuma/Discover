# Discover

An Android demo app that discovers nearby restaurants implemented with CLEAN architecture using DoorDash's API.

## Usage
* The app loads the nearest restaurants around DoorDash HQ automatically.
Clicking on the star button will mark it favorite.
* To refresh the list of restaurants and/or reorder the favorite restaurants to the top, open the navigation drawer and hit __Refresh__.
* Rotate the screen will automatically reorder the favorite restaurants to the top but not refetching the nearby restaurants.

## Unit Testing Codes
* takehome.doordash.discover.features.restaurants.RestaurantViewModelTest

## UI Testing Codes
* takehome.doordash.discover.EspressoRestaurantsListTest

## Third-Party Libraries
* Dependency Injection
  - [Dagger 2](https://github.com/google/dagger)
* Reactive programming
  - [RxJava 2](https://github.com/ReactiveX/RxJava)
* Networking
  - [OkHttp 3.9.0](https://github.com/square/okhttp)
  - [Retrofit 2.3.0](https://github.com/square/retrofit)
* MVVM
  - Android Architecture Components - [Lifecycle View Model 1.0.0](https://developer.android.com/topic/libraries/architecture/viewmodel.html)
* Storage
  - Android Architecture Components - [Room 1.0.0](https://developer.android.com/topic/libraries/architecture/room.html)
* Unit and UI Testing
  - [JUnit 4](https://github.com/junit-team/junit4)
  - [Espresso 3.0.1](https://developer.android.com/training/testing/espresso/index.html)
* API
  - [DoorDash](https://www.doordash.com/)
