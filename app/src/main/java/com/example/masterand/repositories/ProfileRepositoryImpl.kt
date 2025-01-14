package com.example.masterand.repositories

import android.net.Uri
import com.example.masterand.daos.ProfileDao
import com.example.masterand.models.Profile

class ProfileRepositoryImpl(
    private val profileDao: ProfileDao
) : ProfileRepository {
    override suspend fun insertProfile(profile: Profile) {
        profileDao.insert(profile)
    }

    override suspend fun updateProfile(email: String, login: String, description: String, picture: String) {
        profileDao.update(email, login, description, picture)
    }

    override suspend fun getProfileByEmail(email: String): Profile {
        return profileDao.getProfileByEmail(email)
    }
}