package com.example.saloonify

import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.saloonify.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth


class Login : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.textAdmin.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG)

        binding.signup.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG)

        binding.forgotpassword.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG)

        binding.textAdmin.setOnClickListener {
            val intent = Intent(this, AdminLogin::class.java)
            startActivity(intent)
            finish()
        }

        binding.signup.setOnClickListener {
            val intent = Intent(this, SignUp::class.java)
            startActivity(intent)
        }

        binding.forgotpassword.setOnClickListener {
            val intent = Intent(this, ForgotPassword::class.java)
            startActivity(intent)
        }
        firebaseAuth= FirebaseAuth.getInstance()
        binding.imageView3.setOnClickListener{
            val email =binding.email.text.toString()
            val pass = binding.password.text.toString()


            if (email.isNotEmpty() && pass.isNotEmpty()){

                firebaseAuth.signInWithEmailAndPassword(email , pass).addOnCompleteListener{
                        if(it.isSuccessful){
                            val intent=Intent(this, UserDashboard::class.java)
                            intent.putExtra("email",email)
                            startActivity(intent)
                            Toast.makeText(this,"Welcome", Toast.LENGTH_SHORT).show()
                        }else{
                            Toast.makeText(this,it.exception.toString(), Toast.LENGTH_SHORT).show()
                        }
                    }
            }else{
                Toast.makeText(this,"Empty fields not allowed", Toast.LENGTH_SHORT).show()
            }









    }}}
