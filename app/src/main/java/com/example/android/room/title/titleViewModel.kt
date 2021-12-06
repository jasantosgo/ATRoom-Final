package com.example.android.room.title

import androidx.lifecycle.ViewModel

class titleViewModel : ViewModel(){

    private var _nivel : Int = -1
    var nivel : Int = _nivel
        get() = _nivel

    fun estableceNivel(nivel: Int) {
        _nivel = nivel
    }
}