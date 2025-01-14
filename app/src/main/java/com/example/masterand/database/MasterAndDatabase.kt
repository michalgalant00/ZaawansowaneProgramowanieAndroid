package com.example.masterand.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.masterand.daos.*
import com.example.masterand.models.Profile
import com.example.masterand.models.Score

@Database(
    entities = [Profile::class, Score::class],
    version = 2,
    exportSchema = false
)
abstract class MasterAndDatabase : RoomDatabase() {

    abstract fun profileDao(): ProfileDao
    abstract fun profileWithScoreDao(): ProfileWithScoreDao
    abstract fun scoreDao(): ScoreDao

    companion object {
        private const val DB_NAME = "masterand_database"

        @Volatile
        private var Instance: MasterAndDatabase? = null

        fun getDatabase(context: Context): MasterAndDatabase {
            return Room.databaseBuilder(
                context,
                MasterAndDatabase::class.java,
                DB_NAME
            )
                .build().also { Instance = it }
        }
    }
}