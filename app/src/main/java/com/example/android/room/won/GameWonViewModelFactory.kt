package com.example.android.room.won

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class GameWonViewModelFactory(
    private val aciertos : Int,
    private val preguntas: Int,
    private val puntos: Int
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GameWonViewModel::class.java)) {
            return GameWonViewModel(aciertos, preguntas, puntos) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}