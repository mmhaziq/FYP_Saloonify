package com.example.saloonify

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.saloonify.Model.Signup_model
//import com.example.saloonify.databinding.ActivityLoginBinding
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase

class MakeNewAdmin : AppCompatActivity() {


    var pattern = Regex("^[a-zA-Z0-9]([-](?![-])|[a-zA-Z0-9]){3,18}[a-zA-Z0-9]\$");
    lateinit var database : FirebaseDatabase
    lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_newadmin)

        val image = findViewById<ImageView>(R.id.imageView3)
        image.setOnClickListener{
            signup()
        }


        database= FirebaseDatabase.getInstance()
        databaseReference= database.getReferenceFromUrl("https://saloonify-59c4c-default-rtdb.firebaseio.com/saloonify")

    }
    private fun signup(){
        val email_signup = findViewById<EditText>(R.id.email)
        val password_signup = findViewById<EditText>(R.id.password)
        var username = email_signup.text.toString().trim()
        var pass = password_signup.text.toString().trim()

        if(username.isEmpty() || pass.isEmpty()){
            Toast.makeText(this,"Empty fields not allowed", Toast.LENGTH_SHORT).show()
        }else{
            if (username.matches(pattern)){
                databaseReference.child("saloonify").addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        if (dataSnapshot.hasChild(username)){
                            Toast.makeText(applicationContext,"Username already exists",Toast.LENGTH_SHORT).show()
                        }else{

                            databaseReference.child("admins").child(username).child("username").setValue(username)
                            databaseReference.child("admins").child(username).child("password").setValue(pass)

                            Toast.makeText(applicationContext,"Admin Created", Toast.LENGTH_SHORT).show()
                            val intent = Intent(applicationContext, AdminDashboard::class.java)
                            startActivity(intent)
                            finish()
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        // Failed to read value

                        Toast.makeText(applicationContext,error.toString(),Toast.LENGTH_SHORT).show()

                    }
                })


//                isEmailExist(username,pass)
//                = databaseReference.push().key
//                var username = databaseReference.push().key
////                var emalis = databaseReference.root
//                var model = Signup_model(username.toString(),pass)
//
//                databaseReference.child(username.toString()).setValue(model)
//                Toast.makeText(this,"Successful", Toast.LENGTH_SHORT).show()
//
//                val intent=Intent(this, AdminLogin::class.java)
//                startActivity(intent)
//                finish()
            }else{
                Toast.makeText(this,"Enter Valid Username", Toast.LENGTH_SHORT).show()
            }

        }

    }

    override fun onBackPressed() {
        val intent = Intent(this, AdminDashboard::class.java)
        startActivity(intent)
        finish()
    }

    private fun isEmailExist(username: String,password: String){
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.hasChild(username)){
                    Toast.makeText(applicationContext,"Username already exists",Toast.LENGTH_SHORT).show()
                }else{

                    databaseReference.child("admins").child(username).child("username").setValue(username)
                    databaseReference.child("admins").child(username).child("password").setValue(password)

//                    var username = databaseReference.push().key
////                var emalis = databaseReference.root
//                    var model = Signup_model(username.toString(),password)
//
//                    databaseReference.child(username.toString()).setValue(model)
                    Toast.makeText(applicationContext,"Successful", Toast.LENGTH_SHORT).show()
                    val intent = Intent(applicationContext, AdminDashboard::class.java)
                    startActivity(intent)
                    finish()
                }
//                var list=ArrayList<Signup_model>()
//                var isemailexist = false
//                for (postsnapshot in dataSnapshot.children){
//                    var value = postsnapshot.getValue(Signup_model::class.java)
//                    if(value!!.username == username && value!!.password == password){
//                        isemailexist=true
//                    }
//                    list.add(value!!)
//                }
//                    if (isemailexist){
//                        Toast.makeText(applicationContext,"Logged In",Toast.LENGTH_SHORT).show()
//                        val intent=Intent(applicationContext, AdminDashboard::class.java)
//                        startActivity(intent)
//                        finish()
//                    }else{
//                        Toast.makeText(applicationContext,"Login Failed",Toast.LENGTH_SHORT).show()
//                    }
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value

                Toast.makeText(applicationContext,error.toString(),Toast.LENGTH_SHORT).show()

            }
        })
    }
}


