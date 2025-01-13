package com.example.masterand.data

import android.content.Context
import com.example.masterand.database.MasterAndDatabase
import com.example.masterand.repositories.ProfileRepository
import com.example.masterand.repositories.ProfileRepositoryImpl
import com.example.masterand.repositories.ProfileWithScoreRepository
import com.example.masterand.repositories.ProfileWithScoreRepositoryImpl
import com.example.masterand.repositories.ScoreRepository
import com.example.masterand.repositories.ScoreRepositoryImpl

class AppDataContainer (
    private val context: Context
) : AppContainer {
    override val profileRepository: ProfileRepository by lazy {
        ProfileRepositoryImpl(MasterAndDatabase.getDatabase(context).profileDao())
    }

    override val profileWithScoreRepository: ProfileWithScoreRepository by lazy {
        ProfileWithScoreRepositoryImpl(MasterAndDatabase.getDatabase(context).profileWithScoreDao())
    }

    override val scoreRepository: ScoreRepository by lazy {
        ScoreRepositoryImpl(MasterAndDatabase.getDatabase(context).scoreDao())
    }
}