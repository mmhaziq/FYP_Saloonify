package com.example.saloonify

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.saloonify.databinding.ActivityEnquireAppointmentsBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class UserEnquiryAppointments : AppCompatActivity(){

    lateinit var database : FirebaseDatabase
    lateinit var databaseReference: DatabaseReference



    private lateinit var binding : ActivityEnquireAppointmentsBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEnquireAppointmentsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.readdataBtn.setOnClickListener{
            val cnic : String = binding.etcnic.text.toString()

            if (cnic.isNotEmpty()){
                readData(cnic)
            }else{
                Toast.makeText(this, "Please enter CNIC", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun readData(cnic: String) {


        database= FirebaseDatabase.getInstance()
        databaseReference= database.getReferenceFromUrl("https://saloonify-59c4c-default-rtdb.firebaseio.com/saloonify/confirmed")

        databaseReference.child(cnic).get().addOnSuccessListener {

            if(it.exists()){

                val fullname = it.child("fullname").value
                val date = it.child("date").value
                val status = it.child("status").value

                Toast.makeText(this, "Congrats. Your booking is confirmed", Toast.LENGTH_SHORT).show()

                binding.etcnic.text.clear()
                binding.tvFullName.text = fullname.toString()
                binding.tvDate.text = date.toString()
                binding.tvStatus.text = status.toString()


            }else{
                Toast.makeText(this, "Sorry, Booking is either cancelled or does not exist.", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener{
            Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
        }

    }
    override fun onBackPressed() {
        val intent = Intent(this, UserEnquiryOptions::class.java)
        startActivity(intent)
        finish()
    }
}