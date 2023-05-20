package com.example.healthapp.ui.instruction

import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.text.style.ImageSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.healthapp.R
import com.example.healthapp.databinding.FragmentInstructionBinding

class InstructionFragment : Fragment() {

    private var _binding: FragmentInstructionBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val instructionViewModel =
            ViewModelProvider(this).get(InstructionViewModel::class.java)

        _binding = FragmentInstructionBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.textView4.movementMethod = ScrollingMovementMethod()

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}