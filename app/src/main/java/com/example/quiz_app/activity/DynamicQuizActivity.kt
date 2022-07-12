package com.example.quiz_app.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import com.example.quiz_app.R
import com.example.quiz_app.ResultActivity
import com.example.quiz_app.databinding.ActivityDynamicQuizBinding
import com.example.quiz_app.model.ApiResponse
import com.example.quiz_app.model.DynamicQuestion
import com.example.quiz_app.retrofit.ApiInterface
import com.google.gson.Gson
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DynamicQuizActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityDynamicQuizBinding
    private lateinit var retrofitApiInterface: ApiInterface
    private var dynamicQuestionList: ArrayList<DynamicQuestion>? = null

    private var mUserName: String? = null
    private var numberOfQuestion: Int? = null
    private var categoryId: Int? = null
    private var difficulty: String? = null
    private var mCurrentPosition: Int = 1
    private var mSelectedPosition: Int = 1
    private var mCorrectAnswers: Int = 0
    private var correctAnswerPosition: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDynamicQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mUserName = intent.getStringExtra("UserName")
        numberOfQuestion = intent.getIntExtra("NumberOfQuestion", 0)
        categoryId = intent.getIntExtra("CategoryId", 0)
        difficulty = intent.getStringExtra("Difficulty")

        retrofitApiInterface = ApiInterface.create()
        doAPiCallGetQuestion()
        setUpListeners()

    }

    private fun doAPiCallGetQuestion() {
        val info: HashMap<String, String> = HashMap()
        info["amount"] = numberOfQuestion.toString()
        if (categoryId != 0) {
            info["category"] = categoryId.toString()
        }
        if (!difficulty.equals("Any Difficulty")) {
            info["difficulty"] = difficulty!!.lowercase()
        }
        info["type"] = "multiple"


        val call: Call<JsonObject> = retrofitApiInterface.getQuestion(info)

        call.enqueue(object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                Log.e("success", "success + ${response.code()}")
                if (response.code() == 200) {
                    val response: ApiResponse =
                        Gson().fromJson(response.body(), ApiResponse::class.java)

                    dynamicQuestionList = response.results
                    Log.e("size", "size + ${dynamicQuestionList!!.size}")

                    if (dynamicQuestionList!!.isNotEmpty()) {
                        setQuestion()
                    }
                }


            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                Log.e("failure", "failure $t ")
            }

        })
    }

    @SuppressLint("SetTextI18n")
    private fun setQuestion() {

        val question: DynamicQuestion = dynamicQuestionList!![mCurrentPosition - 1]

        defaultOptionsView()
        if (mCurrentPosition == dynamicQuestionList!!.size) {
            binding.btnSubmit.text = "Finish"
        } else {
            binding.btnSubmit.text = "Submit"
        }
        binding.progressBar.max = dynamicQuestionList!!.size
        binding.progressBar.progress = mCurrentPosition
        binding.tvProgress.text = "$mCurrentPosition/${dynamicQuestionList!!.size}"

        binding.tvQuestion.text =
            HtmlCompat.fromHtml(question.question, HtmlCompat.FROM_HTML_MODE_LEGACY)
        val options: ArrayList<String> = question.incorrectAnswers
        options.add(question.correctAnswer)

        options.shuffle()
        for (option in options) {
            Log.e("option", "After shuffle \n $option")
            if (question.correctAnswer == option) {
                correctAnswerPosition = (options.indexOf(option) + 1)
            }
        }
        binding.tvOptionOne.isEnabled = true
        binding.tvOptionTwo.isEnabled = true
        binding.tvOptionThree.isEnabled = true
        binding.tvOptionFour.isEnabled = true
        binding.tvOptionOne.text = options[0]
        binding.tvOptionTwo.text = options[1]
        binding.tvOptionThree.text = options[2]
        binding.tvOptionFour.text = options[3]

    }

    private fun setUpListeners() {
        binding.tvOptionOne.setOnClickListener(this)
        binding.tvOptionTwo.setOnClickListener(this)
        binding.tvOptionThree.setOnClickListener(this)
        binding.tvOptionFour.setOnClickListener(this)
        binding.btnSubmit.setOnClickListener(this)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.tv_option_one -> {
                selectedOptionView(binding.tvOptionOne, 1)
            }
            R.id.tv_option_two -> {
                selectedOptionView(binding.tvOptionTwo, 2)
            }
            R.id.tv_option_three -> {
                selectedOptionView(binding.tvOptionThree, 3)
            }
            R.id.tv_option_four -> {
                selectedOptionView(binding.tvOptionFour, 4)
            }
            R.id.btn_submit -> {
                if (mSelectedPosition == 0) {
                    mCurrentPosition++
                    when {
                        mCurrentPosition <= dynamicQuestionList!!.size -> {
                            setQuestion()
                        }
                        else -> {
                            val intent = Intent(this, ResultActivity::class.java)
                            intent.putExtra("UserName", mUserName)
                            intent.putExtra("CorrectAnswers", mCorrectAnswers)
                            intent.putExtra("TotalQuestions", dynamicQuestionList!!.size)
                            startActivity(intent)
                            finish()
                        }
                    }
                } else {

                    if (correctAnswerPosition != (mSelectedPosition)) {
                        answerView(mSelectedPosition, R.drawable.wrong_option_border_bg)
                    } else {
                        mCorrectAnswers++
                    }
                    answerView(correctAnswerPosition, R.drawable.correct_option_border_bg)

                    if (mCurrentPosition == dynamicQuestionList!!.size) {
                        binding.btnSubmit.text = getString(R.string.finish)
                    } else {
                        binding.btnSubmit.text = getString(R.string.nextQuestion)
                    }
                    binding.tvOptionOne.isEnabled = false
                    binding.tvOptionTwo.isEnabled = false
                    binding.tvOptionThree.isEnabled = false
                    binding.tvOptionFour.isEnabled = false
                    mSelectedPosition = 0
                }
            }
        }
    }

    @SuppressLint("NewApi")
    private fun defaultOptionsView() {
        val options = ArrayList<TextView>()
        options.add(0, binding.tvOptionOne)
        options.add(1, binding.tvOptionTwo)
        options.add(2, binding.tvOptionThree)
        options.add(3, binding.tvOptionFour)

        for (option in options) {
            option.setTextColor(getColor(R.color.light_grey))
            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(
                this,
                R.drawable.default_option_border_bg
            )

        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun selectedOptionView(textView: TextView, selectedOptionNum: Int) {
        defaultOptionsView()
        mSelectedPosition = selectedOptionNum

        textView.setTextColor(getColor(R.color.light_grey))
        textView.setTypeface(textView.typeface, Typeface.BOLD)
        textView.background = ContextCompat.getDrawable(
            this, R.drawable.selected_option_border_bg
        )
    }

    private fun answerView(answer: Int, drawableView: Int) {
        when (answer) {
            1 -> {
                binding.tvOptionOne.background = ContextCompat.getDrawable(
                    this,
                    drawableView
                )
            }
            2 -> {
                binding.tvOptionTwo.background = ContextCompat.getDrawable(
                    this,
                    drawableView
                )
            }
            3 -> {
                binding.tvOptionThree.background = ContextCompat.getDrawable(
                    this,
                    drawableView
                )
            }
            4 -> {
                binding.tvOptionFour.background = ContextCompat.getDrawable(
                    this,
                    drawableView
                )
            }
        }
    }

}