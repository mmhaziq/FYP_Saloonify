package com.example.saloonify

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase

class ForgotPassword : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgotpassword)

        val email = findViewById<EditText>(R.id.email)
        val btntick = findViewById<ImageView>(R.id.imageView3)

        btntick.setOnClickListener{
                    if (email.text.toString().isEmpty()){
                        Toast.makeText(this,"Enter email",Toast.LENGTH_SHORT).show()
                    }else{
                        forgotpassword(email)
                         }
        }}

    private fun forgotpassword(email :EditText){
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.sendPasswordResetEmail(email.text.toString()).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this,"Email sent",Toast.LENGTH_SHORT).show()
                        val intent=Intent(this, Login::class.java)
                        startActivity(intent)
                        finish()
                    }else{
                        Toast.makeText(this,"Enter valid email",Toast.LENGTH_SHORT).show()
                    }

            }
    }

    override fun onBackPressed() {
        val intent = Intent(this, Login::class.java)
        startActivity(intent)
        finish()
    }

    private fun isValidEmail(email:String):Boolean{
        val pattern = Patterns.EMAIL_ADDRESS
        return pattern.matcher(email).matches()
    }
}
