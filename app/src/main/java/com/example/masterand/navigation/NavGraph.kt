package com.example.masterand.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.masterand.viewmodels.GameViewModel
import com.example.masterand.viewmodels.LoginViewModel
import com.example.masterand.viewmodels.ProfileViewModel
import com.example.masterand.viewmodels.ResultsViewModel
import com.example.masterand.views.GameScreen
import com.example.masterand.views.LoginScreen
import com.example.masterand.views.ProfileScreen
import com.example.masterand.views.ResultsScreen

@Composable
fun SetupNavGraph(navController: NavHostController) {
    val loginViewModel: LoginViewModel = hiltViewModel()
    val profileViewModel: ProfileViewModel = hiltViewModel()
    val gameViewModel: GameViewModel = hiltViewModel()
    val resultsViewModel: ResultsViewModel = hiltViewModel()

    NavHost(
        navController = navController,
        startDestination = Screen.LoginScreen.route
    ) {
        composable(route = Screen.LoginScreen.route) {
            loginViewModel.resetForm()
            profileViewModel.resetViewModel()
            gameViewModel.resetViewModel()
            resultsViewModel.resetViewModel()

            LoginScreen(
                navController = navController,
                viewModel = loginViewModel
            )
        }

        composable(
            route = Screen.ProfileScreen.route,
            arguments = listOf(
                navArgument("email") { type = NavType.StringType },
                navArgument("colorsNumber") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email") ?: ""
            val numberOfColors = backStackEntry.arguments?.getInt("colorsNumber") ?: 0

            ProfileScreen(
                navController = navController,
                viewModel = profileViewModel,
                email = email,
                numberOfColors = numberOfColors
            )
        }

        composable(
            route = Screen.GameScreen.route,
            arguments = listOf(
                navArgument("email") { type = NavType.StringType },
                navArgument("colorsNumber") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email") ?: ""
            val numberOfColors = backStackEntry.arguments?.getInt("colorsNumber") ?: 0

            GameScreen(
                navController = navController,
                viewModel = gameViewModel,
                email = email,
                numberOfColors = numberOfColors
            )
        }
        
        composable(
            route = Screen.ResultsScreen.route,
            arguments = listOf(
                navArgument("email") { type = NavType.StringType },
                navArgument("colorsNumber") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email") ?: ""
            val numberOfColors = backStackEntry.arguments?.getInt("colorsNumber") ?: 5

            ResultsScreen(
                navController = navController,
                viewModel = resultsViewModel,
                email = email,
                numberOfColors = numberOfColors
            )
        }
    }
}