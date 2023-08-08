package com.example.saloonify

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class SplashScreenTick : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen_tick)

        val continue_mm = findViewById<Button>(R.id.cont)
        continue_mm.setOnClickListener{
            val intent = Intent(this, UserDashboard::class.java)
            startActivity(intent)
            finish()
        }
    }
}