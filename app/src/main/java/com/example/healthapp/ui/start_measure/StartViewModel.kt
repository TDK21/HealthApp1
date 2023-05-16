package com.example.healthapp.ui.start_measure

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class StartViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Прочтите инструкцию перед началом процесса измерения"
    }
    val text: LiveData<String> = _text
}