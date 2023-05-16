package com.example.healthapp.data

import com.example.healthapp.PatientInfo
import com.example.healthapp.data.model.LoggedInUser
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import java.io.IOException

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource {

    val mapper = jacksonObjectMapper()

    fun login(username: String, password: String): Result<LoggedInUser> {
        try {
            // TODO: handle loggedInUser authentication
            //Отправить запрос на сервер
            //Получить ответ JSON который содержит фио и карту
            //val jsonString = """{"namePatient":"Patient","card":"000000"}"""
            //val userFromJson = mapper.readValue<PatientInfo>(jsonString)
            //Вытащить значения из JSON и отправить в приложение
            val nameUser = "Фамилия Имя Отчество"
            val numberCard = "000001"
            val fakeUser = LoggedInUser(java.util.UUID.randomUUID().toString(), nameUser, numberCard)
            return Result.Success(fakeUser)
        } catch (e: Throwable) {
            return Result.Error(IOException("Error logging in", e))
        }
    }

    fun logout() {
        // TODO: revoke authentication
    }
}