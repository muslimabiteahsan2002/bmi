package com.example.myapplicationvideo

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import com.example.myapplicationvideo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var bmiSpinner: Spinner
    private lateinit var weightUnit: TextView
    private lateinit var heightUnit: TextView
    private lateinit var weightLevel: EditText
    private lateinit var heightLevel: EditText
    private lateinit var bmiResult: Button
    private var isMetric = true
    private val bmiSpinnerList = listOf(metric, english)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setFindId()
        setBmiSpinner()
        setBmi()
    }

    private fun setBmi() {
        bmiResult.setOnClickListener {
            val weightStr = weightLevel.text.toString().trim()
            val heightStr = heightLevel.text.toString().trim()
            if (TextUtils.isEmpty(weightLevel.text.toString())) {
                weightLevel.error = "Enter the weight"
            } else if (TextUtils.isEmpty(heightLevel.text.toString())) {
                heightLevel.error = "Enter the height"
            } else {
                val weight = weightStr.toFloat()
                val height = heightStr.toFloat()
                setBmiResult(weight, height)
            }
        }
    }

    private fun setBmiResult(weight: Float, height: Float) {
        if (isMetric) {
            val bmi = weight / height
            setBmiClassification(bmi)
            Toast.makeText(this, "$bmi", Toast.LENGTH_SHORT).show()
        } else {
            val bmi = 703 * (weight / height)
            setBmiClassification(bmi)
            Toast.makeText(this, "$bmi", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setBmiClassification(bmi: Float) {
        val ent = Intent(this, ResultActivity::class.java)
        val classification = if (bmi < 16.0f) {
            "Severely Underweight"
        } else if (bmi in 16.0f..18.4f) {
            "Underweight"
        } else if (bmi in 18.5f..24.9f) {
            "Normal"
        } else if (bmi in 25.0f..29.9f) {
            "Overweight"
        } else if (bmi in  30.0f..34.9f){
            "Moderately Obese"
        } else if (bmi in 35.5f..39.9f) {
            "Severely Obese"
        } else {
            "Morbidly Obese"
        }
        ent.putExtra(scoreKey, bmi)
        ent.putExtra(classificationKey, classification)
        startActivity(ent)
    }

    private fun setBmiSpinner() {
        val bmiAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, bmiSpinnerList)
        bmiSpinner.adapter = bmiAdapter
        bmiSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            @SuppressLint("SetTextI18n")
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when(bmiSpinnerList[position]) {
                   metric -> {
                       weightUnit.text = "KG"
                       heightUnit.text = "M"
                       isMetric = true
                   }
                    english -> {
                        weightUnit.text = "LBS"
                        heightUnit.text = "IN"
                        isMetric = false
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }

    private fun setFindId() {
        bmiSpinner = binding.spinner
        weightUnit = binding.bmiWeightUnit
        heightUnit = binding.bmiHeightUnit
        weightLevel = binding.bmiWeightLevel
        heightLevel = binding.bmiHeightLevel
        bmiResult = binding.bmiResult
    }
}