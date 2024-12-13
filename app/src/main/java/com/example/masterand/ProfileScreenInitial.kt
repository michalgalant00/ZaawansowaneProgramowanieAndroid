package com.example.masterand

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.masterand.navigation.Screen

@Composable
fun ProfileScreenInitial(
    navController: NavController
) {
    var name by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var colorsNumber by rememberSaveable { mutableStateOf("") }
    var profileImageUri by rememberSaveable { mutableStateOf<Uri?>(null) }

    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { selectedUri ->
            profileImageUri = selectedUri
        }
    )

    // validation booleans
    var isNameValid by rememberSaveable { mutableStateOf(false) }
    var isEmailValid by rememberSaveable { mutableStateOf(false) }
    var isNumberOfColorsValid by rememberSaveable { mutableStateOf(false) }

    // context for toast showing
    val context = LocalContext.current

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

        ProfileImageWithPicker(profileImageUri) {
            imagePicker.launch(
                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        OutlinedTextFieldWithError(
            value = name,
            onValueChange = {
                name = it
                isNameValid = it.isNotEmpty() && it.length > 3
            },
            label = "Enter name",
            error = "Name can't be empty and must be longer than 3 characters",
            isValid = { it.isNotEmpty() && it.length > 3 }
        )

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextFieldWithError(
            value = email,
            onValueChange = {
                email = it
                isEmailValid = android.util.Patterns.EMAIL_ADDRESS.matcher(it).matches()
            },
            label = "Enter email",
            error = "Invalid email format",
            isValid = { android.util.Patterns.EMAIL_ADDRESS.matcher(it).matches() },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextFieldWithError(
            value = colorsNumber,
            onValueChange = {
                colorsNumber = it

                val number = it.toIntOrNull()
                isNumberOfColorsValid = number != null && number in 5..10
            },
            label = "Enter number of colors",
            error = "Number must be between 5 and 10",
            isValid = {
                val number = it.toIntOrNull()
                number != null && number in 5..10
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(40.dp))

        Button(
            onClick = {
                // debug
                navController.navigate(route = Screen.ProfileScreen.route)

                // working
//                if (isNameValid && isEmailValid && isNumberOfColorsValid)
//                    navController.navigate(route = Screen.ProfileScreen.route)
//                else
//                    Toast.makeText(
//                        context,
//                        "Please fill in all fields correctly before proceeding.",
//                        Toast.LENGTH_SHORT
//                    ).show()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Next")
        }
    }
}

@Composable
fun ProfileImageWithPicker(imageUri: Uri?, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(160.dp)
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        if (imageUri == null) {
            Image(
                painter = painterResource(id = R.drawable.default_profile),
                contentDescription = null,
                modifier = Modifier.size(120.dp)
            )
        } else {
            Image(
                painter = rememberAsyncImagePainter(imageUri),
                contentDescription = null,
                modifier = Modifier.size(120.dp)
            )
        }
        IconButton(
            onClick = onClick,
            modifier = Modifier.align(Alignment.TopEnd)
        ) {
            Icon(
                painter = painterResource(id = android.R.drawable.ic_menu_gallery),
                contentDescription = "Pick Image"
            )
        }
    }
}

@Composable
fun OutlinedTextFieldWithError(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    error: String,
    isValid: (String) -> Boolean,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default
) {
    var showError by remember { mutableStateOf(false) }

    Column {
        OutlinedTextField(
            value = value,
            onValueChange = {
                onValueChange(it)
                showError = !isValid(it)
            },
            label = { Text(label) },
            isError = showError,
            keyboardOptions = keyboardOptions,
            modifier = Modifier.fillMaxWidth()
        )
        if (showError) {
            Text(
                text = error,
                color = Color.Red,
                fontSize = 12.sp,
                modifier = Modifier.padding(start = 16.dp)
            )
        }
    }
}
