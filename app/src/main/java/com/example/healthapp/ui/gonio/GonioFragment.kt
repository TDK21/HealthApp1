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
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.healthapp.DataModel
import com.example.healthapp.Patient
import com.example.healthapp.R
import kotlin.math.*
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import java.io.File
import java.util.Calendar

@Suppress("DEPRECATION")
class GonioFragment : Fragment() {

    private var _binding: FragmentGonioBinding? = null
    private val binding get() = _binding!!
    private lateinit var sensorManager: SensorManager
    private var sData: Float? = 0F
    private var resultStart: Float? = 0F
    private var resultFinish: Float? = 0F
    private val dataModel: DataModel by activityViewModels()
    private lateinit var jsString: String
    private val mapper = jacksonObjectMapper()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val gonioViewModel =
            ViewModelProvider(this).get(GonioViewModel::class.java)


        _binding = FragmentGonioBinding.inflate(inflater, container, false)
        val root: View = binding.root


        val tvSensor = binding.textValues
        sensorManager = activity?.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION)
        val sListener = object : SensorEventListener{
            override fun onSensorChanged(p0: SensorEvent?) {
                val value = p0?.values
                sData = value?.get(0)
                tvSensor.text = sData.toString()
            }

            override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

            }

        }
        sensorManager.registerListener(sListener, sensor, SensorManager.SENSOR_DELAY_NORMAL)

        val startStraighteningBtn: Button = binding.buttonStartStraightening

        startStraighteningBtn.setOnClickListener{
            resultStart = sData
            binding.straightening = "0"
        }

        val finishStraighteningBtn: Button = binding.buttonFinishStraightening

        finishStraighteningBtn.setOnClickListener {
            resultFinish = sData
            val result = convert(resultStart, resultFinish)
            binding.straightening = result.toString()
        }

        val startBendingBtn: Button = binding.buttonStartBending

        startBendingBtn.setOnClickListener{
            resultStart = sData
            binding.bending = "0"
        }

        val finishBendingBtn: Button = binding.buttonFinishBending

        finishBendingBtn.setOnClickListener {
            resultFinish = sData
            val result = convert(resultStart, resultFinish)
            binding.bending = result.toString()
        }

        val sendDataBtn: Button = binding.buttonSendData

        sendDataBtn.setOnClickListener {
            sensorManager.unregisterListener(sListener, sensor)
            loadData()
            sendJSON()
            //destroyfragment
            findNavController().popBackStack(R.id.nav_start, false)
        }

        return root
    }

    private fun sendJSON(){
        //val card = dataModel.cardNumber.value!!
        val card = "000001"
        val elbowKnee: String = dataModel.elbowKnee.value!!
        val leftRight: String = dataModel.leftRight.value!!
        val flex: Double = dataModel.flexionAngle.value!!
        val bend: Double = dataModel.extensionAngle.value!!
        val countBend: Int = dataModel.countBend.value!!
        val dizz: Boolean = dataModel.dizziness.value!!
        val state: Int = dataModel.state.value!!
        var distance: Int = dataModel.distance.value!!
        var patientList = Patient(card, elbowKnee, leftRight, flex, bend, countBend, dizz, state, distance)
        if (elbowKnee == "knee"){
            patientList = Patient(card, elbowKnee, leftRight, flex, bend, countBend, dizz, state, null)
        }
        getTime()
        val jsonArray = mapper.writeValueAsString(patientList)
        Log.d("MyLog", jsonArray)
    }


    private fun getTime(){
        val currentDate = Calendar.getInstance()
        val year = currentDate.get(Calendar.YEAR)
        val month = currentDate.get(Calendar.MONTH)
        val day = currentDate.get(Calendar.DAY_OF_MONTH)
        val hour = currentDate.get(Calendar.HOUR_OF_DAY)
        val minute = currentDate.get(Calendar.MINUTE)
        Log.d("MyLog", "$hour:$minute - $day.$month.$year")
    }
    private fun convert(resultFirst: Float?, resultFinish: Float?): Double?{
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

    private fun loadData(){
        val firstMeasure = binding.straightening
        val secondMeasure = binding.bending
        dataModel.extensionAngle.value = firstMeasure?.toDouble()
        dataModel.flexionAngle.value = secondMeasure?.toDouble()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}