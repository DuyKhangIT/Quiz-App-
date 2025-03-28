package com.example.quizapp.presentation.quiz

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.quizapp.R
import com.example.quizapp.presentation.quiz.components.QuizAppBar
import com.example.quizapp.presentation.util.Dimens.LargeSpacerHeight
import com.example.quizapp.presentation.util.Dimens.MediumCornerRadius
import com.example.quizapp.presentation.util.Dimens.SmallSpacerHeight
import com.example.quizapp.presentation.util.Dimens.VerySmallPadding
import com.example.quizapp.presentation.util.Dimens.VerySmallViewHeight


@Preview
@Composable
fun PrevQuiz() {

    QuizScreen(
        numOfQuiz = 12,
        quizCategory = "GK",
        quizDifficulty = "Easy",
    )
}


@Composable
fun QuizScreen(
    numOfQuiz: Int,
    quizCategory: String,
    quizDifficulty: String,
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

        QuizAppBar(
            quizCategory,
            onBackClick = {
                
            },
        )


        Column(
            modifier = Modifier
                .padding(VerySmallPadding)
                .fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(LargeSpacerHeight))

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Questions: $numOfQuiz",
                    color = colorResource(id = R.color.blue_grey),
                )
                Text(
                    text = quizDifficulty,
                    color = colorResource(id = R.color.blue_grey),
                )
            }
            
            Spacer(modifier = Modifier.height(SmallSpacerHeight))
            
            Box(modifier = Modifier
                .fillMaxWidth()
                .height(VerySmallViewHeight)
                .clip(RoundedCornerShape(MediumCornerRadius))
                .background(
                    colorResource(id = R.color.blue_grey)
                ),
            )
        }
    }
}