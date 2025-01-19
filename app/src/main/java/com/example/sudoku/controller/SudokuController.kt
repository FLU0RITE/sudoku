package com.example.sudoku.controller

import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.sudoku.databinding.ActivityMainBinding
import com.example.sudoku.model.Board

import com.example.sudoku.util.Sounds
import com.example.sudoku.view.GameView
import com.example.sudoku.view.InputView
import com.example.sudoku.view.StartView

class SudokuController(private val binding: ActivityMainBinding) {
    @Composable
    fun Start() {
        val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = "start"
        ) {
            // 시작 화면
            composable("start") {
                StartView(onGameStart = { difficulty ->
                    navController.navigate("game/$difficulty")
                })
            }

            // 게임 화면
            composable("game/{difficulty}") { backStackEntry ->
                val difficulty = backStackEntry.arguments?.getString("difficulty") ?: "쉬움"
                GameView(selectedDifficulty = difficulty,binding).Play()
            }
        }

    }
}