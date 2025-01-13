package com.example.masterand.viewmodels

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.masterand.models.Score
import com.example.masterand.repositories.ScoreRepository
import com.example.masterand.types.GameColor

class GameViewModel(
    private val scoreRepository: ScoreRepository
) : ViewModel() {

    var gameSequence = mutableStateOf(emptyList<GameColor>())
    val tryCounter = mutableIntStateOf(0)
    var isGameWon = mutableStateOf(false)

    private fun generateGameSequence() {
        gameSequence.value = GameColor.getGameSequence()
    }

    fun onTryComplete(isWin: Boolean) {
        tryCounter.intValue++
        isGameWon.value = isWin
    }

    suspend fun saveScore(email: String, score: Int, numberOfColors: Int) {
        val scoreToSave = Score(
            email = email,
            numberOfColors = numberOfColors,
            value = score
        )

        scoreRepository.insertScore(scoreToSave)
    }

    fun startNewGame() {
        resetViewModel()
        generateGameSequence()
    }

    fun resetViewModel() {
        gameSequence.value = emptyList()
        tryCounter.intValue = 0
        isGameWon.value = false
    }
}
