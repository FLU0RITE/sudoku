package com.example.sudoku

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.widget.Button
import android.widget.Toast
import com.example.sudoku.databinding.ActivityMainBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    var originBoard = Array(9) { IntArray(9) { 0 } }
    var board = Array(9) { IntArray(9) { 0 } }
    var row = Array(10) { IntArray(10) { 0 } }
    var col = Array(10) { IntArray(10) { 0 } }
    var diag = Array(10) { IntArray(10) { 0 } }
    var modifyStatusA = Array(9) { IntArray(9) { 0 } }
    var modifyFlag = 0
    var modifiedButton = arrayListOf<Button>()
    var mRow = -1
    var mCol = -1
    var terminateFlag = false
    var mistakeCount = 0
    lateinit var binding : ActivityMainBinding
    // 화면 출력

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        // 바인딩 객체 획득
        setContentView(binding.root)

        boardInit()
        // 처음 보드
        binding.newB.setOnClickListener(){
            boardInit()
        }
        // 새게임
        binding.completeB.setOnClickListener{

            if (mistakeCount >= 3){
                Toast.makeText(this, "3회 이상 실수 실패!", Toast.LENGTH_SHORT).show()
            }
            else{
                var successCount = 0
                for (i in 0..8){
                    for (j in 0..8){
                        if (board[i][j] != originBoard[i][j]){
                            Toast.makeText(this, "틀렸습니다!", Toast.LENGTH_SHORT).show()
                            successCount = 1
                            break
                        }
                    }
                    if (successCount == 1) break
                }
                if (successCount == 0){
                    Toast.makeText(this, "성공!", Toast.LENGTH_SHORT).show()
                    boardInit()
                }
            }

        }
        // 채점
    }

    private fun makeSudoku(k: Int): Boolean {
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

    private fun boardInit(){
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

        makeSudoku(0)
        // 보드 만드는 함수 호출
        for (i in 0..8){
            for(j in 0..8){
                originBoard[i][j] = board[i][j]
                modifyStatusA[i][j] = 1
            }
        }

        var temp = mutableSetOf<Int>()
        while(temp.size<51){
            temp.add(Random.nextInt(9)*10+Random.nextInt(9))
        }
        for (i in temp){
            board[i/10][i%10] = 0
        }
        // 무작위 51개 비우기
        fun blockColor(selectedButton: Button, row:Int, col:Int){
            if (row <= 2 && col <= 2){
                selectedButton.setBackgroundResource(R.drawable.odd)
            }
            else if(row <= 2 && col >= 6){
                selectedButton.setBackgroundResource(R.drawable.odd)
            }
            else if(row >= 3 && col >= 3 && row <= 5 && col <= 5){
                selectedButton.setBackgroundResource(R.drawable.odd)
            }
            else if(row >= 6 && col <= 2){
                selectedButton.setBackgroundResource(R.drawable.odd)
            }
            else if(row >= 6 && col >= 6){
                selectedButton.setBackgroundResource(R.drawable.odd)
            }
            else{
                selectedButton.setBackgroundResource(R.drawable.even)
            }
        }
        fun keys(selectedButton: Button, row:Int, col: Int){
            selectedButton.setOnClickListener{
                if (modifyStatusA[row][col]==1){
                    if (modifyFlag == 0){
                        selectedButton.setBackgroundColor(resources.getColor(R.color.blue))
                        modifiedButton.add(selectedButton)
                        mRow = row
                        mCol = col
                        modifyFlag = 1
                    }
                    else {
                        blockColor(modifiedButton[0],mRow,mCol)
                        selectedButton.setBackgroundColor(resources.getColor(R.color.blue))
                        modifiedButton.remove(modifiedButton[0])
                        modifiedButton.add(selectedButton)
                        mRow = row
                        mCol = col
                        modifyFlag = 1
                    }
                    binding.one.setOnClickListener {
                        selectedButton.text = "1"
                        board[row][col] = 1
                        if (originBoard[row][col] != 1){
                            mistakeCount += 1
                            binding.mistake.text = "${mistakeCount}"
                            selectedButton.setTextColor(resources.getColor(R.color.red))
                            Toast.makeText(this, "실수 ${mistakeCount}번째!", Toast.LENGTH_SHORT).show()
                        }
                        else{
                            selectedButton.setTextColor(resources.getColor(R.color.purple_500))
                        }
                        blockColor(selectedButton,row,col)
                        if (modifyFlag == 1){
                            modifyFlag = 0
                            modifiedButton.remove(modifiedButton[0])
                        }
                    }
                    binding.two.setOnClickListener {
                        selectedButton.text = "2"
                        board[row][col] = 2
                        if (originBoard[row][col] != 2){
                            mistakeCount += 1
                            binding.mistake.text = "${mistakeCount}"
                            selectedButton.setTextColor(resources.getColor(R.color.red))
                            Toast.makeText(this, "실수 ${mistakeCount}번째!", Toast.LENGTH_SHORT).show()
                        }
                        else{
                            selectedButton.setTextColor(resources.getColor(R.color.purple_500))
                        }
                        blockColor(selectedButton,row,col)
                        if (modifyFlag == 1){
                            modifyFlag = 0
                            modifiedButton.remove(modifiedButton[0])
                        }
                    }
                    binding.three.setOnClickListener {
                        selectedButton.text = "3"
                        board[row][col] = 3
                        if (originBoard[row][col] != 3){
                            mistakeCount += 1
                            binding.mistake.text = "${mistakeCount}"
                            selectedButton.setTextColor(resources.getColor(R.color.red))
                            Toast.makeText(this, "실수 ${mistakeCount}번째!", Toast.LENGTH_SHORT).show()
                        }
                        else{
                            selectedButton.setTextColor(resources.getColor(R.color.purple_500))
                        }
                        blockColor(selectedButton,row,col)
                        if (modifyFlag == 1){
                            modifyFlag = 0
                            modifiedButton.remove(modifiedButton[0])
                        }
                    }
                    binding.four.setOnClickListener {
                        selectedButton.text = "4"
                        board[row][col] = 4
                        if (originBoard[row][col] != 4){
                            mistakeCount += 1
                            binding.mistake.text = "${mistakeCount}"
                            selectedButton.setTextColor(resources.getColor(R.color.red))
                            Toast.makeText(this, "실수 ${mistakeCount}번째!", Toast.LENGTH_SHORT).show()
                        }
                        else{
                            selectedButton.setTextColor(resources.getColor(R.color.purple_500))
                        }
                        blockColor(selectedButton,row,col)
                        if (modifyFlag == 1){
                            modifyFlag = 0
                            modifiedButton.remove(modifiedButton[0])
                        }
                    }
                    binding.five.setOnClickListener {
                        selectedButton.text = "5"
                        board[row][col] = 5
                        if (originBoard[row][col] != 5){
                            mistakeCount += 1
                            binding.mistake.text = "${mistakeCount}"
                            selectedButton.setTextColor(resources.getColor(R.color.red))
                            Toast.makeText(this, "실수 ${mistakeCount}번째!", Toast.LENGTH_SHORT).show()
                        }
                        else{
                            selectedButton.setTextColor(resources.getColor(R.color.purple_500))
                        }
                        blockColor(selectedButton,row,col)
                        if (modifyFlag == 1){
                            modifyFlag = 0
                            modifiedButton.remove(modifiedButton[0])
                        }
                    }
                    binding.six.setOnClickListener {
                        selectedButton.text = "6"
                        board[row][col] = 6
                        if (originBoard[row][col] != 6){
                            mistakeCount += 1
                            binding.mistake.text = "${mistakeCount}"
                            selectedButton.setTextColor(resources.getColor(R.color.red))
                            Toast.makeText(this, "실수 ${mistakeCount}번째!", Toast.LENGTH_SHORT).show()
                        }
                        else{
                            selectedButton.setTextColor(resources.getColor(R.color.purple_500))
                        }
                        blockColor(selectedButton,row,col)
                        if (modifyFlag == 1){
                            modifyFlag = 0
                            modifiedButton.remove(modifiedButton[0])
                        }
                    }
                    binding.seven.setOnClickListener {
                        selectedButton.text = "7"
                        board[row][col] = 7
                        if (originBoard[row][col] != 7){
                            mistakeCount += 1
                            binding.mistake.text = "${mistakeCount}"
                            selectedButton.setTextColor(resources.getColor(R.color.red))
                            Toast.makeText(this, "실수 ${mistakeCount}번째!", Toast.LENGTH_SHORT).show()
                        }
                        else{
                            selectedButton.setTextColor(resources.getColor(R.color.purple_500))
                        }
                        blockColor(selectedButton,row,col)
                        if (modifyFlag == 1){
                            modifyFlag = 0
                            modifiedButton.remove(modifiedButton[0])
                        }
                    }
                    binding.eight.setOnClickListener {
                        selectedButton.text = "8"
                        board[row][col] = 8
                        if (originBoard[row][col] != 8){
                            mistakeCount += 1
                            binding.mistake.text = "${mistakeCount}"
                            selectedButton.setTextColor(resources.getColor(R.color.red))
                            Toast.makeText(this, "실수 ${mistakeCount}번째!", Toast.LENGTH_SHORT).show()
                        }
                        else{
                            selectedButton.setTextColor(resources.getColor(R.color.purple_500))
                        }
                        blockColor(selectedButton,row,col)
                        if (modifyFlag == 1){
                            modifyFlag = 0
                            modifiedButton.remove(modifiedButton[0])
                        }
                    }
                    binding.nine.setOnClickListener {
                        selectedButton.text = "9"
                        board[row][col] = 9
                        if (originBoard[row][col] != 9){
                            mistakeCount += 1
                            binding.mistake.text = "${mistakeCount}"
                            selectedButton.setTextColor(resources.getColor(R.color.red))
                            Toast.makeText(this, "실수 ${mistakeCount}번째!", Toast.LENGTH_SHORT).show()
                        }
                        else{
                            selectedButton.setTextColor(resources.getColor(R.color.purple_500))
                        }
                        blockColor(selectedButton,row,col)
                        if (modifyFlag == 1){
                            modifyFlag = 0
                            modifiedButton.remove(modifiedButton[0])
                        }
                    }
                    binding.deleteB.setOnClickListener {
                        selectedButton.text = ""
                        board[row][col] = 0
                        blockColor(selectedButton,row,col)
                        if (modifyFlag == 1){
                            modifyFlag = 0
                            modifiedButton.remove(modifiedButton[0])
                        }
                    }
                    binding.hintB.setOnClickListener {
                        selectedButton.text = "${originBoard[row][col]}"
                        board[row][col] = originBoard[row][col]
                        selectedButton.setTextColor(resources.getColor(R.color.purple_500))
                        blockColor(selectedButton,row,col)
                        if (modifyFlag == 1){
                            modifyFlag = 0
                            modifiedButton.remove(modifiedButton[0])
                        }
                    }
                }
            }
        }
        var buttonListB = arrayListOf<Button>(
            binding.b11,binding.b12,binding.b13,binding.b14,binding.b15,binding.b16,binding.b17,binding.b18,binding.b19,
            binding.b31,binding.b32,binding.b33,binding.b34,binding.b35,binding.b36,binding.b37,binding.b38,binding.b39,
            binding.b51,binding.b52,binding.b53,binding.b54,binding.b55,binding.b56,binding.b57,binding.b58,binding.b59,
            binding.b71,binding.b72,binding.b73,binding.b74,binding.b75,binding.b76,binding.b77,binding.b78,binding.b79,
            binding.b91,binding.b92,binding.b93,binding.b94,binding.b95,binding.b96,binding.b97,binding.b98,binding.b99
        )
        var buttonListW = arrayListOf<Button>(
            binding.b21,binding.b22,binding.b23,binding.b24,binding.b25,binding.b26,binding.b27,binding.b28,binding.b29,
            binding.b41,binding.b42,binding.b43,binding.b44,binding.b45,binding.b46,binding.b47,binding.b48,binding.b49,
            binding.b61,binding.b62,binding.b63,binding.b64,binding.b65,binding.b66,binding.b67,binding.b68,binding.b69,
            binding.b81,binding.b82,binding.b83,binding.b84,binding.b85,binding.b86,binding.b87,binding.b88,binding.b89
        )
        for(b in buttonListB){
            b.setTextColor(resources.getColor(R.color.black))
        }
        for(b in buttonListW){
            b.setTextColor(resources.getColor(R.color.white))
        }
        binding.b11.text = if (board[0][0] == 0) "" else "${board[0][0]}"
        if (board[0][0]==0) keys(selectedButton = binding.b11, 0,0)
        binding.b11.setBackgroundResource(R.drawable.odd)

        binding.b12.text = if (board[0][1] == 0) "" else "${board[0][1]}"
        if (board[0][1]==0) keys(selectedButton = binding.b12,0,1)
        binding.b12.setBackgroundResource(R.drawable.odd)

        binding.b13.text = if (board[0][2] == 0) "" else "${board[0][2]}"
        if (board[0][2]==0) keys(selectedButton = binding.b13,0,2)
        binding.b13.setBackgroundResource(R.drawable.odd)

        binding.b14.text = if (board[1][0] == 0) "" else "${board[1][0]}"
        if (board[1][0]==0) keys(selectedButton = binding.b14,1,0)
        binding.b14.setBackgroundResource(R.drawable.odd)

        binding.b15.text = if (board[1][1] == 0) "" else "${board[1][1]}"
        if (board[1][1]==0) keys(selectedButton = binding.b15,1,1)
        binding.b15.setBackgroundResource(R.drawable.odd)

        binding.b16.text = if (board[1][2] == 0) "" else "${board[1][2]}"
        if (board[1][2]==0) keys(selectedButton = binding.b16,1,2)
        binding.b16.setBackgroundResource(R.drawable.odd)

        binding.b17.text = if (board[2][0] == 0) "" else "${board[2][0]}"
        if (board[2][0]==0) keys(selectedButton = binding.b17,2,0)
        binding.b17.setBackgroundResource(R.drawable.odd)

        binding.b18.text = if (board[2][1] == 0) "" else "${board[2][1]}"
        if (board[2][1]==0) keys(selectedButton = binding.b18,2,1)
        binding.b18.setBackgroundResource(R.drawable.odd)

        binding.b19.text = if (board[2][2] == 0) "" else "${board[2][2]}"
        if (board[2][2]==0) keys(selectedButton = binding.b19,2,2)
        binding.b19.setBackgroundResource(R.drawable.odd)
        //블록 1

        binding.b21.text = if (board[0][3] == 0) "" else "${board[0][3]}"
        if (board[0][3]==0) keys(selectedButton = binding.b21,0,3)
        binding.b21.setBackgroundResource(R.drawable.even)

        binding.b22.text = if (board[0][4] == 0) "" else "${board[0][4]}"
        if (board[0][4]==0) keys(selectedButton = binding.b22,0,4)
        binding.b22.setBackgroundResource(R.drawable.even)

        binding.b23.text = if (board[0][5] == 0) "" else "${board[0][5]}"
        if (board[0][5]==0) keys(selectedButton = binding.b23,0,5)
        binding.b23.setBackgroundResource(R.drawable.even)

        binding.b24.text = if (board[1][3] == 0) "" else "${board[1][3]}"
        if (board[1][3]==0) keys(selectedButton = binding.b24,1,3)
        binding.b24.setBackgroundResource(R.drawable.even)

        binding.b25.text = if (board[1][4] == 0) "" else "${board[1][4]}"
        if (board[1][4]==0) keys(selectedButton = binding.b25,1,4)
        binding.b25.setBackgroundResource(R.drawable.even)

        binding.b26.text = if (board[1][5] == 0) "" else "${board[1][5]}"
        if (board[1][5]==0) keys(selectedButton = binding.b26,1,5)
        binding.b26.setBackgroundResource(R.drawable.even)

        binding.b27.text = if (board[2][3] == 0) "" else "${board[2][3]}"
        if (board[2][3]==0) keys(selectedButton = binding.b27,2,3)
        binding.b27.setBackgroundResource(R.drawable.even)

        binding.b28.text = if (board[2][4] == 0) "" else "${board[2][4]}"
        if (board[2][4]==0) keys(selectedButton = binding.b28,2,4)
        binding.b28.setBackgroundResource(R.drawable.even)

        binding.b29.text = if (board[2][5] == 0) "" else "${board[2][5]}"
        if (board[2][5]==0) keys(selectedButton = binding.b29,2,5)
        binding.b29.setBackgroundResource(R.drawable.even)
        // 블록 2

        binding.b31.text = if (board[0][6] == 0) "" else "${board[0][6]}"
        if (board[0][6]==0) keys(selectedButton = binding.b31,0,6)
        binding.b31.setBackgroundResource(R.drawable.odd)

        binding.b32.text = if (board[0][7] == 0) "" else "${board[0][7]}"
        if (board[0][7]==0) keys(selectedButton = binding.b32,0,7)
        binding.b32.setBackgroundResource(R.drawable.odd)

        binding.b33.text = if (board[0][8] == 0) "" else "${board[0][8]}"
        if (board[0][8]==0) keys(selectedButton = binding.b33,0,8)
        binding.b33.setBackgroundResource(R.drawable.odd)

        binding.b34.text = if (board[1][6] == 0) "" else "${board[1][6]}"
        if (board[1][6]==0) keys(selectedButton = binding.b34,1,6)
        binding.b34.setBackgroundResource(R.drawable.odd)

        binding.b35.text = if (board[1][7] == 0) "" else "${board[1][7]}"
        if (board[1][7]==0) keys(selectedButton = binding.b35,1,7)
        binding.b35.setBackgroundResource(R.drawable.odd)

        binding.b36.text = if (board[1][8] == 0) "" else "${board[1][8]}"
        if (board[1][8]==0) keys(selectedButton = binding.b36,1,8)
        binding.b36.setBackgroundResource(R.drawable.odd)

        binding.b37.text = if (board[2][6] == 0) "" else "${board[2][6]}"
        if (board[2][6]==0) keys(selectedButton = binding.b37,2,6)
        binding.b37.setBackgroundResource(R.drawable.odd)

        binding.b38.text = if (board[2][7] == 0) "" else "${board[2][7]}"
        if (board[2][7]==0) keys(selectedButton = binding.b38,2,7)
        binding.b38.setBackgroundResource(R.drawable.odd)

        binding.b39.text = if (board[2][8] == 0) "" else "${board[2][8]}"
        if (board[2][8]==0) keys(selectedButton = binding.b39,2,8)
        binding.b39.setBackgroundResource(R.drawable.odd)
        // 블록 3

        binding.b41.text = if (board[3][0] == 0) "" else "${board[3][0]}"
        if (board[3][0] == 0) keys(selectedButton = binding.b41,3,0)
        binding.b41.setBackgroundResource(R.drawable.even)

        binding.b42.text = if (board[3][1] == 0) "" else "${board[3][1]}"
        if (board[3][1] == 0) keys(selectedButton = binding.b42,3,1)
        binding.b42.setBackgroundResource(R.drawable.even)

        binding.b43.text = if (board[3][2] == 0) "" else "${board[3][2]}"
        if (board[3][2] == 0) keys(selectedButton = binding.b43,3,2)
        binding.b43.setBackgroundResource(R.drawable.even)

        binding.b44.text = if (board[4][0] == 0) "" else "${board[4][0]}"
        if (board[4][0] == 0) keys(selectedButton = binding.b44,4,0)
        binding.b44.setBackgroundResource(R.drawable.even)

        binding.b45.text = if (board[4][1] == 0) "" else "${board[4][1]}"
        if (board[4][1] == 0) keys(selectedButton = binding.b45,4,1)
        binding.b45.setBackgroundResource(R.drawable.even)

        binding.b46.text = if (board[4][2] == 0) "" else "${board[4][2]}"
        if (board[4][2] == 0) keys(selectedButton = binding.b46,4,2)
        binding.b46.setBackgroundResource(R.drawable.even)

        binding.b47.text = if (board[5][0] == 0) "" else "${board[5][0]}"
        if (board[5][0] == 0) keys(selectedButton = binding.b47,5,0)
        binding.b47.setBackgroundResource(R.drawable.even)

        binding.b48.text = if (board[5][1] == 0) "" else "${board[5][1]}"
        if (board[5][1] == 0) keys(selectedButton = binding.b48,5,1)
        binding.b48.setBackgroundResource(R.drawable.even)

        binding.b49.text = if (board[5][2] == 0) "" else "${board[5][2]}"
        if (board[5][2] == 0) keys(selectedButton = binding.b49,5,2)
        binding.b49.setBackgroundResource(R.drawable.even)
        // 블록 4

        binding.b51.text = if (board[3][3] == 0) "" else "${board[3][3]}"
        if (board[3][3] == 0) keys(selectedButton = binding.b51,3,3)
        binding.b51.setBackgroundResource(R.drawable.odd)

        binding.b52.text = if (board[3][4] == 0) "" else "${board[3][4]}"
        if (board[3][4] == 0) keys(selectedButton = binding.b52,3,4)
        binding.b52.setBackgroundResource(R.drawable.odd)

        binding.b53.text = if (board[3][5] == 0) "" else "${board[3][5]}"
        if (board[3][5] == 0) keys(selectedButton = binding.b53,3,5)
        binding.b53.setBackgroundResource(R.drawable.odd)

        binding.b54.text = if (board[4][3] == 0) "" else "${board[4][3]}"
        if (board[4][3] == 0) keys(selectedButton = binding.b54,4,3)
        binding.b54.setBackgroundResource(R.drawable.odd)

        binding.b55.text = if (board[4][4] == 0) "" else "${board[4][4]}"
        if (board[4][4] == 0) keys(selectedButton = binding.b55,4,4)
        binding.b55.setBackgroundResource(R.drawable.odd)

        binding.b56.text = if (board[4][5] == 0) "" else "${board[4][5]}"
        if (board[4][5] == 0) keys(selectedButton = binding.b56,4,5)
        binding.b56.setBackgroundResource(R.drawable.odd)

        binding.b57.text = if (board[5][3] == 0) "" else "${board[5][3]}"
        if (board[5][3] == 0) keys(selectedButton = binding.b57,5,3)
        binding.b57.setBackgroundResource(R.drawable.odd)

        binding.b58.text = if (board[5][4] == 0) "" else "${board[5][4]}"
        if (board[5][4] == 0) keys(selectedButton = binding.b58,5,4)
        binding.b58.setBackgroundResource(R.drawable.odd)

        binding.b59.text = if (board[5][5] == 0) "" else "${board[5][5]}"
        if (board[5][5] == 0) keys(selectedButton = binding.b59,5,5)
        binding.b59.setBackgroundResource(R.drawable.odd)
        //블록 5

        binding.b61.text = if (board[3][6] == 0) "" else "${board[3][6]}"
        if (board[3][6] == 0) keys(selectedButton = binding.b61,3,6)
        binding.b61.setBackgroundResource(R.drawable.even)

        binding.b62.text = if (board[3][7] == 0) "" else "${board[3][7]}"
        if (board[3][7] == 0) keys(selectedButton = binding.b62,3,7)
        binding.b62.setBackgroundResource(R.drawable.even)

        binding.b63.text = if (board[3][8] == 0) "" else "${board[3][8]}"
        if (board[3][8] == 0) keys(selectedButton = binding.b63,3,8)
        binding.b63.setBackgroundResource(R.drawable.even)

        binding.b64.text = if (board[4][6] == 0) "" else "${board[4][6]}"
        if (board[4][6] == 0) keys(selectedButton = binding.b64,4,6)
        binding.b64.setBackgroundResource(R.drawable.even)

        binding.b65.text = if (board[4][7] == 0) "" else "${board[4][7]}"
        if (board[4][7] == 0) keys(selectedButton = binding.b65,4,7)
        binding.b65.setBackgroundResource(R.drawable.even)

        binding.b66.text = if (board[4][8] == 0) "" else "${board[4][8]}"
        if (board[4][8] == 0) keys(selectedButton = binding.b66,4,8)
        binding.b66.setBackgroundResource(R.drawable.even)

        binding.b67.text = if (board[5][6] == 0) "" else "${board[5][6]}"
        if (board[5][6] == 0) keys(selectedButton = binding.b67,5,6)
        binding.b67.setBackgroundResource(R.drawable.even)

        binding.b68.text = if (board[5][7] == 0) "" else "${board[5][7]}"
        if (board[5][7] == 0) keys(selectedButton = binding.b68,5,7)
        binding.b68.setBackgroundResource(R.drawable.even)

        binding.b69.text = if (board[5][8] == 0) "" else "${board[5][8]}"
        if (board[5][8] == 0) keys(selectedButton = binding.b69,5,8)
        binding.b69.setBackgroundResource(R.drawable.even)
        //블록 6

        binding.b71.text = if (board[6][0] == 0) "" else "${board[6][0]}"
        if (board[6][0] == 0) keys(selectedButton = binding.b71,6,0)
        binding.b71.setBackgroundResource(R.drawable.odd)

        binding.b72.text = if (board[6][1] == 0) "" else "${board[6][1]}"
        if (board[6][1] == 0) keys(selectedButton = binding.b72,6,1)
        binding.b72.setBackgroundResource(R.drawable.odd)

        binding.b73.text = if (board[6][2] == 0) "" else "${board[6][2]}"
        if (board[6][2] == 0) keys(selectedButton = binding.b73,6,2)
        binding.b73.setBackgroundResource(R.drawable.odd)

        binding.b74.text = if (board[7][0] == 0) "" else "${board[7][0]}"
        if (board[7][0] == 0) keys(selectedButton = binding.b74,7,0)
        binding.b74.setBackgroundResource(R.drawable.odd)

        binding.b75.text = if (board[7][1] == 0) "" else "${board[7][1]}"
        if (board[7][1] == 0) keys(selectedButton = binding.b75,7,1)
        binding.b75.setBackgroundResource(R.drawable.odd)

        binding.b76.text = if (board[7][2] == 0) "" else "${board[7][2]}"
        if (board[7][2] == 0) keys(selectedButton = binding.b76,7,2)
        binding.b76.setBackgroundResource(R.drawable.odd)

        binding.b77.text = if (board[8][0] == 0) "" else "${board[8][0]}"
        if (board[8][0] == 0) keys(selectedButton = binding.b77,8,0)
        binding.b77.setBackgroundResource(R.drawable.odd)

        binding.b78.text = if (board[8][1] == 0) "" else "${board[8][1]}"
        if (board[8][1] == 0) keys(selectedButton = binding.b78,8,1)
        binding.b78.setBackgroundResource(R.drawable.odd)

        binding.b79.text = if (board[8][2] == 0) "" else "${board[8][2]}"
        if (board[8][2] == 0) keys(selectedButton = binding.b79,8,2)
        binding.b79.setBackgroundResource(R.drawable.odd)
        //블록 7

        binding.b81.text = if (board[6][3] == 0) "" else "${board[6][3]}"
        if (board[6][3] == 0) keys(selectedButton = binding.b81,6,3)
        binding.b81.setBackgroundResource(R.drawable.even)

        binding.b82.text = if (board[6][4] == 0) "" else "${board[6][4]}"
        if (board[6][4] == 0) keys(selectedButton = binding.b82,6,4)
        binding.b82.setBackgroundResource(R.drawable.even)

        binding.b83.text = if (board[6][5] == 0) "" else "${board[6][5]}"
        if (board[6][5] == 0) keys(selectedButton = binding.b83,6,5)
        binding.b83.setBackgroundResource(R.drawable.even)

        binding.b84.text = if (board[7][3] == 0) "" else "${board[7][3]}"
        if (board[7][3] == 0) keys(selectedButton = binding.b84,7,3)
        binding.b84.setBackgroundResource(R.drawable.even)

        binding.b85.text = if (board[7][4] == 0) "" else "${board[7][4]}"
        if (board[7][4] == 0) keys(selectedButton = binding.b85,7,4)
        binding.b85.setBackgroundResource(R.drawable.even)

        binding.b86.text = if (board[7][5] == 0) "" else "${board[7][5]}"
        if (board[7][5] == 0) keys(selectedButton = binding.b86,7,5)
        binding.b86.setBackgroundResource(R.drawable.even)

        binding.b87.text = if (board[8][3] == 0) "" else "${board[8][3]}"
        if (board[8][3] == 0) keys(selectedButton = binding.b87,8,3)
        binding.b87.setBackgroundResource(R.drawable.even)

        binding.b88.text = if (board[8][4] == 0) "" else "${board[8][4]}"
        if (board[8][4] == 0) keys(selectedButton = binding.b88,8,4)
        binding.b88.setBackgroundResource(R.drawable.even)

        binding.b89.text = if (board[8][5] == 0) "" else "${board[8][5]}"
        if (board[8][5] == 0) keys(selectedButton = binding.b89,8,5)
        binding.b89.setBackgroundResource(R.drawable.even)
        //블록 8

        binding.b91.text = if (board[6][6] == 0) "" else "${board[6][6]}"
        if (board[6][6] == 0) keys(selectedButton = binding.b91,6,6)
        binding.b91.setBackgroundResource(R.drawable.odd)

        binding.b92.text = if (board[6][7] == 0) "" else "${board[6][7]}"
        if (board[6][7] == 0) keys(selectedButton = binding.b92,6,7)
        binding.b92.setBackgroundResource(R.drawable.odd)

        binding.b93.text = if (board[6][8] == 0) "" else "${board[6][8]}"
        if (board[6][8] == 0) keys(selectedButton = binding.b93,6,8)
        binding.b93.setBackgroundResource(R.drawable.odd)

        binding.b94.text = if (board[7][6] == 0) "" else "${board[7][6]}"
        if (board[7][6] == 0) keys(selectedButton = binding.b94,7,6)
        binding.b94.setBackgroundResource(R.drawable.odd)

        binding.b95.text = if (board[7][7] == 0) "" else "${board[7][7]}"
        if (board[7][7] == 0) keys(selectedButton = binding.b95,7,7)
        binding.b95.setBackgroundResource(R.drawable.odd)

        binding.b96.text = if (board[7][8] == 0) "" else "${board[7][8]}"
        if (board[7][8] == 0) keys(selectedButton = binding.b96,7,8)
        binding.b96.setBackgroundResource(R.drawable.odd)

        binding.b97.text = if (board[8][6] == 0) "" else "${board[8][6]}"
        if (board[8][6] == 0) keys(selectedButton = binding.b97,8,6)
        binding.b97.setBackgroundResource(R.drawable.odd)

        binding.b98.text = if (board[8][7] == 0) "" else "${board[8][7]}"
        if (board[8][7] == 0) keys(selectedButton = binding.b98,8,7)
        binding.b98.setBackgroundResource(R.drawable.odd)

        binding.b99.text = if (board[8][8] == 0) "" else "${board[8][8]}"
        if (board[8][8] == 0) keys(selectedButton = binding.b99,8,8)
        binding.b99.setBackgroundResource(R.drawable.odd)
        //블록 9
    }
}