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

package com.example.android.room.over

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.core.app.ActivityCompat.finishAffinity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.android.room.won.GameWonFragmentArgs
import com.example.android.room.R
import com.example.android.room.databinding.FragmentGameOverBinding
import kotlin.system.exitProcess

class GameOverFragment : Fragment() {

    private lateinit var gameOverVM: GameOverViewModel
    private lateinit var gameOverVMF: GameOverViewModelFactory

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val binding: FragmentGameOverBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_game_over, container, false)

        val args = GameOverFragmentArgs.fromBundle(requireArguments())
        gameOverVMF = GameOverViewModelFactory(args.numAciertos,args.numPreguntas,args.puntuacion)
        gameOverVM = ViewModelProvider(this,gameOverVMF).get(GameOverViewModel::class.java)

        binding.tvPuntuacionOver.text = resources.getString(R.string.total_score,gameOverVM.puntuacion,gameOverVM.numAciertos,gameOverVM.numPreguntas)

        binding.tryAgainButton.setOnClickListener {
            var nivel = 0
            when(gameOverVM.numPreguntas) {
                2 -> nivel = 0
                4 -> nivel = 1
                6 -> nivel = 2
            }
            it.findNavController().navigate(
                GameOverFragmentDirections.actionGameOverFragmentToGameFragment(
                    nivel
                )
            )
        }

        binding.btnSalirOver.setOnClickListener {
            val intent = Intent(Intent.ACTION_MAIN)
            intent.addCategory(Intent.CATEGORY_HOME)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            activity?.let {
                it.finish()
                exitProcess(0)
            }
        }

        setHasOptionsMenu(true)

        return binding.root
    }

    private fun getShareIntent() : Intent {
        val args = GameOverFragmentArgs.fromBundle(requireArguments())
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.setType("text/plain")
            .putExtra(Intent.EXTRA_TEXT, resources.getString(R.string.total_score,args.puntuacion,args.numAciertos,args.numPreguntas))
        return shareIntent
    }

    // Starting an Activity with our new Intent
    private fun shareSuccess() {
        startActivity(getShareIntent())
    }

    // Showing the Share Menu Item Dynamically
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.winner_menu, menu)
        if(getShareIntent().resolveActivity(requireActivity().packageManager)==null){
            menu.findItem(R.id.share).isVisible = false
        }
    }

    // Sharing from the Menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.share -> shareSuccess()
        }
        return super.onOptionsItemSelected(item)
    }

}
