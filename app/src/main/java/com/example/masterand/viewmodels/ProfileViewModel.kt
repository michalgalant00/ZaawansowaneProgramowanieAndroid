package com.example.masterand.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.masterand.models.Profile
import com.example.masterand.repositories.ProfileRepository

class ProfileViewModel(
    private val profileRepository: ProfileRepository
) : ViewModel() {
    val profile = mutableStateOf<Profile?>(null)
    // todo usunac
    val allProfiles = mutableStateOf<List<Profile>>(emptyList())

    suspend fun loadProfile(email: String) {
        profile.value = profileRepository.getProfileByEmail(email)
    }

    // todo usunac
    suspend fun loadAllProfiles() {
        allProfiles.value = profileRepository.getAllProfiles()
    }

    fun resetViewModel() {
        val profile = mutableStateOf<Profile?>(null)
        // todo usunac
        val allProfiles = mutableStateOf<List<Profile>>(emptyList())
    }
}