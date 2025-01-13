package com.example.masterand.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "score")
data class Score (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var email: String,
    var numberOfColors: Int,
    var value: Int
)