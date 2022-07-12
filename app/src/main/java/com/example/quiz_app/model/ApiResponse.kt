package com.example.quiz_app.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * ApiResponse Model
 */
data class ApiResponse(
    @Expose @SerializedName("response_code") var responseCode: Int,
    @Expose @SerializedName("results") var results: ArrayList<DynamicQuestion>
)
