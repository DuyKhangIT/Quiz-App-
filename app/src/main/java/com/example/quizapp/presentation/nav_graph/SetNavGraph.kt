package com.example.quizapp.presentation.nav_graph

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.quizapp.presentation.home.HomeScreen
import com.example.quizapp.presentation.home.application.HomeScreenViewModel
import com.example.quizapp.presentation.home.application.StateHomeScreen
import com.example.quizapp.presentation.quiz.QuizScreen
import com.example.quizapp.presentation.quiz.application.QuizViewModel
import com.example.quizapp.presentation.score.ScoreScreen

@Composable
fun SetNavGraph() {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.HomeScreen.route) {


        composable(route = Routes.HomeScreen.route) {
            val viewModel: HomeScreenViewModel = hiltViewModel()
            val state: StateHomeScreen by viewModel.homeState.collectAsState()
            HomeScreen(
                state = state,
                event = viewModel::onEvent,
                navController = navController,
            )
        }

        composable(
            route = Routes.QuizScreen.route,
            arguments = listOf(
                navArgument(AGR_KEY_QUIZ_NUMBER) {
                    type = NavType.IntType
                },
                navArgument(AGR_KEY_QUIZ_CATEGORY) {
                    type = NavType.StringType
                },
                navArgument(AGR_KEY_QUIZ_DIFFICULTY) {
                    type = NavType.StringType
                },
                navArgument(AGR_KEY_QUIZ_TYPE) {
                    type = NavType.StringType
                }
            )
        ) {

            val numberOfQuizzes = it.arguments?.getInt(AGR_KEY_QUIZ_NUMBER)
            val category = it.arguments?.getString(AGR_KEY_QUIZ_CATEGORY)
            val difficulty = it.arguments?.getString(AGR_KEY_QUIZ_DIFFICULTY)
            val type = it.arguments?.getString(AGR_KEY_QUIZ_TYPE)

            val quizViewModel: QuizViewModel = hiltViewModel()
            val state by quizViewModel.quizList.collectAsState()
            QuizScreen(
                numOfQuiz = numberOfQuizzes!!,
                quizCategory = category!!,
                quizDifficulty = difficulty!!,
                quizType = type!!,
                event = quizViewModel::onEvent,
                state = state,
                navController = navController,
            )
        }

        composable(
            route = Routes.ScoreScreen.route,
            arguments = listOf(
                navArgument(NOQ_KEY) {
                    type = NavType.IntType
                },
                navArgument(CORRECT_ANS_KEY) {
                    type = NavType.IntType
                }
            )
        ) {
            val numberOfQuestions = it.arguments?.getInt(NOQ_KEY)
            val numberOfCorrectAns = it.arguments?.getInt(CORRECT_ANS_KEY)

            ScoreScreen(
                numOfQuestions = numberOfQuestions!!,
                numOfCorrectAns = numberOfCorrectAns!!,
                navController = navController,
            )
        }
    }
}