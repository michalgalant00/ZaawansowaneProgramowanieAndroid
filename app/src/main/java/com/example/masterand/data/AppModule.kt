package com.example.masterand.data

import android.content.Context
import com.example.masterand.daos.ProfileDao
import com.example.masterand.daos.ProfileWithScoreDao
import com.example.masterand.daos.ScoreDao
import com.example.masterand.database.MasterAndDatabase
import com.example.masterand.repositories.ProfileRepository
import com.example.masterand.repositories.ProfileRepositoryImpl
import com.example.masterand.repositories.ProfileWithScoreRepository
import com.example.masterand.repositories.ProfileWithScoreRepositoryImpl
import com.example.masterand.repositories.ScoreRepository
import com.example.masterand.repositories.ScoreRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): MasterAndDatabase {
        return MasterAndDatabase.getDatabase(context)
    }

    @Provides
    @Singleton
    fun provideProfileDao(database: MasterAndDatabase): ProfileDao {
        return database.profileDao()
    }

    @Provides
    @Singleton
    fun provideProfileWithScoreDao(database: MasterAndDatabase): ProfileWithScoreDao {
        return database.profileWithScoreDao()
    }

    @Provides
    @Singleton
    fun provideScoreDao(database: MasterAndDatabase): ScoreDao {
        return database.scoreDao()
    }

    @Provides
    @Singleton
    fun provideProfileRepository(profileDao: ProfileDao): ProfileRepository {
        return ProfileRepositoryImpl(profileDao)
    }

    @Provides
    @Singleton
    fun provideProfileWithScoreRepository(profileWithScoreDao: ProfileWithScoreDao): ProfileWithScoreRepository {
        return ProfileWithScoreRepositoryImpl(profileWithScoreDao)
    }

    @Provides
    @Singleton
    fun provideScoreRepository(scoreDao: ScoreDao): ScoreRepository {
        return ScoreRepositoryImpl(scoreDao)
    }
}