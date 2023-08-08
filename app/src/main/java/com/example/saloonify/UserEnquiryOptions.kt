package com.example.saloonify

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class UserEnquiryOptions : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_inquire)

        val back = findViewById<ImageView>(R.id.back)
        back.setOnClickListener {
            val intent = Intent(this, UserDashboard::class.java)
            startActivity(intent)
            finish()
        }

        val c_app = findViewById<TextView>(R.id.appointments)
        c_app.setOnClickListener {
            val intent = Intent(this, UserEnquiryAppointments::class.java)
            startActivity(intent)
            finish()
        }

        val c_hs = findViewById<TextView>(R.id.homeservices)
        c_hs.setOnClickListener {
            val intent = Intent(this, UserEnquiryHome::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onBackPressed() {
        val intent = Intent(this, UserDashboard::class.java)
        startActivity(intent)
        finish()
    }
}