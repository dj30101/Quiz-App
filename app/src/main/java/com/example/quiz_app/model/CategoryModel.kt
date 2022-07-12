package com.example.quiz_app.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CategoryModel(
    @Expose @SerializedName("id") var id: Int,
    @Expose @SerializedName("name") var name: String, )