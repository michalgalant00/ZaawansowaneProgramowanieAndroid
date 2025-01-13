package com.example.masterand.repositories

import com.example.masterand.models.ProfileWithScore

interface ProfileWithScoreRepository {
    suspend fun getProfilesWithScores(selectedNumber: Int): List<ProfileWithScore>
}