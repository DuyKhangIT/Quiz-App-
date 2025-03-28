package com.example.quizapp.presentation.nav_graph

const val AGR_KEY_QUIZ_NUMBER = "ak_quiz_number"
const val AGR_KEY_QUIZ_CATEGORY = "ak_quiz_category"
const val AGR_KEY_QUIZ_DIFFICULTY = "ak_quiz_difficulty"
const val AGR_KEY_QUIZ_TYPE = "ak_quiz_type"
sealed class Routes(val route: String) {

    object HomeScreen : Routes(route = "home_screen")
    object QuizScreen : Routes(route = "quiz_screen/{$AGR_KEY_QUIZ_NUMBER}/{$AGR_KEY_QUIZ_CATEGORY}/{$AGR_KEY_QUIZ_DIFFICULTY}/{$AGR_KEY_QUIZ_TYPE}") {

        fun passQuizArguments(numberOfQuizzes: Int, category: String, difficulty: String, type: String) : String {

            return "quiz_screen/{$AGR_KEY_QUIZ_NUMBER}/{$AGR_KEY_QUIZ_CATEGORY}/{$AGR_KEY_QUIZ_DIFFICULTY}/{$AGR_KEY_QUIZ_TYPE}"
                .replace(
                    oldValue = "${AGR_KEY_QUIZ_NUMBER}",
                    newValue = numberOfQuizzes.toString()
                )
                .replace(
                    oldValue = "${AGR_KEY_QUIZ_CATEGORY}",
                    newValue = category
                )
                .replace(
                    oldValue = "${AGR_KEY_QUIZ_DIFFICULTY}",
                    newValue = difficulty
                )
                .replace(
                    oldValue = "${AGR_KEY_QUIZ_TYPE}",
                    newValue = type
                )
        }
    }

    object ScoreScreen : Routes(route = "score_screen")
}