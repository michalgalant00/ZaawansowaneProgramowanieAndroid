package com.example.masterand.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.masterand.models.Profile
import com.example.masterand.repositories.ProfileRepository

class ProfileViewModel(
    private val profileRepository: ProfileRepository
) : ViewModel() {
    val profile = mutableStateOf<Profile?>(null)

    suspend fun loadProfile(email: String) {
        profile.value = profileRepository.getProfileByEmail(email)
    }

    fun resetViewModel() {
        val profile = mutableStateOf<Profile?>(null)
    }
}