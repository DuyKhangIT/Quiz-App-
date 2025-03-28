package com.example.quizapp.domain.repository

interface QuizRepository {


    suspend fun getQuizzes(amount: Int, category: Int, difficulty: String, type: String)
}