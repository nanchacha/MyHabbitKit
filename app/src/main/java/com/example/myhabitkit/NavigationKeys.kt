package com.example.myhabitkit

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable data object Home : NavKey
@Serializable data object AddHabit : NavKey
@Serializable data object Settings : NavKey
@Serializable data class HabitDetail(val habitId: Int) : NavKey
