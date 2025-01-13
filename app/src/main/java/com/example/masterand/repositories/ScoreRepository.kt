package com.example.masterand.repositories

import com.example.masterand.models.Score

interface ScoreRepository {
    suspend fun insertScore(score: Score)
}