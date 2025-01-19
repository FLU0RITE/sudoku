package com.example.sudoku.view_model

import MusicPlayer
import com.example.sudoku.R
import com.example.sudoku.databinding.ActivityMainBinding
import com.example.sudoku.model.Board
import com.example.sudoku.util.Sounds

class InputView(private val board: Board,private val sounds: Sounds, private val binding: ActivityMainBinding,private val musicPlayer: MusicPlayer) {

    fun button(){
        binding.newB.setOnClickListener() {
            musicPlayer.playEffectSound(sounds.sound1)
            board.boardInitialize(board.selectedDifficulty?:"쉬움")
        }
        binding.musicButton.setOnClickListener {
            musicPlayer.musicOnOff(binding.root.context, R.raw.background_piano)
        }
        binding.soundButton.setOnClickListener {
            musicPlayer.effectOnOff()
        }
        binding.completeB.setOnClickListener {
            musicPlayer.playEffectSound(Sounds().sound2)
            if (board.mistakeCount >= 3) {
                OutputView().threeOverMistakeFail()
            } else {
                val successCount = (0..8).firstOrNull { i ->
                    (0..8).any { j ->
                        if (board.board[i][j] != board.originBoard[i][j]) {
                            OutputView().fail()
                            return@firstOrNull true
                        }
                        false
                    }
                }

                if (successCount == null) {
                    OutputView().success()
                    board.boardInitialize( board.selectedDifficulty?:"쉬움")
                }
            }
        }
    }

}