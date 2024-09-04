package com.example.triviaquiz.repository

import android.provider.ContactsContract.Data
import android.util.Log
import com.example.triviaquiz.data.DataOrException
import com.example.triviaquiz.model.Question
import com.example.triviaquiz.model.QuestionItem
import com.example.triviaquiz.network.QuestionAPI
import javax.inject.Inject

class QuestionRepository @Inject constructor(private val api: QuestionAPI) {
    private val dataOrException =
        DataOrException<ArrayList<QuestionItem>, Boolean, Exception>()

    suspend fun getAllQuestions(): DataOrException<ArrayList<QuestionItem>, Boolean, Exception> {
        try {
            dataOrException.loading = true
            dataOrException.data = api.getAllQuestions()
            if (dataOrException.data.toString().isNotEmpty()) dataOrException.loading = false

        } catch (exception: Exception) {
            dataOrException.e = exception
            Log.d("Exception", "getAllQuestions: ${dataOrException.e!!.localizedMessage}")
        }
        return dataOrException
    }
}