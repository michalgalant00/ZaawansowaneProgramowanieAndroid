package com.example.masterand.navigation

sealed class Screen(val route: String) {

    object LoginScreen: Screen(route = "login-screen")

    object ProfileScreen: Screen(route = "profile-screen/{email}/{colorsNumber}") {
        fun createRoute(email: String, colorsNumber: Int) = "profile-screen/$email/$colorsNumber"
    }

    object GameScreen: Screen(route = "game-screen/{email}/{colorsNumber}") {
        fun createRoute(email: String, colorsNumber: Int) = "game-screen/$email/$colorsNumber"
    }

    object ResultsScreen: Screen(route = "results-screen/{email}/{colorsNumber}") {
        fun createRoute(email: String, colorsNumber: Int) = "results-screen/$email/$colorsNumber"
    }

}
