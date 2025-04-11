package com.example.quizapp.presentation.quiz

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.navigation.NavController
import com.example.quizapp.R
import com.example.quizapp.presentation.home.components.ButtonBox
import com.example.quizapp.presentation.nav_graph.Routes
import com.example.quizapp.presentation.quiz.application.StateQuizScreen
import com.example.quizapp.presentation.quiz.components.QuizAppBar
import com.example.quizapp.presentation.quiz.components.QuizInterface
import com.example.quizapp.presentation.quiz.components.ShimmerEffectQuizInterface
import com.example.quizapp.presentation.util.Constants
import com.example.quizapp.presentation.util.Dimens.LargeSpacerHeight
import com.example.quizapp.presentation.util.Dimens.MediumCornerRadius
import com.example.quizapp.presentation.util.Dimens.MediumPadding
import com.example.quizapp.presentation.util.Dimens.SmallPadding
import com.example.quizapp.presentation.util.Dimens.SmallSpacerHeight
import com.example.quizapp.presentation.util.Dimens.SmallTextSize
import com.example.quizapp.presentation.util.Dimens.VerySmallPadding
import com.example.quizapp.presentation.util.Dimens.VerySmallViewHeight
import kotlinx.coroutines.launch


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun QuizScreen(
    numOfQuiz: Int,
    quizCategory: String,
    quizDifficulty: String,
    quizType: String,
    event: (EventQuizScreen) -> Unit,
    state: StateQuizScreen,
    navController: NavController,
) {

    LaunchedEffect(key1 = Unit) {
        val difficulty = when (quizDifficulty) {
            "Medium" -> "medium"
            "Hard" -> "hard"
            else -> "easy"
        }
        val type = when (quizType) {
            "Multiple Choice" -> "multiple"
            else -> "boolean"
        }

        Log.d("quiz", "bbbb")
        event(
            EventQuizScreen.GetQuizzes(
                numOfQuiz,
                Constants.categoriesMap[quizCategory]!!,
                difficulty,
                type
            )
        )
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

        QuizAppBar(
            quizCategory,
            onBackClick = {
                navController.navigate(Routes.HomeScreen.route) {
                    popUpTo(Routes.HomeScreen.route) {
                        inclusive = true
                    }
                }
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

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(VerySmallViewHeight)
                    .clip(RoundedCornerShape(MediumCornerRadius))
                    .background(
                        colorResource(id = R.color.blue_grey)
                    ),
            )

            Spacer(modifier = Modifier.height(LargeSpacerHeight))

            if (quizFetched(state)) {

                val pagerState = rememberPagerState() { state.quizState.size }

                HorizontalPager(state = pagerState) { index ->
                    QuizInterface(
                        modifier = Modifier.weight(1f),
                        quizState = state.quizState[index],
                        onOptionSelected = { selectedIndex ->
                            event(EventQuizScreen.SetOptionSelected(index, selectedIndex))
                        },
                        qNumber = index + 1
                    )
                }


                val buttonText by remember {
                    derivedStateOf {
                        when (pagerState.currentPage) {
                            0 -> {
                                listOf("", "Next")
                            }

                            state.quizState.size - 1 -> {
                                listOf("Previous", "Submit")
                            }

                            else -> {
                                listOf("Previous", "Next")
                            }
                        }
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = MediumPadding)
                        .navigationBarsPadding()
                ) {
                    val scope = rememberCoroutineScope()
                    if (buttonText[0].isNotEmpty()) {
                        ButtonBox(
                            text = "Previous",
                            padding = SmallPadding,
                            fraction = 0.43f,
                            fontSize = SmallTextSize,
                        ) {
                            scope.launch {
                                pagerState.animateScrollToPage(pagerState.currentPage - 1)
                            }
                        }
                    } else {
                        ButtonBox(
                            text = "",
                            fraction = 0.43f,
                            fontSize = SmallTextSize,
                            borderColor = colorResource(id = R.color.mid_night_blue),
                            containerColor = colorResource(id = R.color.mid_night_blue)
                        ) {

                        }
                    }

                    ButtonBox(
                        text = buttonText[1],
                        padding = SmallPadding,
                        borderColor = colorResource(id = R.color.orange),
                        containerColor = if (pagerState.currentPage == state.quizState.size - 1) colorResource(
                            id = R.color.orange
                        ) else colorResource(id = R.color.dark_slate_blue),
                        fraction = 1f,
                        textColor = colorResource(id = R.color.white),
                        fontSize = SmallTextSize,
                    ) {

                        if (pagerState.currentPage == state.quizState.size - 1) {

                            navController.navigate(
                                route = Routes.ScoreScreen.passNumberOfQuestionAndCorrectAns(
                                    state.quizState.size,
                                    state.score,
                                )
                            )

                        } else {
                            scope.launch {
                                pagerState.animateScrollToPage(pagerState.currentPage + 1)
                            }
                        }

                    }
                }

            }


        }
    }
}

@Composable
fun quizFetched(state: StateQuizScreen): Boolean {
    return when {
        state.isLoading -> {
            ShimmerEffectQuizInterface()
            false
        }

        state.quizState.isNotEmpty() -> {
            true
        }

        else -> {
            Text(text = state.error.toString(), color = colorResource(id = R.color.white))
            false
        }
    }
}