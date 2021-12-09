/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.room.game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.android.room.R
import com.example.android.room.database.QuestionDB
import com.example.android.room.databinding.FragmentGameBinding
import com.google.android.material.snackbar.Snackbar

class GameFragment : Fragment() {

    private lateinit var gameVM: GameViewModel
    private lateinit var gameVMF: GameViewModelFactory

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentGameBinding>(
            inflater, R.layout.fragment_game, container, false)

        val application = requireNotNull(this.activity).application
        //obtener el DAO de la base de datos.
        val dataSource = QuestionDB.getInstance(application).questionDAO
        //Creación de la fábrica y obtención del ViewModel. Se le pasa el nivel, DAO y el contexto de aplicación para la BD.
        gameVMF = GameViewModelFactory(GameFragmentArgs.fromBundle(requireArguments()).nivel,dataSource,application)
        gameVM = ViewModelProvider(this, gameVMF).get(GameViewModel::class.java)

        // Enlazar el XML al ViewModel.
        binding.gameVM = gameVM
        binding.lifecycleOwner = viewLifecycleOwner

        //inicializar el label con el nivel.
        binding.tvLevel.apply {
            text= resources.getString(R.string.nivel_puntuacion,resources.getStringArray(R.array.niveles)[gameVM.nivel])
        }

        //Observamos el Livedata de puntuación para actualizar el título.
        gameVM.score.observe(viewLifecycleOwner, Observer { score ->
            (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.title_android_trivia_question, gameVM.questionIndex + 1, gameVM.numQuestions, score)
        })

        binding.btnHint.setOnClickListener {
            mostrarPista()
        }

        // Set the onClickListener for the submitButton
        binding.submitButton.setOnClickListener @Suppress("UNUSED_ANONYMOUS_PARAMETER")
        {
            val checkedId = binding.questionRadioGroup.checkedRadioButtonId
            // Do nothing if nothing is checked (id == -1)
            if (-1 != checkedId) {
                var answerIndex = 0
                when (checkedId) {
                    R.id.secondAnswerRadioButton -> answerIndex = 1
                    R.id.thirdAnswerRadioButton -> answerIndex = 2
                    R.id.fourthAnswerRadioButton -> answerIndex = 3
                }
                //vamos a comprobar el acierto y actuar en consecuencia. En ese metodo se pueden activar
                //las banderas de "gameWon" y de "gameOver"
                gameVM.comprobarAcierto(answerIndex)
            }
        }

        //bandera para indicar que se ha perdido el juego.
        gameVM.gameOver.observe(viewLifecycleOwner, Observer { haPerdido ->
            if(haPerdido) {
                findNavController().navigate(
                    GameFragmentDirections.actionGameFragmentToGameOverFragment(
                        gameVM.questionIndex,
                        gameVM.numQuestions,
                        (gameVM.score.value)?:0
                    )
                )
                gameVM.onGameOverComlete()
            }
        })

        //Se observa el flag "gameWon" que se levanta cuando la pregunta actual es igual al numero de preguntas que se deben responder.
        gameVM.gameWon.observe(viewLifecycleOwner, Observer { haGanado ->
            if(haGanado) {
                this.findNavController().navigate(
                    GameFragmentDirections.actionGameFragmentToGameWonFragment(
                        gameVM.questionIndex,
                        gameVM.numQuestions,
                        (gameVM.score.value)?:0
                    )
                )
                gameVM.onGameWonComplete()
            }
        })

        gameVM.cargaInicial.observe(viewLifecycleOwner, Observer { cargaInicialBD ->
            if(cargaInicialBD) {
                Snackbar.make(requireContext(),requireView(),getString(R.string.carga_db),Snackbar.LENGTH_LONG).show()
                this.findNavController().popBackStack()
                gameVM.cargaInicialComplete()
            }
        })

        return binding.root
    }

    private fun mostrarPista() {
        Snackbar.make(requireContext(),requireView(),gameVM.hint,Snackbar.LENGTH_LONG).show()
        gameVM.pistaUsada()
    }
}
