package com.example.masterand.types

import androidx.compose.ui.graphics.Color

enum class GameColor {
    NONE,
    RED, BLUE, GREEN, YELLOW, PURPLE,
    ORANGE, PINK, BROWN, CYAN, GRAY;

    fun toColor(): Color {
        return when(this) {
            NONE -> Color.White
            RED -> Color.Red
            BLUE -> Color.Blue
            GREEN -> Color.Green
            YELLOW -> Color.Yellow
            PURPLE -> Color(0xFF800080)
            ORANGE -> Color(0xFFFFA500)
            PINK -> Color(0xFFFF69B4)
            BROWN -> Color(0xFF8B4513)
            CYAN -> Color(0xFF00FFFF)
            GRAY -> Color.Gray
        }
    }

    fun next(usedColors: Set<GameColor> = emptySet()): GameColor {
        val availableValues = entries.toTypedArray().filter { it !in usedColors || it == NONE }
        val currentIndex = availableValues.indexOf(this)
        val nextIndex = (currentIndex + 1) % availableValues.size
        return availableValues[nextIndex]
    }

    companion object {
        private var availableColors: List<GameColor> = emptyList()

        fun initializeAvailableColors(numberOfColors: Int) {
            availableColors = entries
                .filter { it != NONE }
                .shuffled()
                .take(numberOfColors)
        }

        fun getAvailableColors(): List<GameColor> = availableColors

        fun getGameSequence(): List<GameColor> {
            return availableColors
                .shuffled()
                .take(4)
        }
    }
}