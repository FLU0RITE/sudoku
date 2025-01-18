package com.example.sudoku.view

import android.annotation.SuppressLint
import android.graphics.Color
import android.widget.Button
import com.example.sudoku.databinding.ActivityMainBinding
import com.example.sudoku.model.Board

class GameButtons(private val board: Board, private val binding: ActivityMainBinding) {
    @SuppressLint("ResourceAsColor")
    fun deleteButton(selectedButton: Button, row: Int, col: Int) : Board {
        selectedButton.text = ""
        board.board[row][col] = 0
        Board(binding).blockColor(selectedButton, row, col)
        if (board.modifyFlag == 1) {
            board.modifyFlag = 0
            board.modifiedButton.remove(board.modifiedButton[0])
        }
        return board
    }

    @SuppressLint("ResourceAsColor")
    fun hintButton(selectedButton: Button, row: Int, col: Int) : Board {
        selectedButton.text = "${board.originBoard[row][col]}"
        board.board[row][col] = board.originBoard[row][col]
        selectedButton.setTextColor(Color.BLUE)
        Board(binding).blockColor(selectedButton, row, col)
        if (board.modifyFlag == 1) {
            board.modifyFlag = 0
            board.modifiedButton.remove(board.modifiedButton[0])
        }
        return board
    }
}