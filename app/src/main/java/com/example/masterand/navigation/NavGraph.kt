package com.example.masterand.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.masterand.MasterAndApplication
import com.example.masterand.viewmodels.*
import com.example.masterand.views.*

@Composable
fun SetupNavGraph(navController: NavHostController) {
    val loginViewModel: LoginViewModel =
        viewModel(factory = MasterAndApplication.AppViewModelProvider.Factory)
    val profileViewModel: ProfileViewModel =
        viewModel(factory = MasterAndApplication.AppViewModelProvider.Factory)
    val gameViewModel: GameViewModel =
        viewModel(factory = MasterAndApplication.AppViewModelProvider.Factory)
    val resultsViewModel: ResultsViewModel =
        viewModel(factory = MasterAndApplication.AppViewModelProvider.Factory)


    NavHost(
        navController = navController,
        startDestination = Screen.LoginScreen.route
    ) {
        composable(route = Screen.LoginScreen.route) {
            // todo formularz tez ma sie resetowac
//            loginViewModel.resetForm()
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