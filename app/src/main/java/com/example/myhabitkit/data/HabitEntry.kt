package com.example.myhabitkit.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "habit_entries",
    foreignKeys = [
        ForeignKey(
            entity = Habit::class,
            parentColumns = ["id"],
            childColumns = ["habitId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["habitId", "dateString"], unique = true)]
)
data class HabitEntry(
    @PrimaryKey(autoGenerate = true) val entryId: Int = 0,
    val habitId: Int,
    val dateString: String // Format: YYYY-MM-DD
)
