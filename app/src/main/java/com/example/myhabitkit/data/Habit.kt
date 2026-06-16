package com.example.myhabitkit.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "habits")
data class Habit(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val color: Long, // Stores Jetpack Compose Color as ARGB Long
    val frequency: Int, // E.g., 1 for daily
    val createdAt: Long = System.currentTimeMillis()
)
