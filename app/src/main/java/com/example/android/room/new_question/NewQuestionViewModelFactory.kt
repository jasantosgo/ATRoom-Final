package com.example.android.room.new_question

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android.room.database.QuestionDAO

class NewQuestionViewModelFactory(
    private val database: QuestionDAO,
    private val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewQuestionViewModel::class.java)) {
            return NewQuestionViewModel(database, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}