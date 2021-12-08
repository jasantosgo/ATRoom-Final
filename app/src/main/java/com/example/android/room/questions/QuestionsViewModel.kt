package com.example.android.room.questions

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

class QuestionsViewModel(
   val database: QuestionDAO,
   application: Application
) : AndroidViewModel(application) {

    private val questions = database.selectQuestions()

    val titleQuestions = Transformations.map(questions) { questions ->
        formatQuestions(questions, application.resources)
    }

    init {
        Log.d("database",titleQuestions.value.toString())
    }

}