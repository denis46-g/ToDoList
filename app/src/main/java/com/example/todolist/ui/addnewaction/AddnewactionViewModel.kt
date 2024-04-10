package com.example.todolist.ui.addnewaction

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AddnewactionViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is addnewaction Fragment"
    }
    val text: LiveData<String> = _text
}