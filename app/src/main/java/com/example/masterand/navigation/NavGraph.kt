package com.example.masterand.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.masterand.GameScreen
import com.example.masterand.ProfileScreen
import com.example.masterand.ProfileScreenInitial

@Composable
fun SetupNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        // debug gamescreen
//        startDestination = "profile-screen-initial"
        startDestination = "game-screen"
    ) {
        composable(route = Screen.ProfileScreenInitial.route) {
            ProfileScreenInitial(navController = navController)
        }

        composable(route = Screen.ProfileScreen.route) {
            ProfileScreen(navController = navController)
        }

        composable(route = Screen.GameScreen.route) {
            GameScreen(navController = navController,
                //debug
                numberOfColors = 5
            )
        }
    }
}