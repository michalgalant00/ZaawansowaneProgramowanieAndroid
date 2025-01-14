package com.example.masterand.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.masterand.models.Profile
import com.example.masterand.repositories.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor (
    private val profileRepository: ProfileRepository
) : ViewModel() {
    var profile = mutableStateOf<Profile?>(null)

    suspend fun loadProfile(email: String) {
        profile.value = profileRepository.getProfileByEmail(email)
    }

    fun resetViewModel() {
        profile = mutableStateOf<Profile?>(null)
    }
}