package com.example.healthapp.ui.calendar

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CalendarView
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.healthapp.R
import com.example.healthapp.databinding.FragmentCalendarBinding
import com.example.healthapp.databinding.FragmentStartBinding
import com.example.healthapp.ui.start_measure.StartViewModel

class CalendarFragment : Fragment() {

    companion object {
        fun newInstance() = CalendarFragment()
    }

    private var _binding: FragmentCalendarBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: CalendarViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProvider(this).get(CalendarViewModel::class.java)
        _binding = FragmentCalendarBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val calendar = binding.calendarView
        calendar.setOnDateChangeListener(CalendarView.OnDateChangeListener{
            view, year, month, dayOfMonth ->
            val date = (dayOfMonth.toString() + "-" + (month) + "-" + year)
            //if cur day in array do next code else print free day
            if (dayOfMonth % 3 == 0){
                binding.currentDay = "Назначены замеры"
            }
            else{
                binding.currentDay = "Свободный день"
            }
        })


        return root
    }

}