package com.example.healthapp.ui.about

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AboutViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Данное приложение разработано для пациентов, проходящих реабилитацию " +
                "после эндопротезирования сустава.\n\n\nПриложение предназначено для проведения измерений" +
                "подвижности сустава пациентом после эндопротезирования.\n\n\n Разработчик: Кузнецов А.Д."
    }
    val text: LiveData<String> = _text
}