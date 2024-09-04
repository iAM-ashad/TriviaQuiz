package com.example.triviaquiz.screen

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.triviaquiz.components.Questions

@Composable
fun TriviaHome(viewModel: TriviaViewModel = hiltViewModel()) {
    Questions(viewModel = viewModel)
}