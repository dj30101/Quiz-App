package com.example.quiz_app.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CategoryModelResponse (
    @Expose @SerializedName("trivia_categories") var categories: ArrayList<CategoryModel>
)
