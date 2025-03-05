package com.rekoj134.gradient_stroke_text_view

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.rekoj134.gradientstroketextview.GradientStrokeTextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        findViewById<GradientStrokeTextView>(R.id.tvHello).setListColor(listOf("#9b5fe0", "#16a4d8", "#60dbe8", "#8bd346", "#efdf48", "#f9a52c", "#d64e12"))
    }
}