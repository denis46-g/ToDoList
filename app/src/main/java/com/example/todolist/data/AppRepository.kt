package com.example.todolist.data

import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AppRepository(private val actionDao: ActionDao) {

    fun getAll(): LiveData<List<Action>> {
        return actionDao.getAll()
    }
    suspend fun insert(act: Action) {
        withContext(Dispatchers.IO) {
            actionDao.insert(act)
        }
    }

    suspend fun update(act: Action) {
        withContext(Dispatchers.IO) {
            actionDao.update(act)
        }
    }
    suspend fun delete(act: Action) {
        withContext(Dispatchers.IO) {
            actionDao.delete(act)
        }
    }


}