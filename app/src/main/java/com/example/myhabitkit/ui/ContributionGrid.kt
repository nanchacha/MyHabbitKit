package com.example.myhabitkit.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun ContributionGrid(
    entries: Set<String>, // format: YYYY-MM-DD
    modifier: Modifier = Modifier,
    habitColor: Color = MaterialTheme.colorScheme.primary,
    columns: Int = 12,
    rows: Int = 7,
    cellSize: Dp = 12.dp,
    cellSpacing: Dp = 4.dp
) {
    // Generate dates for the grid ending at today
    val today = LocalDate.now()
    val totalCells = columns * rows
    val startDate = today.minusDays((totalCells - 1).toLong())
    val formatter = DateTimeFormatter.ISO_LOCAL_DATE

    Row(modifier = modifier, horizontalArrangement = Arrangement.spacedBy(cellSpacing)) {
        for (col in 0 until columns) {
            Column(verticalArrangement = Arrangement.spacedBy(cellSpacing)) {
                for (row in 0 until rows) {
                    val daysOffset = (col * rows) + row
                    val cellDate = startDate.plusDays(daysOffset.toLong())
                    val dateString = cellDate.format(formatter)
                    
                    // Don't show future dates
                    if (cellDate.isAfter(today)) {
                        Box(modifier = Modifier.size(cellSize))
                        continue
                    }

                    val isCompleted = entries.contains(dateString)

                    Box(
                        modifier = Modifier
                            .size(cellSize)
                            .clip(RoundedCornerShape(3.dp))
                            .background(
                                if (isCompleted) habitColor else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
                            )
                    )
                }
            }
        }
    }
}
