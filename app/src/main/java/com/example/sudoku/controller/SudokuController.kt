package com.example.sudoku.controller

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.sudoku.databinding.ActivityMainBinding

import com.example.sudoku.view_model.GameView
import com.example.sudoku.view_model.StartView

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