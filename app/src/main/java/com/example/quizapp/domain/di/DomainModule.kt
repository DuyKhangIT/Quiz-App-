package com.example.quizapp.domain.di

import com.example.quizapp.data.remote.QuizAPI
import com.example.quizapp.data.repository.QuizRepositoryImpl
import com.example.quizapp.domain.repository.QuizRepository
import com.example.quizapp.domain.usecases.GetQuizzesUseCases
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@InstallIn(SingletonComponent::class)
@Module
object DomainModule {


    fun provideGetQuizzesUseCases(quizRepository: QuizRepository) : GetQuizzesUseCases {
        return GetQuizzesUseCases(quizRepository)
    }

}