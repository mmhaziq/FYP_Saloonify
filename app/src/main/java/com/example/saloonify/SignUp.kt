package com.example.saloonify

import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.saloonify.databinding.ActivitySignupBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class SignUp : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    private lateinit var firebaseAuth: FirebaseAuth
    lateinit var database : FirebaseDatabase
    lateinit var databaseReference: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)

        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()


        binding.loginText.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG)

        binding.loginText.setOnClickListener {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
            finish()
        }

        binding.imageView3.setOnClickListener {
            val email = binding.email.text.toString()
            val name = binding.name.text.toString()
            val number = binding.number.text.toString()
            val pass = binding.password.text.toString()
            val conpass = binding.conpassword.text.toString()

            if (email.isNotEmpty() && pass.isNotEmpty() && conpass.isNotEmpty() && name.isNotEmpty() && number.isNotEmpty()) {
                if (pass == conpass) {
                    firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener {
                        if (it.isSuccessful) {
                            database = FirebaseDatabase.getInstance()
                            databaseReference = database.getReferenceFromUrl("https://saloonify-59c4c-default-rtdb.firebaseio.com/saloonify")

                            databaseReference.child("saloonify").addValueEventListener(object :
                                ValueEventListener {
                                override fun onDataChange(dataSnapshot: DataSnapshot) {


                                    databaseReference.child("users").child(number).child("number").setValue(number)
                                    databaseReference.child("users").child(number).child("email").setValue(email)
                                    databaseReference.child("users").child(number).child("password").setValue(pass)
                                    databaseReference.child("users").child(number).child("name").setValue(name)



                                    Toast.makeText(
                                        applicationContext,
                                        "Welcome",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    val intent = Intent(applicationContext, UserDashboard::class.java)
                                    intent.putExtra("email",email)
                                    startActivity(intent)
                                    finish()
                                }
                                override fun onCancelled(error: DatabaseError) {
                                    TODO("Not yet implemented")
                                }
                            })
                        } else {
                            Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    Toast.makeText(
                        this,
                        "Make sure both the passwords are same",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                Toast.makeText(this, "Empty fields not allowed", Toast.LENGTH_SHORT).show()
            }
        }
    }
    override fun onBackPressed() {
        val intent = Intent(this, Login::class.java)
        startActivity(intent)
        finish()
    }
}
