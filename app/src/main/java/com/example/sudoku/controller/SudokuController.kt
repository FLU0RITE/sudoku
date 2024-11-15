package com.example.sudoku.controller

import com.example.sudoku.databinding.ActivityMainBinding
import com.example.sudoku.model.Board
import com.example.sudoku.model.GameButtons
import com.example.sudoku.model.InputButtons
import com.example.sudoku.util.MusicPlayer
import com.example.sudoku.util.Sounds
import com.example.sudoku.view.InputView

class SudokuController(private val binding: ActivityMainBinding) {
    private val board = Board(binding)
    private val sounds = Sounds()
    private val musicPlayer = MusicPlayer(sounds.soundPool, binding)
    fun start() {
        musicPlayer.playBackgroundMusic(sounds.music)
        board.boardInitialize()
        InputView(board,sounds,binding).button()
    }
}