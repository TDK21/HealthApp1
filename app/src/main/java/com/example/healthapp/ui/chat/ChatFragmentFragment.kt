package com.example.healthapp.ui.chat

import android.content.Intent
import android.net.MailTo
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.healthapp.HistoryDataModel
import com.example.healthapp.NavActivity
import com.example.healthapp.R
import com.example.healthapp.databinding.FragmentChatBinding
import com.example.healthapp.databinding.FragmentStartBinding

class ChatFragmentFragment : Fragment() {

    private var _binding: FragmentChatBinding? = null
    private val binding get() = _binding!!
    private val historyDataModel: HistoryDataModel by activityViewModels()

    private lateinit var viewModel: ChatViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProvider(this).get(ChatViewModel::class.java)
        _binding = FragmentChatBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val button_email: Button = binding.emailBtn
        button_email.setOnClickListener{
            val  intent = Intent(Intent.ACTION_SENDTO,
                Uri.fromParts("mailto", "abc@email.com", null))
            startActivity(Intent.createChooser(intent, "Send email..."))
        }

        val button_telegram: Button = binding.telegramBtn
        button_telegram.setOnClickListener{
            val  intent = Intent(Intent.ACTION_VIEW,
                Uri.parse("https://t.me/TemaDK21"))
            intent.setPackage("org.telegram.messenger")
            startActivity(intent)
        }

        val buttonWrongData: Button = binding.wrongDataBtn
        buttonWrongData.setOnClickListener {
            Toast.makeText(activity, "Сообщение об ошибке отправлено", Toast.LENGTH_LONG).show()
            saveError()
        }

        return root

    }

    private fun saveError(){
        val historyString = "Данное измерение выполнено неправильно\n"
        if(historyDataModel.history.value == null){
            historyDataModel.history.value = historyString
        }
        else {
            val newHistory = historyString + historyDataModel.history.value
            historyDataModel.history.value = newHistory
        }
    }

}