package com.example.masterand.models

data class ProfileWithScore (
    val profileEmail: String,
    val scoreId: Int,
    val profileName: String,
    val scoreValue: Int,
    val numberOfColors: Int
)