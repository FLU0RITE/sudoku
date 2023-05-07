package com.example.sudoku

import android.os.Build.VERSION_CODES.P
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.sudoku.databinding.ActivityMainBinding
import kotlin.random.Random



class MainActivity : AppCompatActivity() {
    var originBoard = Array(9) { IntArray(9) { 0 } }
    var board = Array(9) { IntArray(9) { 0 } }
    var row = Array(10) { IntArray(10) { 0 } }
    var col = Array(10) { IntArray(10) { 0 } }
    var diag = Array(10) { IntArray(10) { 0 } }
    var terminateFlag = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 바인딩 객체 획득
        val binding = ActivityMainBinding.inflate(layoutInflater)
        // 화면 출력
        setContentView(binding.root)
        binding.newB.setOnClickListener(){
            originBoard = Array(9) { IntArray(9) { 0 } }
            board = Array(9) { IntArray(9) { 0 } }
            row = Array(10) { IntArray(10) { 0 } }
            col = Array(10) { IntArray(10) { 0 } }
            diag = Array(10) { IntArray(10) { 0 } }
            terminateFlag = false
            boardInit()

        }


    }


    fun makeSudoku(k: Int): Boolean {
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
            makeSudoku(k + 1)
        }

        for (m in 1..9) {
            val num = 1 + (m + startNum) % 9
            val d = (i / 3) * 3 + (j / 3)
            if (row[i][num] == 0 && col[j][num] == 0 && diag[d][num] == 0) {
                row[i][num] = 1
                col[j][num] = 1
                diag[d][num] = 1
                originBoard[i][j] = num
                makeSudoku(k + 1)
                row[i][num] = 0
                col[j][num] = 0
                diag[d][num] = 0
                originBoard[i][j] = 0
            }
        }
        return false
    }

    public fun boardInit(){
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
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


        makeSudoku(0)
        binding.b11.text = "${board[0][0]}"
        binding.b12.text = "${board[0][1]}"
        binding.b13.text = "${board[0][2]}"
        binding.b14.text = "${board[1][0]}"
        binding.b15.text = "${board[1][1]}"
        binding.b16.text = "${board[1][2]}"
        binding.b17.text = "${board[2][0]}"
        binding.b18.text = "${board[2][1]}"
        binding.b19.text = "${board[2][2]}"
        //블록 1
        binding.b21.text = "${board[0][3]}"
        binding.b22.text = "${board[0][4]}"
        binding.b23.text = "${board[0][5]}"
        binding.b24.text = "${board[1][3]}"
        binding.b25.text = "${board[1][4]}"
        binding.b26.text = "${board[1][5]}"
        binding.b27.text = "${board[2][3]}"
        binding.b28.text = "${board[2][4]}"
        binding.b29.text = "${board[2][5]}"
        //블록 2
        binding.b31.text = "${board[0][6]}"
        binding.b32.text = "${board[0][7]}"
        binding.b33.text = "${board[0][8]}"
        binding.b34.text = "${board[1][6]}"
        binding.b35.text = "${board[1][7]}"
        binding.b36.text = "${board[1][8]}"
        binding.b37.text = "${board[2][6]}"
        binding.b38.text = "${board[2][7]}"
        binding.b39.text = "${board[2][8]}"
        //블록 3
        binding.b41.text = "${board[3][0]}"
        binding.b42.text = "${board[3][1]}"
        binding.b43.text = "${board[3][2]}"
        binding.b44.text = "${board[4][0]}"
        binding.b45.text = "${board[4][1]}"
        binding.b46.text = "${board[4][2]}"
        binding.b47.text = "${board[5][0]}"
        binding.b48.text = "${board[5][1]}"
        binding.b49.text = "${board[5][2]}"
        //블록 4
        binding.b51.text = "${board[3][3]}"
        binding.b52.text = "${board[3][4]}"
        binding.b53.text = "${board[3][5]}"
        binding.b54.text = "${board[4][3]}"
        binding.b55.text = "${board[4][4]}"
        binding.b56.text = "${board[4][5]}"
        binding.b57.text = "${board[5][3]}"
        binding.b58.text = "${board[5][4]}"
        binding.b59.text = "${board[5][5]}"
        //블록 5
        binding.b61.text = "${board[3][6]}"
        binding.b62.text = "${board[3][7]}"
        binding.b63.text = "${board[3][8]}"
        binding.b64.text = "${board[4][6]}"
        binding.b65.text = "${board[4][7]}"
        binding.b66.text = "${board[4][8]}"
        binding.b67.text = "${board[5][6]}"
        binding.b68.text = "${board[5][7]}"
        binding.b69.text = "${board[5][8]}"
        //블록 6
        binding.b71.text = "${board[6][0]}"
        binding.b72.text = "${board[6][1]}"
        binding.b73.text = "${board[6][2]}"
        binding.b74.text = "${board[7][0]}"
        binding.b75.text = "${board[7][1]}"
        binding.b76.text = "${board[7][2]}"
        binding.b77.text = "${board[8][0]}"
        binding.b78.text = "${board[8][1]}"
        binding.b79.text = "${board[8][2]}"
        //블록 7
        binding.b81.text = "${board[6][3]}"
        binding.b82.text = "${board[6][4]}"
        binding.b83.text = "${board[6][5]}"
        binding.b84.text = "${board[7][3]}"
        binding.b85.text = "${board[7][4]}"
        binding.b86.text = "${board[7][5]}"
        binding.b87.text = "${board[8][3]}"
        binding.b88.text = "${board[8][4]}"
        binding.b89.text = "${board[8][5]}"
        //블록 8
        binding.b91.text = "${board[6][6]}"
        binding.b92.text = "${board[6][7]}"
        binding.b93.text = "${board[6][8]}"
        binding.b94.text = "${board[7][6]}"
        binding.b95.text = "${board[7][7]}"
        binding.b96.text = "${board[7][8]}"
        binding.b97.text = "${board[8][6]}"
        binding.b98.text = "${board[8][7]}"
        binding.b99.text = "${board[8][8]}"
        //블록 9


    }



}