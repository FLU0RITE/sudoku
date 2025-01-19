package com.example.sudoku.view

import MusicPlayer
import androidx.benchmark.json.BenchmarkData
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.sudoku.R
import com.example.sudoku.databinding.ActivityMainBinding
import com.example.sudoku.model.Board
import com.example.sudoku.util.Sounds

class GameView(val selectedDifficulty: String,private val binding: ActivityMainBinding) {
    private val board = Board(binding)
    private val sounds = Sounds()
    private val musicPlayer = MusicPlayer(sounds.soundPool, binding)
    @Composable
    fun Play(){
        val lifecycleOwner = LocalLifecycleOwner.current

        DisposableEffect(lifecycleOwner) {
            val observer = LifecycleEventObserver { _, event ->
                when (event) {
                    Lifecycle.Event.ON_RESUME -> musicPlayer.playBackgroundMusic(sounds.music)
                    Lifecycle.Event.ON_PAUSE, Lifecycle.Event.ON_STOP -> musicPlayer.stopBackgroundMusic()
                    else -> {}
                }
            }

            lifecycleOwner.lifecycle.addObserver(observer)
            onDispose {
                lifecycleOwner.lifecycle.removeObserver(observer)
                musicPlayer.stopBackgroundMusic()
            }
        }

        board.boardInitialize(selectedDifficulty)
        InputView(board,sounds,binding).button()
        AndroidView(
            factory = { context ->
                binding.root
            }
        )
    }
}