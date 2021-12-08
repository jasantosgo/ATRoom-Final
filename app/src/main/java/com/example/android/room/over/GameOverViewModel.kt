package com.example.android.room.over

import androidx.lifecycle.ViewModel

class GameOverViewModel(
   aciertos : Int,
   preguntas: Int,
   puntos: Int
) : ViewModel() {

    private var _numAciertos = aciertos
    var numAciertos = _numAciertos
        get() = _numAciertos
    private var _numPreguntas = preguntas
    var numPreguntas = _numPreguntas
        get() = _numPreguntas
    private var _puntuacion = puntos
    var puntuacion = _puntuacion
        get() = _puntuacion

}