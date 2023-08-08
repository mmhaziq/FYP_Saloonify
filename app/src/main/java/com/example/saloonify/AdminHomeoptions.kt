package com.example.saloonify

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class AdminHomeoptions : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_home_options)

        val back = findViewById<ImageView>(R.id.back)
        back.setOnClickListener {
            val intent = Intent(this, AdminDashboard::class.java)
            startActivity(intent)
            finish()
        }

        val pending = findViewById<TextView>(R.id.pending)
        pending.setOnClickListener {
            val intent = Intent(this, AdminPendingHome::class.java)
            startActivity(intent)
            finish()
        }

        val confirm = findViewById<TextView>(R.id.confirm)
        confirm.setOnClickListener {
            val intent = Intent(this, AdminConfirmHome::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onBackPressed() {
        val intent = Intent(this, AdminDashboard::class.java)
        startActivity(intent)
        finish()
    }
}