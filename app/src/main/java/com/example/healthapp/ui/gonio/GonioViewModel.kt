package com.example.healthapp.ui.gonio

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class GonioViewModel : ViewModel() {
    val _text = MutableLiveData<String>().apply {
        value = ""
    }
    var text: LiveData<String> = _text
}