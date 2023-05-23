package com.example.healthapp.ui.gonio

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.healthapp.databinding.FragmentGonioBinding
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.healthapp.DataModel
import com.example.healthapp.HistoryDataModel
import com.example.healthapp.Patient
import com.example.healthapp.R
import kotlin.math.*
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import java.io.File
import java.util.Calendar
import kotlin.text.Typography.degree

/* Класс предназачен для выполнения работы
с фрагментом измерения угла. Работа с
сенсорами выполняется здесь. */
@Suppress("DEPRECATION")
class GonioFragment : Fragment() {

    /* Объявление классов построения графического интерфейса. */
    private var _binding: FragmentGonioBinding? = null
    private val binding get() = _binding!!

    private lateinit var sensorManager: SensorManager
    private var degree: Float? = 0F // Значение текущего угла
    private var resultStart: Float? = 0F // Начальное значение угла
    private var resultFinish: Float? = 0F // Конечное значение
    private val mapper = jacksonObjectMapper() // Маппер для JSON
    /* Объявление классов данных */
    private val dataModel: DataModel by activityViewModels()
    private val historyDataModel:
            HistoryDataModel by activityViewModels()
    /* Массивы значений, преобразованные в матрицы */
    private var magnetic = FloatArray(9)
    private var gravity = FloatArray(9)
    /* Массивы значений, получаемые с сенсоров */
    private var accrs = FloatArray(3)
    private var magf = FloatArray(3)
    /* Массив углов наклона в трех проекциях */
    private var values = FloatArray(3)


    /* Конструктор графического интерфейса фрагмента */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val gonioViewModel =
            ViewModelProvider(this)
                .get(GonioViewModel::class.java) // Подключение viewModel


        /* Добавление графических элементов в корневой элемент */
        _binding = FragmentGonioBinding.inflate(inflater,
            container, false)
        val root: View = binding.root

        /* Получение доступа к акселерометру и датчику магнитного поля */
        sensorManager = activity?.getSystemService(Context
            .SENSOR_SERVICE) as SensorManager
        val sensor = sensorManager
            .getDefaultSensor(Sensor
                .TYPE_ACCELEROMETER) //акселерометр
        val sensor2 = sensorManager
            .getDefaultSensor(Sensor
                .TYPE_MAGNETIC_FIELD) //датчик магнитного поля

        /* Обработчик событий, отслеживающий работу сенсоров */
        val sListener = object : SensorEventListener {
            /* Функция обрабатывает получаемые значения с сенсоров
            * event - любое изменение в показаниях сенсоров */
            override fun onSensorChanged(event: SensorEvent?) {
                when (event?.sensor?.type) {
                    /* Запись значений в массивы */
                    Sensor.TYPE_ACCELEROMETER -> accrs =
                        event.values.clone()
                    Sensor.TYPE_MAGNETIC_FIELD -> magf =
                        event.values.clone()
                }

                /* Функция получения ориентации устройства,
                основанного на матрице вращения
                * gravity - матрица вращения
                * magnetic - матрица наклона
                * accrs - значения с акселерометра
                * magf - значения с магнитометра */
                SensorManager.getRotationMatrix(gravity, magnetic,
                    accrs, magf)

                /* Функция преобразования матрицы вращения в массив
                * значений углов наклона в трех плоскостях,
                * выраженных в радианах
                * gravity - матрица вращения
                * values - массив значений углов */
                SensorManager.getOrientation(gravity, values)

                degree = values[2] * 57.2958f // Перевод из радиан в градусы
            }

            /* Функция обработки изменения точности сенсоров. */
            override fun onAccuracyChanged(p0: Sensor?, p1: Int) {}
        }
        /* Активация работы сенсоров и считывания их значений */
        sensorManager.registerListener(sListener, sensor,
            SensorManager.SENSOR_DELAY_NORMAL)
        sensorManager.registerListener(sListener, sensor2,
            SensorManager.SENSOR_DELAY_NORMAL)

        /* Инициализация кнопки начала измерений разгибания
        * и обработчика ее события. */
        val startStraighteningBtn: Button = binding
            .buttonStartStraightening
        startStraighteningBtn.setOnClickListener{
            resultStart = degree // Получение начального значения
            binding.straightening = "0"
        }

        /* Инициализация кнопки завершения измерений разгибания
        * и обработчика ее события. */
        val finishStraighteningBtn: Button = binding
            .buttonFinishStraightening
        finishStraighteningBtn.setOnClickListener {
            resultFinish = degree// Получение конечного значения
            /* Вычисление угла наклона устройства */
            val result = convert(resultStart, resultFinish)
            binding.straightening = result.toString()
        }

        /* Инициализация кнопки начала измерений сгибания
        * и обработчика ее события. */
        val startBendingBtn: Button = binding.buttonStartBending
        startBendingBtn.setOnClickListener{
            resultStart = degree// Получение начального значения
            binding.bending = "0"
        }

