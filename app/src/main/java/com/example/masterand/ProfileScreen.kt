package com.example.masterand

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.masterand.models.Profile

private val ProfileSaver = listSaver<Profile, Any>(
    save = { profile ->
        listOf(profile.login, profile.email, profile.description, profile.picture)
    },
    restore = { savedList ->
        Profile(
            login = savedList[0] as String,
            email = savedList[1] as String,
            description = savedList[2] as String,
            picture = savedList[3] as Int
        )
    }
)

private val testusr = Profile(
    login = "andrzej",
    email = "andrzej@ex.com",
    description = "lorem ipsum dolor sit amet",
    picture = R.drawable.default_profile
)

@Composable
fun ProfileScreen() {
    val user by rememberSaveable(stateSaver = ProfileSaver) { mutableStateOf(testusr) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ProfileCard(profile = user)

        Spacer(modifier = Modifier.height(24.dp))

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = { /* Handle navigation to the GameScreen */ },
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
                    onClick = { /* Handle navigation to the ResultsScreen */ }
                ) {
                    Text(text = "Results")
                }

                Spacer(modifier = Modifier.width(24.dp))

                Button(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    onClick = { /* Handle navigation to the ProfileScreenInitial */ }
                ) {
                    Text(text = "Log out")
                }
            }
        }
    }
}

@Composable
fun ProfileCard(profile: Profile) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .height(120.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = rememberAsyncImagePainter(profile.picture),
                contentDescription = "Profile picture",
                modifier = Modifier
                    .size(120.dp)
            )

            Spacer(modifier = Modifier.width(24.dp))

            Column {
                Text(
                    text = profile.login,
                    fontSize = 20.sp
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = profile.description,
                    fontSize = 14.sp
                )
            }
        }
    }
}
