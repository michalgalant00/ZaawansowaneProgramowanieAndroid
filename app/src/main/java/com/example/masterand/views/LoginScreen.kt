package com.example.masterand.views

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.masterand.components.OutlinedTextFieldWithError
import com.example.masterand.components.ProfileImageWithPicker
import com.example.masterand.navigation.Screen
import com.example.masterand.viewmodels.LoginViewModel
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    navController: NavController
) {
    // form fields
    val login = viewModel.login
    val email = viewModel.email
    val colorsNumber = viewModel.colorsNumber
    val profileImageUri = viewModel.profileImageUri
    // validation fields
    val isNameValid = viewModel.isNameValid
    val isEmailValid = viewModel.isEmailValid
    val isNumberOfColorsValid = viewModel.isNumberOfColorsValid

    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { selectedUri ->
            profileImageUri.value = selectedUri
        }
    )

    // context for toast showing
    val context = LocalContext.current

    // coroutine scope for suspended functions
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "MasterAnd",
            fontSize = 56.sp,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(24.dp))

        ProfileImageWithPicker(profileImageUri.value) {
            imagePicker.launch(
                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        OutlinedTextFieldWithError(
            value = login.value,
            onValueChange = {
                login.value = it
                isNameValid.value = viewModel.validateName()
            },
            label = "Enter name",
            error = "Name can't be empty and must be minimum 3 characters long",
            isValid = { viewModel.validateName() }
        )

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextFieldWithError(
            value = email.value,
            onValueChange = {
                email.value = it
                isEmailValid.value = viewModel.validateEmail()
            },
            label = "Enter email",
            error = "Invalid email format",
            isValid = { viewModel.validateEmail() },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextFieldWithError(
            value = colorsNumber.value,
            onValueChange = {
                colorsNumber.value = it
                isNumberOfColorsValid.value = viewModel.validateNumberOfColors()
            },
            label = "Enter number of colors",
            error = "Number must be between 5 and 10",
            isValid = { viewModel.validateNumberOfColors() },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(40.dp))

        Button(
            onClick = {
                if (viewModel.validateForm()) {
                    coroutineScope.launch {
                        viewModel.login()

                        navController.navigate(
                            route = Screen.ProfileScreen.createRoute(
                                email = viewModel.email.value,
                                colorsNumber = colorsNumber.value.toInt()
                            )
                        )
                    }
                }
                else
                    Toast.makeText(
                        context,
                        "Please fill in all fields correctly before proceeding.",
                        Toast.LENGTH_SHORT
                    ).show()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Login")
        }
    }
}




