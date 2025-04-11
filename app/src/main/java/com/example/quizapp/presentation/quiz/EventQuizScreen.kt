package com.example.quizapp.presentation.quiz

sealed class EventQuizScreen {

    data class GetQuizzes(
        val numberOfQuizzes: Int,
        val category: Int,
        val difficulty: String,
        val type: String
    ) : EventQuizScreen()

    data class SetOptionSelected(val quizStateIndex: Int, val selectedOption: Int) :
        EventQuizScreen()

}


