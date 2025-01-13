package com.example.masterand.repositories

import com.example.masterand.daos.ScoreDao
import com.example.masterand.models.Score

class ScoreRepositoryImpl(
    private val scoreDao: ScoreDao
) : ScoreRepository {
    override suspend fun insertScore(score: Score) {
        scoreDao.insert(score)
    }
}