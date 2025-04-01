package com.example.quizapp.presentation.quiz.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun Prev(){
    QuizInterface(
        onOptionSelected = {},
        qNumber = 1,
    )
}

@Composable
fun QuizInterface(
    onOptionSelected: (Int) -> Unit,
    qNumber: Int,
    modifier: Modifier = Modifier,
) {

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        Column (
            modifier = Modifier.wrapContentHeight()
        ) {

        }
    }
}