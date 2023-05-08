package com.example.healthapp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class DataModel: ViewModel() {

    val cardNumber: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val elbowKnee: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val leftRight: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val flexionAngle: MutableLiveData<Double> by lazy {
        MutableLiveData<Double>()
    }

    val extensionAngle: MutableLiveData<Double> by lazy {
        MutableLiveData<Double>()
    }

    val countBend: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }

    val dizziness: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    val state: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }

    val distance: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }
}