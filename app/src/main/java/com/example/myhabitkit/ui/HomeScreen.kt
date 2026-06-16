package com.example.myhabitkit.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HabitViewModel,
    onAddHabitClick: () -> Unit,
    onHabitClick: (Int) -> Unit,
    onSettingsClick: () -> Unit
) {
    val habits by viewModel.allHabits.collectAsState()
    val entries by viewModel.allEntries.collectAsState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("MyHabitKit", fontWeight = FontWeight.Bold) },
                actions = {
                    IconButton(onClick = onSettingsClick) {
                        Icon(Icons.Default.Settings, contentDescription = "Settings")
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddHabitClick, containerColor = MaterialTheme.colorScheme.primary) {
                Icon(Icons.Default.Add, contentDescription = "Add Habit", tint = MaterialTheme.colorScheme.onPrimary)
            }
        }
    ) { paddingValues ->
        if (habits.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize().padding(paddingValues), contentAlignment = Alignment.Center) {
                Text("No habits yet. Tap + to add one!", color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(habits) { habit ->
                    val habitEntries = entries.filter { it.habitId == habit.id }.map { it.dateString }.toSet()
                    HabitCard(
                        habit = habit,
                        entries = habitEntries,
                        onClick = { onHabitClick(habit.id) },
                        onToggleToday = { isCompleted ->
                            val today = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE)
                            viewModel.toggleEntry(habit.id, today, isCompleted)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun HabitCard(
    habit: com.example.myhabitkit.data.Habit,
    entries: Set<String>,
    onClick: () -> Unit,
    onToggleToday: (Boolean) -> Unit
) {
    val today = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE)
    val isCompletedToday = entries.contains(today)
    val habitColor = Color(habit.color.toULong())

    Card(
        modifier = Modifier.fillMaxWidth().clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(habit.name, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                Checkbox(
                    checked = isCompletedToday,
                    onCheckedChange = { onToggleToday(it) },
                    colors = CheckboxDefaults.colors(checkedColor = habitColor)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            ContributionGrid(
                entries = entries,
                habitColor = habitColor,
                columns = 16,
                rows = 5,
                cellSize = 14.dp,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}
