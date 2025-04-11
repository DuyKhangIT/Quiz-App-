package com.example.quizapp.data.repository

import android.util.Log
import com.example.quizapp.data.remote.QuizAPI
import com.example.quizapp.domain.model.Quiz
import com.example.quizapp.domain.repository.QuizRepository

class QuizRepositoryImpl(
   private val quizAPI: QuizAPI
) : QuizRepository {


    override suspend fun getQuizzes(
        amount: Int,
        category: Int,
        difficulty: String,
        type: String,
        ) : List<Quiz> {
        val rsp = quizAPI.getQuizzes(amount, category, difficulty, type).results
        Log.d("quiz", rsp.toString())
        return rsp
    }
}