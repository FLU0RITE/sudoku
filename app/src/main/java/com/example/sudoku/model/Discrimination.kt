package com.example.sudoku.model

import android.widget.Toast

class Discrimination {
    fun discriminate(board: List<List<Int>>, originBoard: List<List<Int>>, mistakeCount: Int) {

        if (mistakeCount >= 3) {
            Toast.makeText(this, "3회 이상 실수로 실패!", Toast.LENGTH_SHORT).show()
        } else {
            var successCount = 0
            for (i in 0..8) {
                for (j in 0..8) {
                    if (board[i][j] != originBoard[i][j]) {
                        Toast.makeText(this, "틀렸습니다!", Toast.LENGTH_SHORT).show()
                        successCount = 1
                        break
                    }
                }
                if (successCount == 1) break
            }
            if (successCount == 0) {
                Toast.makeText(this, "성공!", Toast.LENGTH_SHORT).show()
                Board().boardInitialize()
            }
        }
    }
}