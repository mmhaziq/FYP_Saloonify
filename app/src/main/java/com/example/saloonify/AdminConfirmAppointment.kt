package com.example.saloonify

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class AdminConfirmAppointment:AppCompatActivity() {
    lateinit var database : FirebaseDatabase
    lateinit var databaseReference: DatabaseReference
    //    private lateinit var dbref :DatabaseReference
    private lateinit var confirmappointmentRV :RecyclerView
    private lateinit var confirmArraylist :ArrayList<ConfirmAppointment>

    //    val accept = findViewById<Button>(R.id.accept)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirmappointment)

        val back =findViewById<ImageView>(R.id.back)
        back.setOnClickListener{
            val intent = Intent(this, AdminAppointmentoptions::class.java)
            startActivity(intent)
            finish()
        }

        confirmappointmentRV = findViewById(R.id.recyclerView)
        confirmappointmentRV.layoutManager = LinearLayoutManager(this)
        confirmappointmentRV.setHasFixedSize(true)



        confirmArraylist= arrayListOf<ConfirmAppointment>()
        getAppData()


//
//
    }

    private fun getAppData() {

//        dbref= FirebaseDatabase.getInstance().getReference("appointments")

        database= FirebaseDatabase.getInstance()
        databaseReference= database.getReferenceFromUrl("https://saloonify-59c4c-default-rtdb.firebaseio.com/saloonify/confirmed")
        databaseReference.addValueEventListener(object:ValueEventListener{
            override fun onDataChange(p0: DataSnapshot) {

                confirmArraylist.clear()
                if (p0.exists()){
                    for (appointmentSnapshot in p0.children){
                        val appointment = appointmentSnapshot.getValue(ConfirmAppointment::class.java)
                        confirmArraylist.add(appointment!!)
                    }
                    confirmappointmentRV.adapter = ConfirmAppointmentAdapter(confirmArraylist)
                }
            }

            override fun onCancelled(p0: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    override fun onBackPressed() {
        val intent = Intent(this, AdminAppointmentoptions::class.java)
        startActivity(intent)
        finish()
    }
}