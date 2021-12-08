package com.example.android.room.new_question

import android.app.Application
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
import androidx.navigation.findNavController
import com.example.android.room.R
import com.example.android.room.database.Question
import com.example.android.room.database.QuestionDAO
import formatQuestions
import kotlinx.coroutines.launch

class NewQuestionViewModel(
   val database: QuestionDAO,
   application: Application
) : AndroidViewModel(application) {

    private val _newQuestion = MutableLiveData<Boolean>()
    val newQuestion: LiveData<Boolean>
        get() = _newQuestion

    init {
        _newQuestion.value = false
    }

    fun insertaPregunta(enunciado: String, correcta: String, segunda: String, tercera: String, cuarta: String, pista: String) {
        val question = Question(text=enunciado,answer_one = correcta,answer_two = segunda,answer_three = tercera,answer_four = cuarta,hint=pista)
        viewModelScope.launch {
            newQuestion(question)
            _newQuestion.value = true
        }
    }

    suspend fun newQuestion(question: Question) {
        database.insertQuestion(question)
    }

    fun onNewQuestionComplete() {
        _newQuestion.value = false
    }
}