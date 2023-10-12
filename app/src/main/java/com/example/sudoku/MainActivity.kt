package com.example.sudoku


import android.media.AudioAttributes
import android.media.AudioManager
import android.media.MediaPlayer
import android.media.SoundPool
import android.os.Build
import android.os.Bundle
import android.os.SystemClock
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
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
    var soundFlag = true
    lateinit var binding: ActivityMainBinding

    // 화면 출력
    var soundPool: SoundPool? = null
    var sound1 = 0
    var sound2 = 0
    var sound3 = 0
    var sound4 = 0
    var clock = 0
    var sound1234 = 0.8f
    var clockSound = 1f
    lateinit var music: MediaPlayer
    override fun onDestroy() {
        super.onDestroy()
        music.release()
    }


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        soundPool = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val audioAttributes = AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build()
            SoundPool.Builder()
                .setMaxStreams(6)
                .setAudioAttributes(audioAttributes)
                .build()
        } else {
            SoundPool(6, AudioManager.STREAM_MUSIC, 0)
        }

        sound1 = soundPool!!.load(this, R.raw.sound1, 1)
        sound2 = soundPool!!.load(this, R.raw.sound2, 1)
        sound3 = soundPool!!.load(this, R.raw.sound3, 1)
        sound4 = soundPool!!.load(this, R.raw.sound4, 1)
        clock = soundPool!!.load(this, R.raw.clock, 1)
        music = MediaPlayer.create(this, R.raw.ladyofthe80)

        music.isLooping = true; //무한재생
        music.start();

        boardInit()
        // 처음 보드
        binding.newB.setOnClickListener() {
            boardInit()
            soundPool!!.play(sound1, sound1234, sound1234, 1, 0, 1f)
        }
        // 새게임
        binding.completeB.setOnClickListener {
            soundPool!!.play(sound2, sound1234, sound1234, 1, 0, 1f)
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
                    boardInit()
                }
            }

        }
        // 채점
        binding.musicButton.setOnClickListener {
            if (music.isPlaying) {
                music.pause()
                binding.musicButton.setBackgroundResource(R.drawable.baseline_music_off_24)
            } else {
                music.start()
                binding.musicButton.setBackgroundResource(R.drawable.baseline_music_note_24)
            }
        }
        binding.soundButton.setOnClickListener {
            if (soundFlag) {
                sound1234 = 0f
                clockSound = 0f
                binding.soundButton.setBackgroundResource(R.drawable.baseline_volume_off_24)
                soundFlag = false
            } else {
                sound1234 = 0.8f
                clockSound = 1f
                binding.soundButton.setBackgroundResource(R.drawable.baseline_volume_up_24)
                soundFlag = true
            }
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

    private fun boardInit() {
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
        for (i in 0..8) {
            for (j in 0..8) {
                originBoard[i][j] = board[i][j]
                modifyStatusA[i][j] = 1
            }
        }

        var temp = mutableSetOf<Int>()
        while (temp.size < 51) {
            temp.add(Random.nextInt(9) * 10 + Random.nextInt(9))
        }
        for (i in temp) {
            board[i / 10][i % 10] = 0
        }
        // 무작위 51개 비우기
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


        fun keys(selectedButton: Button, row: Int, col: Int) {
            selectedButton.setOnClickListener {
                soundPool!!.play(clock, clockSound, clockSound, 1, 0, 1f)
                if (modifyStatusA[row][col] == 1) {
                    if (modifyFlag == 0) {
                        selectedButton.setBackgroundResource(R.drawable.selected_box)
                        modifiedButton.add(selectedButton)
                        mRow = row
                        mCol = col
                        modifyFlag = 1
                    } else {
                        blockColor(modifiedButton[0], mRow, mCol)
                        selectedButton.setBackgroundResource(R.drawable.selected_box)
                        modifiedButton.remove(modifiedButton[0])
                        modifiedButton.add(selectedButton)
                        mRow = row
                        mCol = col
                        modifyFlag = 1
                    }
                    binding.one.setOnClickListener {
                        soundPool!!.play(clock, clockSound, clockSound, 1, 0, 1f)
                        selectedButton.text = "1"
                        board[row][col] = 1
                        if (originBoard[row][col] != 1) {
                            mistakeCount += 1
                            binding.mistake.text = "${mistakeCount}"
                            selectedButton.setTextColor(resources.getColor(R.color.red))
                            Toast.makeText(this, "${mistakeCount}회 실수", Toast.LENGTH_SHORT).show()
                        } else {
                            selectedButton.setTextColor(resources.getColor(R.color.purple_500))
                        }
                        blockColor(selectedButton, row, col)
                        if (modifyFlag == 1) {
                            modifyFlag = 0
                            modifiedButton.remove(modifiedButton[0])
                        }
                    }
                    binding.two.setOnClickListener {
                        soundPool!!.play(clock, clockSound, clockSound, 1, 0, 1f)
                        selectedButton.text = "2"
                        board[row][col] = 2
                        if (originBoard[row][col] != 2) {
                            mistakeCount += 1
                            binding.mistake.text = "${mistakeCount}"
                            selectedButton.setTextColor(resources.getColor(R.color.red))
                            Toast.makeText(this, "${mistakeCount}회 실수", Toast.LENGTH_SHORT).show()
                        } else {
                            selectedButton.setTextColor(resources.getColor(R.color.purple_500))
                        }
                        blockColor(selectedButton, row, col)
                        if (modifyFlag == 1) {
                            modifyFlag = 0
                            modifiedButton.remove(modifiedButton[0])
                        }
                    }
                    binding.three.setOnClickListener {
                        soundPool!!.play(clock, clockSound, clockSound, 1, 0, 1f)
                        selectedButton.text = "3"
                        board[row][col] = 3
                        if (originBoard[row][col] != 3) {
                            mistakeCount += 1
                            binding.mistake.text = "${mistakeCount}"
                            selectedButton.setTextColor(resources.getColor(R.color.red))
                            Toast.makeText(this, "${mistakeCount}회 실수", Toast.LENGTH_SHORT).show()
                        } else {
                            selectedButton.setTextColor(resources.getColor(R.color.purple_500))
                        }
                        blockColor(selectedButton, row, col)
                        if (modifyFlag == 1) {
                            modifyFlag = 0
                            modifiedButton.remove(modifiedButton[0])
                        }
                    }
                    binding.four.setOnClickListener {
                        soundPool!!.play(clock, clockSound, clockSound, 1, 0, 1f)
                        selectedButton.text = "4"
                        board[row][col] = 4
                        if (originBoard[row][col] != 4) {
                            mistakeCount += 1
                            binding.mistake.text = "${mistakeCount}"
                            selectedButton.setTextColor(resources.getColor(R.color.red))
                            Toast.makeText(this, "${mistakeCount}회 실수", Toast.LENGTH_SHORT).show()
                        } else {
                            selectedButton.setTextColor(resources.getColor(R.color.purple_500))
                        }
                        blockColor(selectedButton, row, col)
                        if (modifyFlag == 1) {
                            modifyFlag = 0
                            modifiedButton.remove(modifiedButton[0])
                        }
                    }
                    binding.five.setOnClickListener {
                        soundPool!!.play(clock, clockSound, clockSound, 1, 0, 1f)
                        selectedButton.text = "5"
                        board[row][col] = 5
                        if (originBoard[row][col] != 5) {
                            mistakeCount += 1
                            binding.mistake.text = "${mistakeCount}"
                            selectedButton.setTextColor(resources.getColor(R.color.red))
                            Toast.makeText(this, "${mistakeCount}회 실수", Toast.LENGTH_SHORT).show()
                        } else {
                            selectedButton.setTextColor(resources.getColor(R.color.purple_500))
                        }
                        blockColor(selectedButton, row, col)
                        if (modifyFlag == 1) {
                            modifyFlag = 0
                            modifiedButton.remove(modifiedButton[0])
                        }
                    }
                    binding.six.setOnClickListener {
                        soundPool!!.play(clock, clockSound, clockSound, 1, 0, 1f)
                        selectedButton.text = "6"
                        board[row][col] = 6
                        if (originBoard[row][col] != 6) {
                            mistakeCount += 1
                            binding.mistake.text = "${mistakeCount}"
                            selectedButton.setTextColor(resources.getColor(R.color.red))
                            Toast.makeText(this, "${mistakeCount}회 실수", Toast.LENGTH_SHORT).show()
                        } else {
                            selectedButton.setTextColor(resources.getColor(R.color.purple_500))

                        }
                        blockColor(selectedButton, row, col)
                        if (modifyFlag == 1) {
                            modifyFlag = 0
                            modifiedButton.remove(modifiedButton[0])
                        }
                    }
                    binding.seven.setOnClickListener {
                        soundPool!!.play(clock, clockSound, clockSound, 1, 0, 1f)
                        selectedButton.text = "7"
                        board[row][col] = 7
                        if (originBoard[row][col] != 7) {
                            mistakeCount += 1
                            binding.mistake.text = "${mistakeCount}"
                            selectedButton.setTextColor(resources.getColor(R.color.red))
                            Toast.makeText(this, "${mistakeCount}회 실수", Toast.LENGTH_SHORT).show()
                        } else {
                            selectedButton.setTextColor(resources.getColor(R.color.purple_500))
                        }
                        blockColor(selectedButton, row, col)
                        if (modifyFlag == 1) {
                            modifyFlag = 0
                            modifiedButton.remove(modifiedButton[0])
                        }
                    }
                    binding.eight.setOnClickListener {
                        soundPool!!.play(clock, clockSound, clockSound, 1, 0, 1f)
                        selectedButton.text = "8"
                        board[row][col] = 8
                        if (originBoard[row][col] != 8) {
                            mistakeCount += 1
                            binding.mistake.text = "${mistakeCount}"
                            selectedButton.setTextColor(resources.getColor(R.color.red))
                            Toast.makeText(this, "${mistakeCount}회 실수", Toast.LENGTH_SHORT).show()
                        } else {
                            selectedButton.setTextColor(resources.getColor(R.color.purple_500))
                        }
                        blockColor(selectedButton, row, col)
                        if (modifyFlag == 1) {
                            modifyFlag = 0
                            modifiedButton.remove(modifiedButton[0])
                        }
                    }
                    binding.nine.setOnClickListener {
                        soundPool!!.play(clock, clockSound, clockSound, 1, 0, 1f)
                        selectedButton.text = "9"
                        board[row][col] = 9
                        if (originBoard[row][col] != 9) {
                            mistakeCount += 1
                            binding.mistake.text = "${mistakeCount}"
                            selectedButton.setTextColor(resources.getColor(R.color.red))
                            Toast.makeText(this, "${mistakeCount}회 실수", Toast.LENGTH_SHORT).show()
                        } else {
                            selectedButton.setTextColor(resources.getColor(R.color.purple_500))
                        }
                        blockColor(selectedButton, row, col)
                        if (modifyFlag == 1) {
                            modifyFlag = 0
                            modifiedButton.remove(modifiedButton[0])
                        }
                    }
                    binding.deleteB.setOnClickListener {
                        soundPool!!.play(sound3, sound1234, sound1234, 1, 0, 1f)
                        selectedButton.text = ""
                        board[row][col] = 0
                        blockColor(selectedButton, row, col)
                        if (modifyFlag == 1) {
                            modifyFlag = 0
                            modifiedButton.remove(modifiedButton[0])
                        }
                    }
                    binding.hintB.setOnClickListener {
                        soundPool!!.play(sound4, sound1234, sound1234, 1, 0, 1f)
                        selectedButton.text = "${originBoard[row][col]}"
                        board[row][col] = originBoard[row][col]
                        selectedButton.setTextColor(resources.getColor(R.color.purple_500))
                        blockColor(selectedButton, row, col)
                        if (modifyFlag == 1) {
                            modifyFlag = 0
                            modifiedButton.remove(modifiedButton[0])
                        }
                    }
                }
            }
        }

        var buttonListB = arrayListOf<Button>(
            binding.b11,
            binding.b12,
            binding.b13,
            binding.b21,
            binding.b22,
            binding.b23,
            binding.b31,
            binding.b32,
            binding.b33,
            binding.b14,
            binding.b15,
            binding.b16,
            binding.b24,
            binding.b25,
            binding.b26,
            binding.b34,
            binding.b35,
            binding.b36,
            binding.b17,
            binding.b18,
            binding.b19,
            binding.b27,
            binding.b28,
            binding.b29,
            binding.b37,
            binding.b38,
            binding.b39,

            binding.b41,
            binding.b42,
            binding.b43,
            binding.b51,
            binding.b52,
            binding.b53,
            binding.b61,
            binding.b62,
            binding.b63,
            binding.b44,
            binding.b45,
            binding.b46,
            binding.b54,
            binding.b55,
            binding.b56,
            binding.b64,
            binding.b65,
            binding.b66,
            binding.b47,
            binding.b48,
            binding.b49,
            binding.b57,
            binding.b58,
            binding.b59,
            binding.b67,
            binding.b68,
            binding.b69,

            binding.b71,
            binding.b72,
            binding.b73,
            binding.b81,
            binding.b82,
            binding.b83,
            binding.b91,
            binding.b92,
            binding.b93,
            binding.b74,
            binding.b75,
            binding.b76,
            binding.b84,
            binding.b85,
            binding.b86,
            binding.b94,
            binding.b95,
            binding.b96,
            binding.b77,
            binding.b78,
            binding.b79,
            binding.b87,
            binding.b88,
            binding.b89,
            binding.b97,
            binding.b98,
            binding.b99,
        )

        for (b in buttonListB) {
            b.setTextAppearance(R.style.myTitleText)
        }


        for (i in 0..8) {
            for (j in 0..8) {
                if (board[i][j] == 0) {
                    buttonListB[i * 9 + j].isEnabled = true
                } else {
                    buttonListB[i * 9 + j].isEnabled = false
                }
            }
        }

        for (i in 0..8) {
            for (j in 0..8) {
                buttonListB[i * 9 + j].text = if (board[i][j] == 0) "" else "${board[i][j]}"
                if (board[i][j] == 0) keys(selectedButton = buttonListB[i * 9 + j], i, j)
                blockColor(buttonListB[i * 9 + j], i, j)
            }
        }


    }

}