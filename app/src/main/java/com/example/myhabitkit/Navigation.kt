package com.example.myhabitkit

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.example.myhabitkit.data.AppDatabase
import com.example.myhabitkit.ui.AddEditHabitScreen
import com.example.myhabitkit.ui.HabitDetailScreen
import com.example.myhabitkit.ui.HomeScreen
import com.example.myhabitkit.ui.HabitViewModel

@Composable
fun MainNavigation() {
    val context = LocalContext.current
    val database = AppDatabase.getDatabase(context)
    val factory = object : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return HabitViewModel(database.habitDao()) as T
        }
    }
    val viewModel: HabitViewModel = viewModel(factory = factory)

    val backStack = rememberNavBackStack(Home)

    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryProvider = entryProvider {
            entry<Home> {
                HomeScreen(
                    viewModel = viewModel,
                    onAddHabitClick = { backStack.add(AddHabit) },
                    onHabitClick = { id -> backStack.add(HabitDetail(id)) },
                    onSettingsClick = { backStack.add(Settings) }
                )
            }
            entry<AddHabit> {
                AddEditHabitScreen(
                    viewModel = viewModel,
                    onBack = { backStack.removeLastOrNull() }
                )
            }
            entry<Settings> {
                com.example.myhabitkit.ui.SettingsScreen(
                    viewModel = viewModel,
                    onBack = { backStack.removeLastOrNull() }
                )
            }
            entry<HabitDetail> { navKey ->
                HabitDetailScreen(
                    habitId = navKey.habitId,
                    viewModel = viewModel,
                    onBack = { backStack.removeLastOrNull() },
                    onDeleted = { backStack.removeLastOrNull() }
                )
            }
        }
    )
}