        /* Инициализация кнопки завершения измерений сгибания
        * и обработчика ее события. */
        val finishBendingBtn: Button = binding.buttonFinishBending
        finishBendingBtn.setOnClickListener {
            resultFinish = degree// Получение конечного значения
            /* Вычисление угла наклона устройства */
            val result = convert(resultStart, resultFinish)
            binding.bending = result.toString()
        }

        /* Инициализация кнопки отправки данных
        * и обработчика ее события. */
        val sendDataBtn: Button = binding.buttonSendData
        sendDataBtn.setOnClickListener {
            /* Деактивация работы сенсоров и считывания их значений */
            sensorManager.unregisterListener(sListener, sensor)
            sensorManager.unregisterListener(sListener, sensor2)
            /* Сохранение результатов измерений */
            loadData()
            /* Подготовка JSON строки */
            sendJSON()
            /* Завершение цикла жизни фрагмента и возврат в начало */
            findNavController().popBackStack(R.id.nav_start, false)
        }

        return root
    }

    /* Функция подготовки JSON запроса для отправки на сервер */
    private fun sendJSON(){
        //val card = dataModel.cardNumber.value!! // Карта пациента
        val card = "000001"
        val elbowKnee: String = dataModel.elbowKnee.value!! // Тип сустава
        val leftRight: String = dataModel.leftRight.value!! // Левая или правая
        val flex: Double = dataModel.flexionAngle.value!! // Угол разгибания
        val bend: Double = dataModel.extensionAngle.value!! // Угол сгибания
        val countBend: Int = dataModel.countBend.value!! // Количество движений
        val dizz: Boolean = dataModel.dizziness.value!! //стереть
        val state: Int = dataModel.state.value!! // Самочувствие пациента
        var distance: Int = dataModel.distance.value!! // Пройденное расстояние
        val date = getTime() // Получение даты проведения измерений
        var patientList: Patient // Инициализация объекта данных
        /* Условие если пациент измерял колено, то
        * отправить пройденное расстояние, иначе null */
        if (elbowKnee == "knee"){
            patientList = Patient(card, elbowKnee, leftRight, flex,
                bend, countBend, dizz, state, null, date)
        }
        else{
            patientList = Patient(card, elbowKnee, leftRight, flex,
                bend, countBend, dizz, state, distance, date)
        }
        /* Формирование строки JSON из объекта пациента */
        val jsonArray = mapper.writeValueAsString(patientList)

        /* Сохранение информации о проведенном измерении */
        saveHistory(patientList)

    }

    /* Функция сохранения отчета об измерениях.
    * patientList - объект с данными об измерении */
    private fun saveHistory(patientList: Patient){
        val historyString =
            "--------------------------------------\n " +
                    "${patientList.date}\n" + "Сустав: " +
                "${patientList.leftRight} ${patientList.elbowKnee}\n" +
                "Количество движений: ${patientList.countBend}\n" +
                "Самочувствие: ${patientList.state}\n" +
                "Пройденное расстояние: ${patientList.distance}\n" +
                "Углы измерений: ${patientList.flexionAngle}" +
                " и ${patientList.extensionAngle}\n"
        /* Добавление записи в историю.
        * Если записей не было, то просто запись,
        * если были, то добавить в начало */
        if(historyDataModel.history.value == null){
            historyDataModel.history.value = historyString
        }
        else {
            val newHistory =
                historyString + historyDataModel.history.value
            historyDataModel.history.value = newHistory
        }
    }

    /* Функция получения значения текущего времени и даты.
    * Возвращает шаблонную строку даты */
    private fun getTime(): String{
        val currentDate = Calendar.getInstance()
        val year = currentDate.get(Calendar.YEAR)
        val month = currentDate.get(Calendar.MONTH)
        val day = currentDate.get(Calendar.DAY_OF_MONTH)
        val hour = currentDate.get(Calendar.HOUR_OF_DAY)
        val minute = currentDate.get(Calendar.MINUTE)
        val curTime = "$hour:$minute - $day.$month.$year"
        return curTime
    }

    /* Функция обработки конечного угла наклона
    * resultFirst - значение угла в начале измерений
    * resultFinish - значение угла в конце измерений */
    private fun convert(resultFirst: Float?,
                        resultFinish: Float?): Double?{
        val first: Float = resultFirst!!
        val second: Float = resultFinish!!
        var result = 0F

        if (second > first){
            result = second - first
        }
        else{
            result = 360F - first + second
        }

        var res = result?.toDouble()
        if (res != null) {
            res = (res * 100).roundToInt() / 100.0
        }

        return res
    }

    /* Функция сохранения результатов измерений
    * в класс данных */
    private fun loadData(){
        val firstMeasure = binding.straightening
        val secondMeasure = binding.bending
        dataModel.extensionAngle.value = firstMeasure?.toDouble()
        dataModel.flexionAngle.value = secondMeasure?.toDouble()
    }

    /* Функция очистки конструктора фрагмента */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}