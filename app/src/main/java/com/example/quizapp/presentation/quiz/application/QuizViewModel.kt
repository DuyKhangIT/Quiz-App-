package com.example.quizapp.presentation.quiz.application

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizapp.common.Resource
import com.example.quizapp.domain.model.Quiz
import com.example.quizapp.domain.usecases.GetQuizzesUseCases
import com.example.quizapp.presentation.quiz.EventQuizScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuizViewModel @Inject constructor(private val getQuizzesUseCases: GetQuizzesUseCases) :
    ViewModel() {

    private val _quizList = MutableStateFlow(StateQuizScreen())
    val quizList = _quizList

    fun onEvent(event: EventQuizScreen) {
        when (event) {
            is EventQuizScreen.GetQuizzes -> {
                getQuizzes(event.numberOfQuizzes, event.category, event.difficulty, event.type)
            }

            is EventQuizScreen.SetOptionSelected -> {
                updateQuizStateList(event.quizStateIndex, event.selectedOption)
            }

            else -> {}
        }
    }

    private fun updateQuizStateList(quizStateIndex: Int, selectedOption: Int) {

        val updateQuizStateList = mutableListOf<QuizState>()
        quizList.value.quizState.forEachIndexed { index, quizState ->
            updateQuizStateList.add(
                if (quizStateIndex == index) {
                    quizState.copy(selectedOptions = selectedOption)
                } else quizState
            )
        }
        _quizList.value = quizList.value.copy(quizState = updateQuizStateList)

        updateScore(_quizList.value.quizState[quizStateIndex])
    }

    private fun updateScore(quizState: QuizState) {
        if (quizState.selectedOptions != -1) {
            val correctAnswer = quizState.quiz?.correct_answer
            val selectedAnswer = quizState.selectedOptions?.let {
                quizState.shuffledOptions[it].replace("&quot;", "\"")
                    .replace("&#039;", "\'")
            }
            Log.d("check", "$correctAnswer -> $selectedAnswer")
            if (correctAnswer == selectedAnswer) {
                val previousScore = _quizList.value.score
                _quizList.value = quizList.value.copy(score = previousScore + 1)
            }
        }
    }


    private fun getQuizzes(amount: Int, category: Int, difficulty: String, type: String) {
        viewModelScope.launch {
            getQuizzesUseCases(amount, category, difficulty, type).collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        Log.d("quiz", "loading")
                        _quizList.value = StateQuizScreen(isLoading = true)
                    }

                    is Resource.Success -> {
                        Log.d("quiz", "success")
                        val listOfQuizState: List<QuizState> = getListOfQuizState(resource.data)
                        _quizList.value = StateQuizScreen(quizState = listOfQuizState)
                    }

                    is Resource.Error -> {
                        Log.d("quiz", "error")
                        _quizList.value = StateQuizScreen(error = resource.message.toString())
                    }
                }
            }
        }


    }


    private fun getListOfQuizState(data: List<Quiz>?): List<QuizState> {

        val listOfQuizState = mutableListOf<QuizState>()

        for (quiz in data!!) {
            val shuffledOptions = mutableListOf<String>().apply {
                add(quiz.correct_answer)
                addAll(quiz.incorrect_answers)
            }
            listOfQuizState.add(QuizState(quiz, shuffledOptions.shuffled(), -1))
        }

        return listOfQuizState
    }

}