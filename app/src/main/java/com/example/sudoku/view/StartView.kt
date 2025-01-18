package com.example.sudoku.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sudoku.R

@Composable
fun StartView(onGameStart: (String) -> Unit
) {
    var selectedDifficulty by remember { mutableStateOf("쉬움") }
    val difficulties = listOf("쉬움", "보통", "어려움")

    Scaffold{ padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color.Black),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.president),
                contentDescription = "로고 이미지",
                modifier = Modifier.size(200.dp)
            )
            // 제목
            Text(
                text = "스덕후",
                style = MaterialTheme.typography.h2,
                modifier = Modifier.padding(bottom = 32.dp),
                color = Color.White
            )

            // 난이도 선택
            Text(
                text = "난이도",
                style = MaterialTheme.typography.h4,
                modifier = Modifier.padding(bottom = 16.dp),
                color = Color.White
            )
            Row {
                difficulties.forEach { difficulty ->
                    Row(
                        modifier = Modifier
                            .padding(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = (selectedDifficulty == difficulty),
                            onClick = { selectedDifficulty = difficulty }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = difficulty,
                            color = Color.White,
                            style = MaterialTheme.typography.h6,
                            modifier = Modifier.clickable {
                                selectedDifficulty = difficulty
                            }
                        )
                    }
                }
            }


            Spacer(modifier = Modifier.height(124.dp))

            // 시작 버튼
            Button(
                onClick = {
                    onGameStart(selectedDifficulty)
                },
                modifier = Modifier.size(200.dp, 50.dp),
                colors = ButtonDefaults.buttonColors(Color.Black),
            ) {
                Text(text = "게임 시작", color = Color.White)
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewStatView() {
    StartView(onGameStart = {})
}
