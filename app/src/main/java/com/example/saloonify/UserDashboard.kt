package com.example.saloonify


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class UserDashboard : AppCompatActivity() {
    lateinit var database : FirebaseDatabase
    lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_userdashboardnew)

//        val sharedPref=this?.getPreferences(Context.MODE_PRIVATE)?:return
//        val isLogin = sharedPref.getString("Email","1")
//        if(isLogin=="1"){
//
//        }else{
//            setText(isLogin)
//        }

        val linearLayout = findViewById<View>(R.id.takeappitment) as LinearLayout

        linearLayout.setOnClickListener {
            val intent = Intent(this, UserBookAppointment::class.java)
            startActivity(intent)
        }

        val HomeService = findViewById<View>(R.id.bookhomeservice) as LinearLayout

        HomeService.setOnClickListener {
            val intent = Intent(this, UserBookHomeService::class.java)
            startActivity(intent)
        }

        val feed = findViewById(R.id.feedback) as ImageView
        feed.setOnClickListener {
            val intent = Intent(this, UserGiveFeedback::class.java)
            startActivity(intent)
        }

        val confirm = findViewById(R.id.inquiry) as ImageView
        confirm.setOnClickListener {
            val intent = Intent(this, UserEnquiryOptions::class.java)
            startActivity(intent)
        }

        val logout = findViewById(R.id.logout) as ImageView
        logout.setOnClickListener {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }

        val virtual = findViewById(R.id.VR) as LinearLayout
        virtual.setOnClickListener {
            val intent = Intent(this, FaceFilterActivity::class.java)
            startActivity(intent)
        }



    }
    private fun setText(email:String){
        database = FirebaseDatabase.getInstance()
        databaseReference = database.getReferenceFromUrl("https://saloonify-59c4c-default-rtdb.firebaseio.com/saloonify")

    }


    override fun onBackPressed() {
        val intent = Intent(this, Login::class.java)
        startActivity(intent)
        finish()
    }

    fun showproductlist(view: View) {
        var i:Intent
        i=Intent(this,UserProductList::class.java)
        startActivity(i)
    }
}

