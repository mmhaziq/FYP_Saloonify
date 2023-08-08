package com.example.saloonify

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.saloonify.Model.Signup_model
import com.example.saloonify.databinding.ActivityLoginBinding
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue

class AdminLogin : AppCompatActivity() {
    lateinit var database : FirebaseDatabase
    lateinit var databaseReference: DatabaseReference
    var pattern = Regex("^[a-zA-Z0-9]([-](?![-])|[a-zA-Z0-9]){3,18}[a-zA-Z0-9]\$");
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adminlogin)


        database= FirebaseDatabase.getInstance()
        databaseReference= database.getReferenceFromUrl("https://saloonify-59c4c-default-rtdb.firebaseio.com/saloonify")


        val image = findViewById<ImageView>(R.id.imageView3)
        image.setOnClickListener {
            login()
        }
    }
    private fun login(){
        val EmailLogin = findViewById<EditText>(R.id.email)
        val PasswordLogin = findViewById<EditText>(R.id.password)
        var username = EmailLogin.text.toString().trim()
        var password = PasswordLogin.text.toString().trim()

        if(username.isEmpty() || password.isEmpty()){
            Toast.makeText(this,"Empty fields not allowed", Toast.LENGTH_SHORT).show()

        }else{
            if (username.matches(pattern)){
                databaseReference.child("admins").addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        if (dataSnapshot.hasChild(username)){
                            val getpassword = dataSnapshot.child(username).child("password").getValue().toString()
                            if(getpassword.equals(password)){
                                val intent=Intent(applicationContext, AdminDashboard::class.java)
                                startActivity(intent)
                                finish()
                                Toast.makeText(applicationContext,"Login Successful",Toast.LENGTH_SHORT).show()
                            }else{
                                Toast.makeText(applicationContext,"Username & password doesnt match",Toast.LENGTH_SHORT).show()
                            }
//                            val intent=Intent(applicationContext, AdminDashboard::class.java)
//                            startActivity(intent)
//                            finish()
//                            Toast.makeText(applicationContext,"Login Successful",Toast.LENGTH_SHORT).show()

                        }
                        else{
                            Toast.makeText(applicationContext,"Username does not exist",Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        // Failed to read value

                        Toast.makeText(applicationContext,error.toString(),Toast.LENGTH_SHORT).show()

                    }
                })
               // isEmailExist(username,password)

            }else{
                Toast.makeText(this,"Enter Valid Username", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun isEmailExist(username: String,password: String){
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var list=ArrayList<Signup_model>()
                var isemailexist = false
                for (postsnapshot in dataSnapshot.children){
                    var value = postsnapshot.getValue(Signup_model::class.java)
                    if(value!!.username == username && value!!.password == password){
                        isemailexist=true
                    }
                    list.add(value!!)
                }
                    if (isemailexist){
                        Toast.makeText(applicationContext,"Logged In",Toast.LENGTH_SHORT).show()
                        val intent=Intent(applicationContext, AdminDashboard::class.java)
                        startActivity(intent)
                        finish()
                    }else{
                        Toast.makeText(applicationContext,"Login Failed",Toast.LENGTH_SHORT).show()
                    }
                }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value

                Toast.makeText(applicationContext,error.toString(),Toast.LENGTH_SHORT).show()

            }
        })
    }
//
//    private fun String.matches(username:String):Boolean{
//        val pattern = "^[a-zA-Z0-9]([-](?![-])|[a-zA-Z0-9]){3,18}[a-zA-Z0-9]\$"
//        return pattern.matches(username)
//    }

    override fun onBackPressed() {
        val intent = Intent(this, Login::class.java)
        startActivity(intent)
        finish()
    }

}
