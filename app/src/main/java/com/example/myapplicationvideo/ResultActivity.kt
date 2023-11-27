package com.example.myapplicationvideo

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplicationvideo.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val bmiScore = intent.getFloatExtra(scoreKey, 0.0f)
        val bmiClassification = intent.getStringExtra(classificationKey)
        binding.showScore.text = "The score is $bmiScore"
        binding.showClassification.text = "The classification is $bmiClassification"
    }
}