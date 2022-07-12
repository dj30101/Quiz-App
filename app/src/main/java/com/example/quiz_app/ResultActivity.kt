package com.example.quiz_app

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.quiz_app.databinding.ActivityResultBinding

private lateinit var binding: ActivityResultBinding

class ResultActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

        val userName = intent.getStringExtra("UserName").toString()
        binding.tvName.text = userName

        val totalQuestions = intent.getIntExtra("TotalQuestions", 0)
        val correctAnswer = intent.getIntExtra("CorrectAnswers", 0)

        binding.tvScore.text = "Your Score is $correctAnswer out of $totalQuestions"

        binding.btnFinish.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}