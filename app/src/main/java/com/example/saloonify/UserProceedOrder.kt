package com.example.saloonify

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView

class UserProceedOrder : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_proceed_order)
        val cod = findViewById<ImageView>(R.id.cashond)
        cod.setOnClickListener {
            val intent = Intent(this, CashOnDeliveryForm::class.java)
            startActivity(intent)
            finish()
        }
    }
}