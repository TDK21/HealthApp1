package com.example.healthapp.ui.start_measure

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.navigation.fragment.findNavController
import com.example.healthapp.R
import com.example.healthapp.databinding.FragmentAboutBinding
import com.example.healthapp.databinding.FragmentStartBinding
import com.example.healthapp.ui.about.AboutViewModel

class StartFragment : Fragment() {

    private var _binding: FragmentStartBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val startViewModel =
            ViewModelProvider(this).get(StartViewModel::class.java)

        _binding = FragmentStartBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val button_start: Button = binding.buttonStart
        button_start.setOnClickListener{
            findNavController().navigate(R.id.nav_home)
        }

        val textView: TextView = binding.textStart
        startViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}