package com.example.android.room.game

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android.room.database.QuestionDAO
import com.example.android.room.database.QuestionDB

class GameViewModelFactory(
    private val nivel: Int,
    private val database: QuestionDAO,
    private val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GameViewModel::class.java)) {
            return GameViewModel(nivel, database, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}