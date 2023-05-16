package com.example.healthapp.ui.about

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AboutViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Здесь находится информация о разработчике ПО.\n\n\n Разработчик: Кузнецов А.Д."
    }
    val text: LiveData<String> = _text
}