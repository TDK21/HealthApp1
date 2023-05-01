package com.example.healthapp.ui.start_measure

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class StartViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Read instruction before measuring and press start button below"
    }
    val text: LiveData<String> = _text
}