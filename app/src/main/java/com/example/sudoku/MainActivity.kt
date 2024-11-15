package com.example.sudoku


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.sudoku.controller.SudokuController
import com.example.sudoku.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        SudokuController(binding).start()
    }
}