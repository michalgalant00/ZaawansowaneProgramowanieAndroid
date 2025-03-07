package com.example.masterand.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "profile")
data class Profile(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var login: String,
    var email: String,
    var description: String,
    var picture: String? // store uri to img as string
)