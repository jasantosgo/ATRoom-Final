package com.example.android.room.over

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class GameOverViewModelFactory(
    private val aciertos : Int,
    private val preguntas: Int,
    private val puntos: Int
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GameOverViewModel::class.java)) {
            return GameOverViewModel(aciertos, preguntas, puntos) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}