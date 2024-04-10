package com.example.todolist.ui.actioninfo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ActioninfoViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is actioninfo Fragment"
    }
    val text: LiveData<String> = _text
}