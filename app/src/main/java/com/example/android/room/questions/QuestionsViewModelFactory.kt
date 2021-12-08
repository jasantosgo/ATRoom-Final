package com.example.android.room.questions

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android.room.database.QuestionDAO

class QuestionsViewModelFactory(
    private val database: QuestionDAO,
    private val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(QuestionsViewModel::class.java)) {
            return QuestionsViewModel(database, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}