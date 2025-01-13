package com.example.masterand.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.masterand.types.FeedbackType
import com.example.masterand.types.GameColor

@Composable
fun Game(
    numberOfColors: Int,
    gameSequence: List<GameColor>,
    onTryComplete: (Boolean) -> Unit
) {
    var tries by remember { mutableStateOf(listOf(0)) }

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        tries.forEach { _ ->
            TryRow(
                onTrySubmitted = { guess ->
                    // Create mutable lists to track matches and used positions
                    val sequence = gameSequence.toMutableList()
                    val guessColors = guess.toMutableList()
                    val feedback = MutableList(4) { FeedbackType.NOT_IN_SEQUENCE }

                    // First pass: Check for correct positions
                    for (i in sequence.indices) {
                        if (sequence[i] == guessColors[i]) {
                            feedback[i] = FeedbackType.CORRECT_POSITION
                            // Mark these positions as used by setting them to NONE
                            sequence[i] = GameColor.NONE
                            guessColors[i] = GameColor.NONE
                        }
                    }

                    // Second pass: Check for correct colors in wrong positions
                    for (i in guessColors.indices) {
                        if (guessColors[i] != GameColor.NONE) { // Skip already matched positions
                            val colorIndex = sequence.indexOf(guessColors[i])
                            if (colorIndex != -1) {
                                // Found color in wrong position
                                feedback[i] = FeedbackType.WRONG_POSITION
                                // Mark this color as used
                                sequence[colorIndex] = GameColor.NONE
                                guessColors[i] = GameColor.NONE
                            }
                        }
                    }

                    // Check if all positions are correct
                    val isWin = feedback.all { it == FeedbackType.CORRECT_POSITION }

                    // Only add new try if game is not won
                    if (!isWin) {
                        tries = tries + (tries.size)
                    }

                    onTryComplete(isWin)
                    feedback
                }
            )
        }
    }
}