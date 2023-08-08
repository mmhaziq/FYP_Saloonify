package com.example.saloonify

import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
//import com.example.saloonify.databinding.ActivityLoginBinding

class AdminDashboard : AppCompatActivity() {

//    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_admindashboardnew)

        val admintext = findViewById(R.id.create_new_admin) as ImageView
        admintext.setOnClickListener {
            val intent = Intent(this, MakeNewAdmin::class.java)
            startActivity(intent)
        }

        val ProductOrder = findViewById(R.id.productOrders) as ImageView
        ProductOrder.setOnClickListener {
            val intent = Intent(this, AdminViewProductOrders::class.java)
            startActivity(intent)
        }

        val linearLayout = findViewById<View>(R.id.appointment) as LinearLayout

        linearLayout.setOnClickListener {
            val intent = Intent(this, AdminAppointmentoptions::class.java)
            startActivity(intent)
        }

        val home = findViewById<View>(R.id.home) as LinearLayout

        home.setOnClickListener {
            val intent = Intent(this, AdminHomeoptions::class.java)
            startActivity(intent)
        }

        val feed = findViewById<View>(R.id.feedbacks) as LinearLayout

        feed.setOnClickListener {
            val intent = Intent(this, AdminFeedback::class.java)
            startActivity(intent)
        }

        val inventory = findViewById<View>(R.id.inventory) as LinearLayout

        inventory.setOnClickListener {
            val intent = Intent(this, AdmininventoryActivity::class.java)
            startActivity(intent)
        }

    }
    override fun onBackPressed() {
        val intent = Intent(this, Login::class.java)
        startActivity(intent)
        finish()
    }
}

