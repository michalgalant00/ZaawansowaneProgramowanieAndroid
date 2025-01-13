package com.example.masterand.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.masterand.navigation.Screen
import com.example.masterand.viewmodels.ResultsViewModel
import kotlinx.coroutines.launch

@Composable
fun ResultsScreen(
    navController: NavController,
    viewModel: ResultsViewModel,
    email: String,
    numberOfColors: Int
) {
    val coroutineScope = rememberCoroutineScope()

    val selectedNumber = viewModel.selectedNumber.intValue

    LaunchedEffect(Unit) {
        viewModel.onNumberSelected(numberOfColors)
    }

    val profilesWithScores = viewModel.profilesWithScores.value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Results",
            fontSize = 24.sp,
            modifier = Modifier
                .padding(bottom = 16.dp)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            OutlinedButton(
                onClick = { viewModel.toggleDropdown() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Number of colors: $selectedNumber")
            }
            DropdownMenu(
                expanded = viewModel.expanded.value,
                onDismissRequest = { viewModel.closeDropdown() },
                modifier = Modifier.fillMaxWidth()
            ) {
                viewModel.availableNumbers.forEach { number ->
                    DropdownMenuItem(
                        onClick = {
                            coroutineScope.launch {
                                viewModel.onNumberSelected(number)
                            }
                        },
                        text = { Text("$number") }
                    )
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Nazwa użytkownika",
                modifier = Modifier.weight(2f),
                textAlign = TextAlign.Center
            )
            Text(
                text = "Wynik",
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )
        }

        profilesWithScores.let {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                it.forEach { score ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = score.profileName,
                            modifier = Modifier
                                .weight(2f)
                                .padding(start = 16.dp),
                            textAlign = TextAlign.Left
                        )
                        Text(
                            text = score.scoreValue.toString(),
                            modifier = Modifier.weight(1f),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }

        Button(
            onClick = {
                navController.navigate(
                    Screen.ProfileScreen.createRoute(
                        email = email,
                        colorsNumber = numberOfColors
                    )
                )
            },
            modifier = Modifier
                .width(150.dp)
        ) {
            Text(text = "Close")
        }
    }
}