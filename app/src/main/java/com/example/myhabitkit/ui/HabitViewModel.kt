package com.example.myhabitkit.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myhabitkit.data.Habit
import com.example.myhabitkit.data.HabitDao
import com.example.myhabitkit.data.HabitEntry
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HabitViewModel(private val habitDao: HabitDao) : ViewModel() {

    val allHabits = habitDao.getAllHabits()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allEntries = habitDao.getAllEntries()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun insertHabit(habit: Habit) {
        viewModelScope.launch {
            habitDao.insertHabit(habit)
        }
    }

    fun deleteHabit(habit: Habit) {
        viewModelScope.launch {
            habitDao.deleteHabit(habit)
        }
    }

    fun toggleEntry(habitId: Int, dateString: String, isCompleted: Boolean) {
        viewModelScope.launch {
            if (isCompleted) {
                habitDao.insertEntry(HabitEntry(habitId = habitId, dateString = dateString))
            } else {
                habitDao.deleteEntry(habitId = habitId, dateString = dateString)
            }
        }
    }
}
