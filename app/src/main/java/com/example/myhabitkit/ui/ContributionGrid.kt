package com.example.myhabitkit.ui

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.Text
import androidx.compose.ui.text.style.TextOverflow
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun ContributionGrid(
    entries: Set<String>, // format: YYYY-MM-DD
    modifier: Modifier = Modifier,
    habitColor: Color = MaterialTheme.colorScheme.primary,
    emptyColor: Color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
    columns: Int = 12,
    rows: Int = 7,
    cellSpacing: Dp = 4.dp,
    cellSize: Dp = 14.dp
) {
    // Generate dates for the grid ending at today
    val today = LocalDate.now()
    val totalCells = columns * rows
    val startDate = today.minusDays((totalCells - 1).toLong())
    val formatter = DateTimeFormatter.ISO_LOCAL_DATE

    Column(modifier = modifier.fillMaxWidth().horizontalScroll(rememberScrollState(initial = 10000))) {
        // Month labels
        Row(horizontalArrangement = Arrangement.spacedBy(cellSpacing)) {
            var lastMonth = -1
            for (col in 0 until columns) {
                val daysOffset = (col * rows)
                val firstCellDate = startDate.plusDays(daysOffset.toLong())
                val currentMonth = firstCellDate.monthValue
                
                Box(modifier = Modifier.width(cellSize)) {
                    if (col == 0 || currentMonth != lastMonth) {
                        Text(
                            text = firstCellDate.month.name.take(3), // "JAN", "FEB", etc.
                            style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.sp),
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            maxLines = 1,
                            overflow = TextOverflow.Visible
                        )
                        lastMonth = currentMonth
                    }
                }
            }
        }
        
        Spacer(modifier = Modifier.height(4.dp))
        
        // Main Grid
        Row(horizontalArrangement = Arrangement.spacedBy(cellSpacing)) {
            for (col in 0 until columns) {
                Column(modifier = Modifier.width(cellSize), verticalArrangement = Arrangement.spacedBy(cellSpacing)) {
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
                                .border(
                                    width = 1.dp,
                                    color = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f),
                                    shape = RoundedCornerShape(3.dp)
                                )
                                .background(
                                    if (isCompleted) habitColor else emptyColor
                                )
                        )
                    }
                }
            }
        }
    }
}
