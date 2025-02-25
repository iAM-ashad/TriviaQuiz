package com.example.triviaquiz.network

import com.example.triviaquiz.model.Question
import retrofit2.http.GET
import javax.inject.Singleton

@Singleton
interface QuestionAPI {
    @GET("world.json")
    suspend fun getAllQuestions(): Question
}