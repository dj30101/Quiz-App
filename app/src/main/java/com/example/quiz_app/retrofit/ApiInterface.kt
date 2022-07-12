package com.example.quiz_app.retrofit

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap
import java.util.concurrent.TimeUnit

interface ApiInterface {
    /*amount=10&category=21&difficulty=easy&type=multiple*/
/*    @GET("api.php")
    fun getQuestion(
        @Query("amount") amount: Int?,
        @Query("category") category: Int?,
        @Query("difficulty") difficulty: String?,
        @Query("type") type: String?
    ): Call<JsonObject>*/

    @GET("api.php")
    fun getQuestion(
        @QueryMap info: HashMap<String, String>?
    ): Call<JsonObject>

    @GET("api_category.php")
    fun getCategories(
    ): Call<JsonObject>

    companion object {

        private val baseUrl = "https://opentdb.com/"

        fun create(): ApiInterface {
            val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

            val gson: Gson = GsonBuilder().setLenient().create()
            val client: OkHttpClient = OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .addNetworkInterceptor(interceptor)
                .build()

            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .baseUrl(baseUrl)
                .build()
            return retrofit.create(ApiInterface::class.java)

        }
    }
}
