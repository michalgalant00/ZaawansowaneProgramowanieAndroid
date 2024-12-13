package com.example.masterand.navigation

sealed class Screen(val route: String) {

    object ProfileScreenInitial: Screen(route = "profile-screen-initial")
    object ProfileScreen: Screen(route = "profile-screen")
    object GameScreen: Screen(route = "game-screen")

}
