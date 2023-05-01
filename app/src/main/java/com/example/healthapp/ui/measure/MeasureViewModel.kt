package com.example.healthapp.ui.measure

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class MeasureViewModel : ViewModel() {

    val jString: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
}