package com.example.masterand.viewmodels

import android.net.Uri
import android.util.Patterns
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.masterand.models.Profile
import com.example.masterand.repositories.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor (
    private val profileRepository: ProfileRepository
) : ViewModel() {

    var login = mutableStateOf("")
    var email = mutableStateOf("")
    var colorsNumber = mutableStateOf("")
    var profileImageUri = mutableStateOf<Uri?>(null)

    var isNameValid = mutableStateOf(false)
    var isEmailValid = mutableStateOf(false)
    var isNumberOfColorsValid = mutableStateOf(false)

    suspend fun login() {
        val existingProfile = profileRepository.getProfileByEmail(email.value)

        val profile = Profile(
            login = login.value,
            email = email.value,
            description = "hi! i'm ${login.value}, my email is ${email.value}. ${colorsNumber.value}",
            picture = profileImageUri.value?.toString() ?: ""
        )

        if (existingProfile != null && existingProfile.email == email.value)
            profileRepository.updateProfile(
                email = email.value,
                login = login.value,
                description = profile.description,
                picture = profile.picture.toString()
            )
        else
            profileRepository.insertProfile(profile)
    }

    fun validateName(): Boolean {
        return login.value.isNotEmpty() && login.value.length >= 3
    }

    fun validateEmail(): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email.value).matches()
    }

    fun validateNumberOfColors(): Boolean {
        val number = colorsNumber.value.toIntOrNull()
        return number != null && number in 5..10
    }

    fun validateForm(): Boolean {
        return validateName() && validateEmail() && validateNumberOfColors()
    }

    fun resetForm() {
        login.value = ""
        email.value = ""
        colorsNumber.value = ""
        profileImageUri.value = null

        isNameValid.value = false
        isEmailValid.value = false
        isNumberOfColorsValid.value = false
    }
}