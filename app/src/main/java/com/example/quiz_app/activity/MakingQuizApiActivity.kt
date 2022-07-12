package com.example.quiz_app.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.quiz_app.R
import com.example.quiz_app.databinding.ActivityMakingQuizApiBinding
import com.example.quiz_app.model.CategoryModel
import com.example.quiz_app.model.CategoryModelResponse
import com.example.quiz_app.retrofit.ApiInterface
import com.google.gson.Gson
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MakingQuizApiActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMakingQuizApiBinding
    private lateinit var retrofitApiInterface: ApiInterface
    private var categoryList: ArrayList<CategoryModel>? = null
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var difficultyList: Array<out String>
    var id: Int = 0
    var difficulty: String = ""
    var userName: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMakingQuizApiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userName= intent.getStringExtra("UserName").toString()

        retrofitApiInterface = ApiInterface.create()
        doApiCallCategory()

        difficultyList = resources.getStringArray(R.array.difficulty_array)
        val difficultyAdapter: ArrayAdapter<String> =
            ArrayAdapter(
                this@MakingQuizApiActivity,
                R.layout.item_spinner,
                R.id.txt_item,
                difficultyList
            )
        binding.spinnerDifficulty.adapter = difficultyAdapter

        setUpListeners()
    }

    private fun doApiCallCategory() {
        val call: Call<JsonObject> = retrofitApiInterface.getCategories()

        call.enqueue(object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                Log.e("success", "success + ${response.code()}")
                if (response.code() == 200) {
                    val response: CategoryModelResponse =
                        Gson().fromJson(response.body(), CategoryModelResponse::class.java)

                    categoryList = response.categories
                    Log.e("size", "size + ${categoryList!!.size}")

                    categoryList!!.add(0, CategoryModel(0, "Any Category"))
                    categoryAdapter = CategoryAdapter(this@MakingQuizApiActivity, categoryList!!)
                    binding.spinnerCategory.adapter = categoryAdapter

                }

            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                Log.e("failure", "failure $t ")
            }
        })
    }

    private fun setUpListeners() {
        binding.spinnerCategory.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long
                ) {
                    id = categoryList!![p2].id
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                }
            }
        binding.spinnerDifficulty.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long
                ) {
                    difficulty = difficultyList[p2]
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                }
            }

        binding.btnSubmit.setOnClickListener {
            if (binding.etNumberOfQuestion.text.toString().isEmpty()) {
                Toast.makeText(
                    this@MakingQuizApiActivity,
                    "Please Enter number of Question.",
                    Toast.LENGTH_SHORT
                ).show()
            } else if ((binding.etNumberOfQuestion.text.toString().toInt()) > 50) {
                Toast.makeText(
                    this@MakingQuizApiActivity,
                    "Value must be less than or equal to 50.",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                val intent = Intent(this, DynamicQuizActivity::class.java)
                intent.putExtra("UserName",userName)
                intent.putExtra("NumberOfQuestion",binding.etNumberOfQuestion.text.toString().toInt())
                intent.putExtra("CategoryId",id)
                intent.putExtra("Difficulty",difficulty)
                startActivity(intent)
                finish()
            }
        }
    }
}