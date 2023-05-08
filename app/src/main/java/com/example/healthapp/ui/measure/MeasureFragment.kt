package com.example.healthapp.ui.measure

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.healthapp.DataModel
import com.example.healthapp.NavActivity
import com.example.healthapp.R
import com.example.healthapp.databinding.FragmentMeasureBinding
import com.example.healthapp.ui.gonio.GonioFragment
import com.example.healthapp.ui.gonio.GonioViewModel


class MeasureFragment : Fragment() {

    private var _binding: FragmentMeasureBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val dataModel: DataModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val measureViewModel =
            ViewModelProvider(this).get(MeasureViewModel::class.java)

        _binding = FragmentMeasureBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val btnMeasureBt: Button = binding.measureBtn


        btnMeasureBt.setOnClickListener {
            humanPart()
            headAche()
            feelings()
            distance()
            if (!isFieldEmpty()) {
                movements()
                findNavController().navigate(R.id.nav_gonio)
            }
        }

        return root
    }


    private fun isFieldEmpty(): Boolean{
        binding.apply {
            if (editNumMov.text.isNullOrEmpty()) editNumMov.error = "Input value"
            return editNumMov.text.isNullOrEmpty()
        }
    }

    private fun feelings(){
        val selectedFeelings = binding.spinner3.selectedItemId
        dataModel.state.value = selectedFeelings.toInt()

    }

    private fun headAche(){
        val selectedHeadAche = binding.spinner2.selectedItem.toString()
        if (selectedHeadAche == "Yes"){
            dataModel.dizziness.value = true
        }
        else{
            dataModel.dizziness.value = false
        }
    }

    private fun distance(){
        val selectedDistance = binding.editTextDistance.text.toString()
        dataModel.distance.value = selectedDistance.toInt()
    }

    private fun movements(){
        val selectedNumberOfMov = binding.editNumMov.text.toString()
        dataModel.countBend.value = selectedNumberOfMov.toInt()
    }

    private fun humanPart(){
        val selectedHumanPart = binding.spinner.selectedItem.toString()
        val parts = selectedHumanPart.split("\\s".toRegex()).toTypedArray()
        dataModel.leftRight.value = parts[0]
        dataModel.elbowKnee.value = parts[1]

       /* if (parts[0] == "Right"){
            dataModel.leftRight.value = 1
        }
        else{
            dataModel.leftRight.value = 0
        }

        if (parts[1] == "Elbow"){
            dataModel.elbowKnee.value = 0
        }
        else{
            dataModel.elbowKnee.value = 1
        }*/
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}