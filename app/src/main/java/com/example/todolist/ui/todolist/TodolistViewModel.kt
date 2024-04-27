package com.example.todolist.ui.todolist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.todolist.data.Action
import com.example.todolist.data.AppDatabase
import com.example.todolist.data.AppRepository
import kotlinx.coroutines.launch

class TodolistViewModel(application: Application) : AndroidViewModel(application) {

    val actions: LiveData<List<Action>>

    private val repository : AppRepository

    init{
        val actionDao = AppDatabase.getDataBase(application).actionDao()
        repository = AppRepository(actionDao)
        actions = repository.getAll()
    }
    fun insert(act : Action) = viewModelScope.launch {
        repository.insert(act)
    }

    fun update(act: Action) = viewModelScope.launch {
        repository.update(act)
    }
    fun delete(act : Action) = viewModelScope.launch {
        repository.delete(act)
    }
}