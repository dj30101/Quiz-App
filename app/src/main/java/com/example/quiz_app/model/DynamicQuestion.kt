package com.example.quiz_app.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class DynamicQuestion(
    @Expose @SerializedName("category") var category: String,
    @Expose @SerializedName("type") var type: String,
    @Expose @SerializedName("difficulty") var difficulty: String,
    @Expose @SerializedName("question") var question: String,
    @Expose @SerializedName("correct_answer") var correctAnswer: String,
    @Expose @SerializedName("incorrect_answers") var incorrectAnswers: ArrayList<String>)
