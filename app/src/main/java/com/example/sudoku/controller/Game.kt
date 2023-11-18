package com.example.sudoku.controller

import com.example.sudoku.databinding.ActivityMainBinding
import com.example.sudoku.model.Board
import com.example.sudoku.model.MusicPlayer
import com.example.sudoku.model.Sounds
import com.example.sudoku.view.InputView

class Game(val binding: ActivityMainBinding) {
    fun start() {
        MusicPlayer(Sounds().soundPool, binding).playBackgroundMusic(Sounds().music)
        Board(binding).boardInitialize()
        InputView(binding).button()
    }
}