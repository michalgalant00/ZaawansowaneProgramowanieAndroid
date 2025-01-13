package com.example.masterand.data

import com.example.masterand.repositories.ProfileRepository
import com.example.masterand.repositories.ProfileWithScoreRepository
import com.example.masterand.repositories.ScoreRepository

interface AppContainer {
    val profileRepository: ProfileRepository
    val profileWithScoreRepository: ProfileWithScoreRepository
    val scoreRepository: ScoreRepository
}