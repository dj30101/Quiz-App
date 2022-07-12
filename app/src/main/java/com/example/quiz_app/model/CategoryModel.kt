package com.example.quiz_app.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * CategoryModel
 */
data class CategoryModel(
    @Expose @SerializedName("id") val id: Int,
    @Expose @SerializedName("name") val name: String, )