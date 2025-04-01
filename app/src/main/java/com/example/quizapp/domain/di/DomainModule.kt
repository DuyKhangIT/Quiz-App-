package com.example.quizapp.domain.di

import com.example.quizapp.domain.repository.QuizRepository
import com.example.quizapp.domain.usecases.GetQuizzesUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object DomainModule {


    @Provides
    @Singleton
    fun provideGetQuizzesUseCases(quizRepository: QuizRepository) : GetQuizzesUseCases {
        return GetQuizzesUseCases(quizRepository)
    }

}