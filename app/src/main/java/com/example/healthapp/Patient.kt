package com.example.healthapp

import java.time.Year


data class Patient(
    val cardNumber: String,
    val elbowKnee: String,
    val leftRight: String,
    val flexionAngle: Double,
    val extensionAngle: Double,
    val countBend: Int,
    val dizziness: Boolean,
    val state: Int,
    val distance: Int?,
    val date: String?
)
