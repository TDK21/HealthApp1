package com.example.healthapp.ui.about

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AboutViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Here will be information about developer and app.\n\n\n Created by TDK21.\nGITHUB: TDK21"
    }
    val text: LiveData<String> = _text
}