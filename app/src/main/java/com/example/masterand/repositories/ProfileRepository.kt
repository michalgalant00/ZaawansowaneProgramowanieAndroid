package com.example.masterand.repositories

import android.net.Uri
import com.example.masterand.models.Profile

interface ProfileRepository {
    suspend fun insertProfile(profile: Profile)
    suspend fun updateProfile(email: String, login: String, description: String, picture: String)
    suspend fun getProfileByEmail(email: String): Profile?

    // todo usunac
    suspend fun getAllProfiles(): List<Profile>
}