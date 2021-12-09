package com.example.android.room.new_question

import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.android.room.R
import com.example.android.room.database.QuestionDB
import com.example.android.room.databinding.FragmentGameBinding
import com.example.android.room.databinding.FragmentNewQuestionBinding
import com.example.android.room.databinding.FragmentQuestionsBinding
import com.example.android.room.game.GameViewModel
import com.example.android.room.game.GameViewModelFactory
import com.google.android.material.snackbar.Snackbar
import androidx.core.content.ContextCompat.getSystemService




class NewQuestionFragment : Fragment() {

    private lateinit var newQVM: NewQuestionViewModel
    private lateinit var newQVMF: NewQuestionViewModelFactory


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = DataBindingUtil.inflate<FragmentNewQuestionBinding>(
            inflater, R.layout.fragment_new_question, container, false)

        val application = requireNotNull(this.activity).application
        val dataSource = QuestionDB.getInstance(application).questionDAO
        newQVMF = NewQuestionViewModelFactory(dataSource,application)
        newQVM = ViewModelProvider(this,newQVMF).get(NewQuestionViewModel::class.java)

        binding.newQVM = newQVM
        binding.lifecycleOwner = viewLifecycleOwner

        binding.btnInsQuest.setOnClickListener {
            val enunciado = binding.etEnunciado.text.toString()
            val correcta = binding.etCorrectAns.text.toString()
            val segunda = binding.etAnsTwo.text.toString()
            val tercera = binding.etAnsThree.text.toString()
            val cuarta = binding.etAnsFour.text.toString()
            val pista = binding.etHint.text.toString()
            if(enunciado.isNotBlank() &&
                correcta.isNotBlank() &&
                segunda.isNotBlank() &&
                tercera.isNotBlank() &&
                cuarta.isNotBlank() &&
                pista.isNotBlank()) {
                    newQVM.insertaPregunta(enunciado,correcta,segunda,tercera,cuarta,pista)
            } else {
                Snackbar.make(requireContext(),requireView(),getString(R.string.complete_fields), Snackbar.LENGTH_SHORT).show()
            }

        }

        newQVM.newQuestion.observe(viewLifecycleOwner, Observer { insertada ->
            if(insertada) {
                findNavController().popBackStack()
                hideKeyboard()
                newQVM.onNewQuestionComplete()
            }
        })

        binding.btnBackQuest.setOnClickListener {
            findNavController().popBackStack()
            hideKeyboard()
        }
        
        return binding.root
    }


    fun Fragment.hideKeyboard() {
        view?.let { activity?.hideKeyboard(it) }
    }

    fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

}