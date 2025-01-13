package com.example.masterand.repositories

import com.example.masterand.daos.ProfileWithScoreDao
import com.example.masterand.models.ProfileWithScore

class ProfileWithScoreRepositoryImpl (
    private val profileWithScoreDao: ProfileWithScoreDao
) : ProfileWithScoreRepository {
    override suspend fun getProfilesWithScores(selectedNumber: Int): List<ProfileWithScore> {
        return profileWithScoreDao.getProfilesWithScores(selectedNumber = selectedNumber)
    }
}