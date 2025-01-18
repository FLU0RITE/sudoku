package com.example.sudoku.view

import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView
import com.example.sudoku.databinding.ActivityMainBinding
import com.example.sudoku.model.Board
import com.example.sudoku.util.MusicPlayer
import com.example.sudoku.util.Sounds

class GameView(selectedDifficulty: String,private val binding: ActivityMainBinding) {
    private val board = Board(binding)
    private val sounds = Sounds()
    private val musicPlayer = MusicPlayer(sounds.soundPool, binding)
    @Composable
    fun play(){
        AndroidView(
            factory = { context ->
                binding.root
            }
        )
        musicPlayer.playBackgroundMusic(sounds.music)
        board.boardInitialize()
        InputView(board,sounds,binding).button()
    }
}