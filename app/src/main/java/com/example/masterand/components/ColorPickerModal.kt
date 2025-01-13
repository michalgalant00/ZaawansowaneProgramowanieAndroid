package com.example.masterand.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.masterand.views.FEEDBACK_CIRCLE_SIZE
import com.example.masterand.types.GameColor

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