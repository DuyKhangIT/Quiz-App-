package com.example.quizapp.data.di

import com.example.quizapp.data.remote.QuizAPI
import com.example.quizapp.data.repository.QuizRepositoryImpl
import com.example.quizapp.domain.repository.QuizRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object DataModel {

    @Provides
    @Singleton
    fun provideQuizAPI() : QuizAPI {
        return Retrofit.Builder()
            .baseUrl("https://opentdb.com/api.php")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(QuizAPI::class.java)
    }


    fun provideQuizRepository(quizApi: QuizAPI) : QuizRepository {
        return QuizRepositoryImpl(quizApi)

    }
}