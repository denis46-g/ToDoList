package com.example.todolist.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "actions")
data class Action(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    @ColumnInfo(name = "act") val act: String?,
    @ColumnInfo(name = "isChecked") var isChecked: Boolean?,
    @ColumnInfo(name = "deadline") val deadline: String?,
    @ColumnInfo(name = "difficulty") val difficulty: String?,
    @ColumnInfo(name = "importance") val importance: String?
)