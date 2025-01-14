package com.example.masterand.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.masterand.components.Game
import com.example.masterand.navigation.Screen
import com.example.masterand.viewmodels.GameViewModel
import kotlinx.coroutines.launch

val MAIN_CIRCLE_SIZE = 56.dp
val FEEDBACK_CIRCLE_SIZE = 28.dp

@Composable
fun GameScreen(
    navController: NavController,
    viewModel: GameViewModel,
    email: String,
    numberOfColors: Int
) {
    LaunchedEffect(Unit) {
        viewModel.startNewGame(numberOfColors)
    }

    val coroutineScope = rememberCoroutineScope()

    val gameSequence = viewModel.gameSequence.value
    val tryCounter = viewModel.tryCounter.intValue
    val isGameWon = viewModel.isGameWon.value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column (
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(text = "Counter: $tryCounter")

            Spacer(modifier = Modifier.height(32.dp))

            Game(
                gameSequence = gameSequence,
                onTryComplete = { isWin ->
                    coroutineScope.launch {
                        viewModel.onTryComplete(isWin, email, numberOfColors)
                    }
                }
            )

            if (isGameWon) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "You won!")
            }
        }

        Button(
            onClick = {
                navController.navigate(Screen.ProfileScreen.createRoute(
                    email = email,
                    colorsNumber = numberOfColors
                ))
            },
            modifier = Modifier
                .width(150.dp)
        ) {
            Text(text = "Close")
        }
    }
}
