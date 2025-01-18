package com.example.sudoku.model

import android.os.SystemClock
import android.widget.Button
import com.example.sudoku.R
import com.example.sudoku.databinding.ActivityMainBinding
import com.example.sudoku.util.BoardButtons
import com.example.sudoku.view.InputButtons
import kotlin.random.Random

class Board(private val binding: ActivityMainBinding) {
    var mistakeCount = 0
    private var terminateFlag = false
    var originBoard = Array(9) { IntArray(9) { 0 } }
    var board = Array(9) { IntArray(9) { 0 } }
    private var row = Array(10) { IntArray(10) { 0 } }
    private var col = Array(10) { IntArray(10) { 0 } }
    private var diag = Array(10) { IntArray(10) { 0 } }
    var modifyStatusA = Array(9) { IntArray(9) { 0 } }
    var modifyFlag = 0
    var modifiedButton = arrayListOf<Button>()
    var mRow = -1
    var mCol = -1
    fun blockColor(selectedButton: Button, row: Int, col: Int) {
        if (row <= 2 && col <= 2) {
            selectedButton.setBackgroundResource(R.drawable.first_box)
        } else if (row <= 2 && col >= 6) {
            selectedButton.setBackgroundResource(R.drawable.first_box)
        } else if (row >= 3 && col >= 3 && row <= 5 && col <= 5) {
            selectedButton.setBackgroundResource(R.drawable.first_box)
        } else if (row >= 6 && col <= 2) {
            selectedButton.setBackgroundResource(R.drawable.first_box)
        } else if (row >= 6 && col >= 6) {
            selectedButton.setBackgroundResource(R.drawable.first_box)
        } else {
            selectedButton.setBackgroundResource(R.drawable.second_box)
        }
    }

    private fun makeBoard(k: Int): Boolean {

        if (terminateFlag) {
            return true
        }

        if (k > 80) {
            for (i in 0 until 9) {
                for (j in 0 until 9) {
                    board[i][j] = originBoard[i][j]
                }
            }
            terminateFlag = true
            return true
        }

        val (i, j) = k / 9 to k % 9
        val startNum = Random.nextInt(1, 10)

        if (originBoard[i][j] != 0) {
            makeBoard(k + 1)
        }

        for (m in 1..9) {
            val num = 1 + (m + startNum) % 9
            val d = (i / 3) * 3 + (j / 3)
            if (row[i][num] == 0 && col[j][num] == 0 && diag[d][num] == 0) {
                row[i][num] = 1
                col[j][num] = 1
                diag[d][num] = 1
                originBoard[i][j] = num
                makeBoard(k + 1)
                row[i][num] = 0
                col[j][num] = 0
                diag[d][num] = 0
                originBoard[i][j] = 0
            }
        }
        return false
    }

    fun boardInitialize() {
        binding.clock.base = SystemClock.elapsedRealtime()
        binding.clock.start()
        // 시간 시작
        originBoard = Array(9) { IntArray(9) { 0 } }
        board = Array(9) { IntArray(9) { 0 } }
        row = Array(10) { IntArray(10) { 0 } }
        col = Array(10) { IntArray(10) { 0 } }
        diag = Array(10) { IntArray(10) { 0 } }
        modifyStatusA = Array(9) { IntArray(9) { 0 } }
        modifyFlag = 0
        modifiedButton = arrayListOf<Button>()
        mRow = -1
        mCol = -1
        terminateFlag = false
        mistakeCount = 0
        binding.mistake.text = "0"
        // 변수 초기화
        val seqDiag = listOf(0, 4, 8)
        for (offset in 0 until 9 step 3) {
            val seq = (1..9).toMutableList().apply { shuffle() }
            for (idx in 0 until 9) {
                val (i, j) = idx / 3 to idx % 3
                row[offset + i][seq[idx]] = 1
                col[offset + j][seq[idx]] = 1
                val k = seqDiag[offset / 3]
                diag[k][seq[idx]] = 1
                originBoard[offset + i][offset + j] = seq[idx]
            }
        }
        // 보드판 초기화

        makeBoard(0)
        // 보드 만드는 함수 호출
        for (i in 0..8) {
            for (j in 0..8) {
                originBoard[i][j] = board[i][j]
                modifyStatusA[i][j] = 1
            }
        }

        val temp = mutableSetOf<Int>()
        while (temp.size < 51) {
            temp.add(Random.nextInt(9) * 10 + Random.nextInt(9))
        }
        for (i in temp) {
            board[i / 10][i % 10] = 0
        }
        // 무작위 51개 비우기

        val boardButtons = BoardButtons(binding).returnButton()

        for (b in boardButtons) {
            b.setTextAppearance(R.style.myTitleText)
        }

        for (i in 0..8) {
            for (j in 0..8) {
                boardButtons[i * 9 + j].isEnabled = board[i][j] == 0
                boardButtons[i * 9 + j].text = if (board[i][j] == 0) "" else "${board[i][j]}"
                if (board[i][j] == 0) {
                    InputButtons(this, binding).selectButton(selectedButton = boardButtons[i * 9 + j], i, j)
                }
                blockColor(boardButtons[i * 9 + j], i, j)
            }
        }

    }
}