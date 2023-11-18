package com.example.sudoku.view

import com.example.sudoku.databinding.ActivityMainBinding
import com.example.sudoku.model.Board
import com.example.sudoku.model.MusicPlayer
import com.example.sudoku.model.Sounds

class InputView(val binding: ActivityMainBinding) {
    fun button(){
        binding.newB.setOnClickListener() {
            MusicPlayer(Sounds().soundPool,binding).playEffectSound(Sounds().sound1)
            Board(binding).boardInitialize()
        }
        binding.completeB.setOnClickListener {
            MusicPlayer(Sounds().soundPool,binding).playEffectSound(Sounds().sound2)
            Board(binding).discriminate()
        }
        binding.musicButton.setOnClickListener {
            MusicPlayer(Sounds().soundPool,binding).musicOnOff(Sounds().music)
        }
        binding.soundButton.setOnClickListener {
            MusicPlayer(Sounds().soundPool,binding).effectOnOff()
        }
    }
}