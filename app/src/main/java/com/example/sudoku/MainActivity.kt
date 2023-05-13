package com.example.sudoku

import android.os.Build.VERSION_CODES.P
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.view.KeyEvent
import android.widget.Button
import com.example.sudoku.databinding.ActivityMainBinding
import kotlin.random.Random



class MainActivity : AppCompatActivity() {
    var originBoard = Array(9) { IntArray(9) { 0 } }
    var board = Array(9) { IntArray(9) { 0 } }
    var row = Array(10) { IntArray(10) { 0 } }
    var col = Array(10) { IntArray(10) { 0 } }
    var diag = Array(10) { IntArray(10) { 0 } }
    var terminateFlag = false
    lateinit var binding : ActivityMainBinding
    // 화면 출력

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        // 바인딩 객체 획득
        setContentView(binding.root)
        boardInit()
        binding.newB.setOnClickListener(){
            boardInit()
        }
        binding.hintB.setOnClickListener{

        }
        binding.exitB.setOnClickListener{
            finish()
        }
        binding.mistake.setOnClickListener{

        }
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

        originBoard = Array(9) { IntArray(9) { 0 } }
        board = Array(9) { IntArray(9) { 0 } }
        row = Array(10) { IntArray(10) { 0 } }
        col = Array(10) { IntArray(10) { 0 } }
        diag = Array(10) { IntArray(10) { 0 } }
        terminateFlag = false

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


        var temp = mutableSetOf<Int>()

        while(temp.size<51){
            temp.add(Random.nextInt(9)*10+Random.nextInt(9))
        }
        for (i in temp){
            board[i/10][i%10] = 0
        }


        val used = arrayListOf<Button>()
        val use = arrayListOf<Button>()
        val hi = arrayListOf<Button>(
            binding.b11,binding.b12,binding.b13,binding.b14,binding.b15,binding.b16,binding.b17,binding.b18,binding.b19,
            binding.b21,binding.b22,binding.b23,binding.b24,binding.b25,binding.b26,binding.b27,binding.b28,binding.b29,
            binding.b31,binding.b32,binding.b33,binding.b34,binding.b35,binding.b36,binding.b37,binding.b38,binding.b39,
            binding.b41,binding.b42,binding.b43,binding.b44,binding.b45,binding.b46,binding.b47,binding.b48,binding.b49,
            binding.b51,binding.b52,binding.b53,binding.b54,binding.b55,binding.b56,binding.b57,binding.b58,binding.b59,
            binding.b61,binding.b62,binding.b63,binding.b64,binding.b65,binding.b66,binding.b67,binding.b68,binding.b69,
            binding.b71,binding.b72,binding.b73,binding.b74,binding.b75,binding.b76,binding.b77,binding.b78,binding.b79,
            binding.b81,binding.b82,binding.b83,binding.b84,binding.b85,binding.b86,binding.b87,binding.b88,binding.b89,
            binding.b91,binding.b92,binding.b93,binding.b94,binding.b95,binding.b96,binding.b97,binding.b98,binding.b99,
        )
        var start = 0
        val buttonColor = arrayListOf<Int>()
        fun keys(selectedButton: Button){
            var trats = 0
            var buttonColorSize = buttonColor.size.toInt()

            selectedButton.setOnClickListener{
                if (hi.contains(selectedButton)){
                    use.add(selectedButton)
                    use.remove(use[0])
                }
                else{
                    used.add(selectedButton)
                    used.remove(used[0])
                }
                selectedButton.setTextColor(resources.getColor(R.color.black))
                selectedButton.setBackgroundColor(resources.getColor(R.color.blue))

                binding.one.setOnClickListener {
                    selectedButton.text = "1";start = 0;selectedButton.setBackgroundColor(resources.getColor(R.color.blue))
                    start = 0;buttonColorSize = buttonColor.size.toInt();trats = 0
                }
                binding.two.setOnClickListener {
                    selectedButton.text = "2";start = 0;selectedButton.setBackgroundColor(resources.getColor(R.color.blue))
                    start = 0;buttonColorSize = buttonColor.size.toInt();trats = 0
                }
                binding.three.setOnClickListener {
                    selectedButton.text = "3";start = 0;selectedButton.setBackgroundColor(resources.getColor(R.color.blue))
                    start = 0;buttonColorSize = buttonColor.size.toInt();trats = 0
                }
                binding.four.setOnClickListener {
                    selectedButton.text = "4";start = 0;selectedButton.setBackgroundColor(resources.getColor(R.color.blue))
                    start = 0;buttonColorSize = buttonColor.size.toInt();trats = 0
                }
                binding.five.setOnClickListener {
                    selectedButton.text = "5";start = 0;selectedButton.setBackgroundColor(resources.getColor(R.color.blue))
                    start = 0;buttonColorSize = buttonColor.size.toInt();trats = 0
                }
                binding.six.setOnClickListener {
                    selectedButton.text = "6";start = 0;selectedButton.setBackgroundColor(resources.getColor(R.color.blue))
                    start = 0;buttonColorSize = buttonColor.size.toInt();trats = 0
                }
                binding.seven.setOnClickListener {
                    selectedButton.text = "7";start = 0;selectedButton.setBackgroundColor(resources.getColor(R.color.blue))
                    start = 0;buttonColorSize = buttonColor.size.toInt();trats = 0
                }
                binding.eight.setOnClickListener {
                    selectedButton.text = "8";start = 0;selectedButton.setBackgroundColor(resources.getColor(R.color.blue))
                    start = 0;buttonColorSize = buttonColor.size.toInt();trats = 0
                }
                binding.nine.setOnClickListener {
                    selectedButton.text = "9";start = 0;selectedButton.setBackgroundColor(resources.getColor(R.color.blue))
                    start = 0;buttonColorSize = buttonColor.size.toInt();trats = 0
                }
                binding.deleteB.setOnClickListener {
                    selectedButton.text = ""
                }
            }
        }

