package com.example.healthapp.ui.about

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AboutViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Приложение предназначено для пациентов, которые " +
                "проходят реабилитацию после операции эндопротезирования " +
                "коленного или локтевого суставов.\n\n" +
                "Основной функцией приложения является измерения угла " +
                "сгибания и разгибания данных суставов.\n\n\nРазработчик: Кузнецов А.Д."
    }
    val text: LiveData<String> = _text
}