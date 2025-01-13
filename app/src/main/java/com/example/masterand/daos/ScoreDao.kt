package com.example.masterand.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.masterand.models.Score

@Dao
interface ScoreDao {
    // todo byc moze bedzie trzeba zmienic na dodawanie po emailu
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(score: Score)
}