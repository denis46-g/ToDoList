package com.example.todolist.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ActionDao {
    @Query("SELECT * FROM actions ORDER BY id ASC")
    fun getAll(): LiveData<List<Action>>

    @Insert(entity = Action::class)
    suspend fun insert(act: Action)

    @Update
    suspend fun update(act: Action)

    @Delete
    suspend fun delete(act: Action)
}