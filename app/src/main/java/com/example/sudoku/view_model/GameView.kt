package com.example.sudoku.view_model

import MusicPlayer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.sudoku.databinding.ActivityMainBinding
import com.example.sudoku.model.Board
import com.example.sudoku.util.Sounds

class GameView(val selectedDifficulty: String, private val binding: ActivityMainBinding) {
    private val sounds = Sounds()
    private val musicPlayer = MusicPlayer(sounds.soundPool, binding)
    private val board = Board(binding, musicPlayer)
    @Composable
    fun Play() {
        val lifecycleOwner = LocalLifecycleOwner.current

        DisposableEffect(lifecycleOwner) {
            val observer = LifecycleEventObserver { _, event ->
                when (event) {
                    Lifecycle.Event.ON_DESTROY -> {
                        musicPlayer.release() // 리소스 해제
                    }
                    else -> {}
                }
            }

            lifecycleOwner.lifecycle.addObserver(observer)
            onDispose {
                lifecycleOwner.lifecycle.removeObserver(observer)
                musicPlayer.release()
            }
        }

        // 보드 초기화 및 버튼 설정
        board.boardInitialize(selectedDifficulty)
        InputView(board, sounds, binding, musicPlayer).button()

        // Compose와 View 결합
        AndroidView(
            factory = { context ->
                binding.root
            }
        )
    }
}