        binding.b11.text = if (board[0][0] == 0) "" else "${board[0][0]}"; if (board[0][0]==0) keys(selectedButton = binding.b11)
        binding.b12.text = if (board[0][1] == 0) "" else "${board[0][1]}"; if (board[0][1]==0) keys(selectedButton = binding.b12)
        binding.b13.text = if (board[0][2] == 0) "" else "${board[0][2]}"; if (board[0][2]==0) keys(selectedButton = binding.b13)
        binding.b14.text = if (board[1][0] == 0) "" else "${board[1][0]}"; if (board[1][0]==0) keys(selectedButton = binding.b14)
        binding.b15.text = if (board[1][1] == 0) "" else "${board[1][1]}"; if (board[1][1]==0) keys(selectedButton = binding.b15)
        binding.b16.text = if (board[1][2] == 0) "" else "${board[1][2]}"; if (board[1][2]==0) keys(selectedButton = binding.b16)
        binding.b17.text = if (board[2][0] == 0) "" else "${board[2][0]}"; if (board[2][0]==0) keys(selectedButton = binding.b17)
        binding.b18.text = if (board[2][1] == 0) "" else "${board[2][1]}"; if (board[2][1]==0) keys(selectedButton = binding.b18)
        binding.b19.text = if (board[2][2] == 0) "" else "${board[2][2]}"; if (board[2][2]==0) keys(selectedButton = binding.b19)

