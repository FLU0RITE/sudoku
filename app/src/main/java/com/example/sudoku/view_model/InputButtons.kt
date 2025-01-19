package com.example.sudoku.view_model

import MusicPlayer
import android.annotation.SuppressLint
import android.graphics.Color
import android.widget.Button
import android.widget.Toast
import com.example.sudoku.R
import com.example.sudoku.databinding.ActivityMainBinding
import com.example.sudoku.model.Board
import com.example.sudoku.util.App

import com.example.sudoku.util.Sounds

class InputButtons (private var board: Board, private val binding: ActivityMainBinding,private val musicPlayer: MusicPlayer){
    @SuppressLint("ResourceAsColor")
    fun numberButton(selectedButton: Button, row: Int, col: Int, number: Int) : Board {
        selectedButton.text = "$number"
        board.board[row][col] = number
        if (board.originBoard[row][col] != number) {
            board.mistakeCount += 1
            binding.mistake.text = "${board.mistakeCount}"
            selectedButton.setTextColor(Color.RED)
            Toast.makeText(App.context(), "${board.mistakeCount}회 실수", Toast.LENGTH_SHORT).show()
        } else {
            selectedButton.setTextColor(Color.BLUE)
        }
        Board(binding,musicPlayer).blockColor(selectedButton, row, col)
        if (board.modifyFlag == 1) {
            board.modifyFlag = 0
            board.modifiedButton.remove(board.modifiedButton[0])
        }
        return board
    }

    fun selectButton(selectedButton: Button, row: Int, col: Int) : Board {
        selectedButton.setOnClickListener {
            musicPlayer.playEffectClockSound(Sounds().clock)
            if (board.modifyStatusA[row][col] == 1) {
                if (board.modifyFlag == 0) {
                    selectedButton.setBackgroundResource(R.drawable.selected_box)
                    board.modifiedButton.add(selectedButton)
                    board.mRow = row
                    board.mCol = col
                    board.modifyFlag = 1
                } else {
                    Board(binding,musicPlayer).blockColor(board.modifiedButton[0], board.mRow, board.mCol)
                    selectedButton.setBackgroundResource(R.drawable.selected_box)
                    board.modifiedButton.remove(board.modifiedButton[0])
                    board.modifiedButton.add(selectedButton)
                    board.mRow = row
                    board.mCol = col
                    board.modifyFlag = 1
                }
                binding.one.setOnClickListener {
                    musicPlayer.playEffectClockSound(Sounds().clock)
                    numberButton(selectedButton, row, col, 1)
                }
                binding.two.setOnClickListener {
                    musicPlayer.playEffectClockSound(Sounds().clock)
                    numberButton(selectedButton, row, col, 2)
                }
                binding.three.setOnClickListener {
                    musicPlayer.playEffectClockSound(Sounds().clock)
                    numberButton(selectedButton, row, col, 3)
                }
                binding.four.setOnClickListener {
                    musicPlayer.playEffectClockSound(Sounds().clock)
                    numberButton(selectedButton, row, col, 4)
                }
                binding.five.setOnClickListener {
                    musicPlayer.playEffectClockSound(Sounds().clock)
                    numberButton(selectedButton, row, col, 5)
                }
                binding.six.setOnClickListener {
                    musicPlayer.playEffectClockSound(Sounds().clock)
                    numberButton(selectedButton, row, col, 6)
                }
                binding.seven.setOnClickListener {
                    musicPlayer.playEffectClockSound(Sounds().clock)
                    numberButton(selectedButton, row, col, 7)
                }
                binding.eight.setOnClickListener {
                    musicPlayer.playEffectClockSound(Sounds().clock)
                    numberButton(selectedButton, row, col, 8)
                }
                binding.nine.setOnClickListener {
                    musicPlayer.playEffectClockSound(Sounds().clock)
                    numberButton(selectedButton, row, col, 9)
                }
                binding.deleteB.setOnClickListener {
                    musicPlayer.playEffectSound(Sounds().sound3)
                    board = GameButtons(board,binding,musicPlayer).deleteButton(selectedButton, row, col)
                }
                binding.hintB.setOnClickListener {
                    musicPlayer.playEffectSound(Sounds().sound4)
                    board = GameButtons(board,binding,musicPlayer).hintButton(selectedButton, row, col)
                }
            }
        }
        return board
    }
}