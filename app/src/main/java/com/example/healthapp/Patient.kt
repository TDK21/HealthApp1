package com.example.healthapp


data class Patient(
    val cardNumber: String,
    val elbowKnee: Int,
    val leftRight: Int,
    val flexionAngle: Double,
    val extensionAngle: Double,
    val countBend: Int,
    val dizziness: Boolean,
    val state: Int,
    val distance: Int?
)
