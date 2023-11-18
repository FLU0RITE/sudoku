package com.example.sudoku.view

import android.content.Context
import com.example.sudoku.model.Discrimination
import com.example.sudoku.model.Board
import com.example.sudoku.model.MusicPlayer
import com.example.sudoku.model.Sounds

class InputView() {
    lateinit var binding: com.example.sudoku.databinding.ActivityMainBinding
    fun button(){
        binding.newB.setOnClickListener() {
            MusicPlayer(Sounds().soundPool).playEffectSound(Sounds().sound1)
            Board().boardInitialize()
        }
        binding.completeB.setOnClickListener {
            MusicPlayer(Sounds().soundPool).playEffectSound(Sounds().sound2)
            Discrimination().discriminate()
        }
        binding.musicButton.setOnClickListener {
            MusicPlayer(Sounds().soundPool).musicOnOff()
        }
        binding.soundButton.setOnClickListener {
            MusicPlayer(Sounds().soundPool).effetOnOff()
        }
    }
}