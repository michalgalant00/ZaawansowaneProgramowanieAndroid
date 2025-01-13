package com.example.masterand.views

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.masterand.R
import com.example.masterand.components.ProfileCard
import com.example.masterand.models.Profile
import com.example.masterand.navigation.Screen
import com.example.masterand.viewmodels.ProfileViewModel

@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: ProfileViewModel,
    email: String,
    numberOfColors: Int
) {
    LaunchedEffect(email) {
        viewModel.loadProfile(email)
        viewModel.loadAllProfiles()
    }

    val profile = viewModel.profile.value
    val allProfiles = viewModel.allProfiles.value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        profile?.let {
            ProfileCard(profile = it)

            Spacer(modifier = Modifier.height(24.dp))

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = {
                        navController.navigate(
                            route = Screen.GameScreen.createRoute(
                                email = email,
                                colorsNumber = numberOfColors
                            )
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Play")
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth(),
                        onClick = {
                            navController.navigate(
                                route = Screen.ResultsScreen.createRoute(
                                    email = email,
                                    colorsNumber = numberOfColors
                                ))
                        }
                    ) {
                        Text(text = "Results")
                    }

                    Spacer(modifier = Modifier.width(24.dp))

                    Button(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth(),
                        onClick = {
                            navController.navigate(
                                route = Screen.LoginScreen.route
                            )
                        }
                    ) {
                        Text(text = "Log out")
                    }
                }
            }
        } ?: run {
            CircularProgressIndicator()
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(text = "My results")

        // todo zamienic na wyniki
        allProfiles.let {
            Column {
                it.forEach { p ->
                    Text(
                        text = "id: ${p.id} login: ${p.login} email: ${p.email}\n" +
                                "desc: ${p.description}\n" +
                                "picture: ${p.picture}"
                    )
                }
            }
        }
    }
}
