package com.example.quizapp.domain.repository

import com.example.quizapp.domain.model.Quiz

interface QuizRepository {


    suspend fun getQuizzes(amount: Int, category: Int, difficulty: String, type: String) : List<Quiz>
}