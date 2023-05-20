package com.example.healthapp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class HistoryDataModel: ViewModel() {

    val history: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
}