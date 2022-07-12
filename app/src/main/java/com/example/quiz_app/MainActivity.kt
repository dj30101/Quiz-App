package com.example.quiz_app

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.quiz_app.activity.MakingQuizApiActivity
import com.example.quiz_app.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

        binding.btnStart.setOnClickListener {
            if (binding.etName.text.toString().isEmpty()) {
                Toast.makeText(
                    this@MainActivity, getString(R.string.enter_your_name), Toast.LENGTH_SHORT
                ).show()
            } else {
//                val intent = Intent(this@MainActivity,QuizQuestionsActivity::class.java)
//                val intent = Intent(this@MainActivity, DynamicQuizActivity::class.java)
                val intent = Intent(this@MainActivity, MakingQuizApiActivity::class.java)
                intent.putExtra("UserName", binding.etName.text.toString())
                startActivity(intent)
                finish()
            }
        }
    }
}