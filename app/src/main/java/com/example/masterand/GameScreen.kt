package com.example.masterand

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.masterand.navigation.Screen
import com.example.masterand.types.FeedbackType
import com.example.masterand.types.GameColor

val MAIN_CIRCLE_SIZE = 56.dp
val FEEDBACK_CIRCLE_SIZE = 28.dp

@Composable
fun GameScreen(
    navController: NavController,
    numberOfColors: Int
) {
    val gameSequence = remember { GameColor.getGameSequence() }
    println(gameSequence) // debug game sequence
    var tryCounter by rememberSaveable { mutableIntStateOf(0) }
    var isGameWon by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column (
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(text = "Counter: $tryCounter")

            Spacer(modifier = Modifier.height(32.dp))

            Game(
                numberOfColors = numberOfColors,
                gameSequence = gameSequence,
                onTryComplete = { isWin ->
                    if (!isWin) {
                        tryCounter++
                    }
                    isGameWon = isWin
                }
            )

            if (isGameWon) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "You won!")
            }
        }

        Button(
            onClick = {
                navController.navigate(Screen.GameScreen.route)
            },
            modifier = Modifier
                .width(150.dp)
        ) {
            Text(text = "Close")
        }
    }
}

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

@Composable
fun TryRow(
    onTrySubmitted: (List<GameColor>) -> List<FeedbackType>,
    modifier: Modifier = Modifier,
) {
    var colors by remember { mutableStateOf(List(4) { GameColor.NONE }) }
    var feedback by remember { mutableStateOf<List<FeedbackType>?>(null) }
    var isSubmitted by remember { mutableStateOf(false) }
    var showColorPicker by remember { mutableStateOf(false) }
    var selectedIndex by remember { mutableStateOf(-1) }

    if (showColorPicker) {
        ColorPickerModal(
            onDismiss = { showColorPicker = false },
            onColorSelected = { color ->
                val newColors = colors.toMutableList()
                newColors[selectedIndex] = color
                colors = newColors
                showColorPicker = false
            },
            usedColors = colors.toSet()
        )
    }

    Row(
        modifier = modifier
            .height(64.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Main circles
        colors.forEachIndexed { index, currentColor ->
            ColorCircle(
                color = currentColor,
                enabled = !isSubmitted,
                onClick = {
                    if (!isSubmitted) {
                        selectedIndex = index
                        showColorPicker = true
                    }
                }
            )
        }

        // Confirm button
        if ((colors.none { it == GameColor.NONE } && !isSubmitted) || isSubmitted) {
            ConfirmButton(
                modifier = Modifier,
                onClick = {
                    feedback = onTrySubmitted(colors)
                    isSubmitted = true
                }
            )
        } else {
            Spacer(modifier = Modifier.width(MAIN_CIRCLE_SIZE))
        }

        // Feedback circles
        if (isSubmitted && feedback != null) {
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    FeedbackCircle(type = feedback!![0])
                    FeedbackCircle(type = feedback!![1])
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    FeedbackCircle(type = feedback!![2])
                    FeedbackCircle(type = feedback!![3])
                }
            }
        } else {
            Spacer(modifier = Modifier
                .width(MAIN_CIRCLE_SIZE - 2.dp)
                .height(MAIN_CIRCLE_SIZE))
        }
    }
}

@Composable
fun ConfirmButton(
    modifier: Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .size(MAIN_CIRCLE_SIZE)
            .clip(CircleShape)
            .border(2.dp, Color.Black, CircleShape),
        contentAlignment = Alignment.Center
    ) {
        IconButton(
            onClick = onClick
        ) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_check_24),
                contentDescription = "Confirm"
            )
        }
    }
}

@Composable
fun ColorCircle(
    color: GameColor,
    enabled: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(MAIN_CIRCLE_SIZE)
            .clip(CircleShape)
            .background(color.toColor())
            .border(2.dp, Color.Black, CircleShape)
            .then(if (enabled) Modifier.clickable(onClick = onClick) else Modifier)
    )
}

@Composable
fun FeedbackCircle(
    type: FeedbackType,
    modifier: Modifier = Modifier
) {
    val color = when (type) {
        FeedbackType.CORRECT_POSITION -> Color.Red
        FeedbackType.WRONG_POSITION -> Color.Yellow
        FeedbackType.NOT_IN_SEQUENCE -> Color.White
    }

    Box(
        modifier = modifier
            .size(FEEDBACK_CIRCLE_SIZE)
            .clip(CircleShape)
            .background(color)
            .border(1.dp, Color.Black, CircleShape)
    )
}

@Composable
fun ColorPickerModal(
    onDismiss: () -> Unit,
    onColorSelected: (GameColor) -> Unit,
    usedColors: Set<GameColor>
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Choose color") },
        text = {
            Column {
                GameColor.entries
                    .filter { it != GameColor.NONE && it !in usedColors }
                    .forEach { color ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onColorSelected(color) }
                                .padding(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(FEEDBACK_CIRCLE_SIZE)
                                    .clip(CircleShape)
                                    .background(color.toColor())
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = color.name)
                        }
                    }
            }
        },
        confirmButton = {
            Button(onClick = onDismiss) {
                Text("Close")
            }
        }
    )
}