        //블록 1
        binding.b21.text =  if (board[0][3] == 0) "" else "${board[0][3]}"
        binding.b22.text =  if (board[0][4] == 0) "" else "${board[0][4]}"
        binding.b23.text =  if (board[0][5] == 0) "" else "${board[0][5]}"
        binding.b24.text =  if (board[1][4] == 0) "" else "${board[1][4]}"
        binding.b25.text =  if (board[1][5] == 0) "" else "${board[1][5]}"
        binding.b26.text =  if (board[1][6] == 0) "" else "${board[1][6]}"
        binding.b27.text =  if (board[2][3] == 0) "" else "${board[2][3]}"
        binding.b28.text =  if (board[2][4] == 0) "" else "${board[2][4]}"
        binding.b29.text =  if (board[2][5] == 0) "" else "${board[2][5]}"
        // 블록 2
        binding.b31.text = if (board[0][6] == 0) "" else "${board[0][6]}"
        binding.b32.text = if (board[0][7] == 0) "" else "${board[0][7]}"
        binding.b33.text = if (board[0][8] == 0) "" else "${board[0][8]}"
        binding.b34.text = if (board[1][6] == 0) "" else "${board[1][6]}"
        binding.b35.text = if (board[1][7] == 0) "" else "${board[1][7]}"
        binding.b36.text = if (board[1][8] == 0) "" else "${board[1][8]}"
        binding.b37.text = if (board[2][6] == 0) "" else "${board[2][6]}"
        binding.b38.text = if (board[2][7] == 0) "" else "${board[2][7]}"
        binding.b39.text = if (board[2][8] == 0) "" else "${board[2][8]}"
// 블록 3
        binding.b41.text = if (board[3][0] == 0) "" else "${board[3][0]}"
        binding.b42.text = if (board[3][1] == 0) "" else "${board[3][1]}"
        binding.b43.text = if (board[3][2] == 0) "" else "${board[3][2]}"
        binding.b44.text = if (board[4][0] == 0) "" else "${board[4][0]}"
        binding.b45.text = if (board[4][1] == 0) "" else "${board[4][1]}"
        binding.b46.text = if (board[4][2] == 0) "" else "${board[4][2]}"
        binding.b47.text = if (board[5][0] == 0) "" else "${board[5][0]}"
        binding.b48.text = if (board[5][1] == 0) "" else "${board[5][1]}"
        binding.b49.text = if (board[5][2] == 0) "" else "${board[5][2]}"
// 블록 4
        binding.b51.text = if (board[3][3] == 0) "" else "${board[3][3]}"
        binding.b52.text = if (board[3][4] == 0) "" else "${board[3][4]}"
        binding.b53.text = if (board[3][5] == 0) "" else "${board[3][5]}"
        binding.b54.text = if (board[4][3] == 0) "" else "${board[4][3]}"
        binding.b55.text = if (board[4][4] == 0) "" else "${board[4][4]}"
        binding.b56.text = if (board[4][5] == 0) "" else "${board[4][5]}"
        binding.b57.text = if (board[5][3] == 0) "" else "${board[5][3]}"
        binding.b58.text = if (board[5][4] == 0) "" else "${board[5][4]}"
        binding.b59.text = if (board[5][5] == 0) "" else "${board[5][5]}"
        //블록 5
        binding.b61.text = if (board[3][6] == 0) "" else "${board[3][6]}"
        binding.b62.text = if (board[3][7] == 0) "" else "${board[3][7]}"
        binding.b63.text = if (board[3][8] == 0) "" else "${board[3][8]}"
        binding.b64.text = if (board[4][6] == 0) "" else "${board[4][6]}"
        binding.b65.text = if (board[4][7] == 0) "" else "${board[4][7]}"
        binding.b66.text = if (board[4][8] == 0) "" else "${board[4][8]}"
        binding.b67.text = if (board[5][6] == 0) "" else "${board[5][6]}"
        binding.b68.text = if (board[5][7] == 0) "" else "${board[5][7]}"
        binding.b69.text = if (board[5][8] == 0) "" else "${board[5][8]}"
//블록 6
        binding.b71.text = if (board[6][0] == 0) "" else "${board[6][0]}"
        binding.b72.text = if (board[6][1] == 0) "" else "${board[6][1]}"
        binding.b73.text = if (board[6][2] == 0) "" else "${board[6][2]}"
        binding.b74.text = if (board[7][0] == 0) "" else "${board[7][0]}"
        binding.b75.text = if (board[7][1] == 0) "" else "${board[7][1]}"
        binding.b76.text = if (board[7][2] == 0) "" else "${board[7][2]}"
        binding.b77.text = if (board[8][0] == 0) "" else "${board[8][0]}"
        binding.b78.text = if (board[8][1] == 0) "" else "${board[8][1]}"
        binding.b79.text = if (board[8][2] == 0) "" else "${board[8][2]}"
//블록 7
        binding.b81.text = if (board[6][3] == 0) "" else "${board[6][3]}"
        binding.b82.text = if (board[6][4] == 0) "" else "${board[6][4]}"
        binding.b83.text = if (board[6][5] == 0) "" else "${board[6][5]}"
        binding.b84.text = if (board[7][3] == 0) "" else "${board[7][3]}"
        binding.b85.text = if (board[7][4] == 0) "" else "${board[7][4]}"
        binding.b86.text = if (board[7][5] == 0) "" else "${board[7][5]}"
        binding.b87.text = if (board[8][3] == 0) "" else "${board[8][3]}"
        binding.b88.text = if (board[8][4] == 0) "" else "${board[8][4]}"
        binding.b89.text = if (board[8][5] == 0) "" else "${board[8][5]}"
        //블록 8
        binding.b91.text = if (board[6][6] == 0) "" else "${board[6][6]}"
        binding.b92.text = if (board[6][7] == 0) "" else "${board[6][7]}"
        binding.b93.text = if (board[6][8] == 0) "" else "${board[6][8]}"
        binding.b94.text = if (board[7][6] == 0) "" else "${board[7][6]}"
        binding.b95.text = if (board[7][7] == 0) "" else "${board[7][7]}"
        binding.b96.text = if (board[7][8] == 0) "" else "${board[7][8]}"
        binding.b97.text = if (board[8][6] == 0) "" else "${board[8][6]}"
        binding.b98.text = if (board[8][7] == 0) "" else "${board[8][7]}"
        binding.b99.text = if (board[8][8] == 0) "" else "${board[8][8]}"
        //블록 9
    }



}