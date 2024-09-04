package com.example.triviaquiz.components

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.triviaquiz.model.Question
import com.example.triviaquiz.model.QuestionItem
import com.example.triviaquiz.screen.TriviaViewModel
import com.example.triviaquiz.ui.theme.TriviaQuizTheme

@Composable
fun Questions(viewModel: TriviaViewModel) {
    val questions = viewModel.data.value.data?.toMutableList()
    if(viewModel.data.value.loading == true) {
        CircularProgressIndicator()
    } else {
        if (questions != null) {
            QuestionDisplay(question = questions.first())
        }
    }
}

@Composable
fun QuestionDisplay(
    question: QuestionItem,
    //questionIndex: MutableState<Int>,
    //viewModel: TriviaViewModel,
    //onNextClicked: (Int) -> Unit
) {

    val choicesState = remember(question) {
        question.choices.toMutableList()
    }

    val answerState = remember(question) {
        mutableStateOf<Int?>(null)
    }

    val correctAnswerState = remember(question) {
        mutableStateOf<Boolean?>(null)
    }

    val updateAnswer: (Int) -> Unit = remember(question) {
        {
            answerState.value = it
            correctAnswerState.value = choicesState[it] == question.answer
        }
    }

    Surface (
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(4.dp),
        color = Color(0xFF230E45)
    ) {
        Column (
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .padding(5.dp)
        ) {
            QuestionTracker()
            DrawDottedLine(pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f,10f), 0f))
            Spacer(modifier = Modifier.padding(7.dp))
            Column (
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
            ) {
                Text(
                    text = question.question,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 22.sp,
                    color = Color(0xFFFFFFFF),
                    modifier = Modifier
                        .padding(6.dp)
                        .align(alignment = Alignment.Start)
                        .fillMaxHeight(.3f)
                )
                choicesState.forEachIndexed { index, answerText ->
                    Row (
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(3.dp)
                            .fillMaxWidth()
                            .height(45.dp)
                            .border(
                                width = 4.dp, brush = Brush.linearGradient(
                                    colors = listOf(
                                        Color(0xFF7F525D),
                                        Color(0xFFFFFFFF)
                                    )
                                ),
                                shape = RoundedCornerShape(15.dp)
                            )
                            .clip(
                                RoundedCornerShape(
                                    topEndPercent = 50,
                                    bottomEndPercent = 50,
                                    topStartPercent = 50,
                                    bottomStartPercent = 50
                                )
                            )
                            .background(color = Color.Transparent)
                    ) {
                        RadioButton(
                            selected = (answerState.value == index),
                            onClick = {
                                updateAnswer(index)
                            },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = if (correctAnswerState.value == true && index == answerState.value) {
                                    Color(0xFFFFDAD6)
                                }
                                else {
                                    Color(0xFFBA1A1A)
                                }
                            ),
                            modifier = Modifier
                                .padding(16.dp)
                        )
                        Text(
                            text = answerText,
                            textAlign = TextAlign.Center,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun QuestionTracker(
    currentQues: Int = 0,
    totalQues: Int = 1000
) {
    Text(text = buildAnnotatedString {
        withStyle(style = ParagraphStyle(textIndent = TextIndent.None)) {
        withStyle(style = SpanStyle(
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 27.sp
        )) {
            append("Question $currentQues/")
            withStyle(style = SpanStyle(
                color = Color.LightGray,
                fontWeight = FontWeight.Light,
                fontSize = 14.sp
            )) {
                append("$totalQues")
            }
        }
        }
    },
        modifier = Modifier
            .padding(20.dp)
    )
}


@Composable
fun DrawDottedLine(pathEffect: PathEffect) {
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)) {
        drawLine(
            color = Color.White,
            start = Offset(0f,0f),
            end = Offset(size.width, y = 0f),
            pathEffect = pathEffect
        )
    }
}