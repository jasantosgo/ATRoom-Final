package com.example.android.room.questions

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.android.room.R
import com.example.android.room.database.QuestionDB
import com.example.android.room.databinding.FragmentGameBinding
import com.example.android.room.databinding.FragmentQuestionsBinding
import com.example.android.room.game.GameViewModel
import com.example.android.room.game.GameViewModelFactory

class QuestionsFragment : Fragment() {

    private lateinit var questionsVM: QuestionsViewModel
    private lateinit var questionsVMF: QuestionsViewModelFactory


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = DataBindingUtil.inflate<FragmentQuestionsBinding>(
            inflater, R.layout.fragment_questions, container, false)

        val application = requireNotNull(this.activity).application
        val dataSource = QuestionDB.getInstance(application).questionDAO
        questionsVMF = QuestionsViewModelFactory(dataSource,application)
        questionsVM = ViewModelProvider(this,questionsVMF).get(QuestionsViewModel::class.java)

        binding.questionsVM = questionsVM
        binding.lifecycleOwner = viewLifecycleOwner

        binding.btnNewQuestion.setOnClickListener {
            findNavController().navigate(QuestionsFragmentDirections.actionQuestionsFragmentToNewQuestionFragment())
        }

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        return binding.root
    }

}