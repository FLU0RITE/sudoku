package com.example.sudoku.view

import android.widget.Toast
import com.example.sudoku.util.App

class OutputView {
    fun threeOverMistakeFail(){
        Toast.makeText(App.context(), "3회 이상 실수로 실패!", Toast.LENGTH_SHORT).show()
    }
    fun fail(){
        Toast.makeText(App.context(), "틀렸습니다!", Toast.LENGTH_SHORT).show()
    }
    fun success(){
        Toast.makeText(App.context(), "성공!", Toast.LENGTH_SHORT).show()
    }
}