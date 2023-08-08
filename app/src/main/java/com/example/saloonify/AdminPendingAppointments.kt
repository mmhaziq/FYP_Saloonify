package com.example.saloonify

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class AdminPendingAppointments:AppCompatActivity() {
    lateinit var database : FirebaseDatabase
    lateinit var databaseReference: DatabaseReference
//    private lateinit var dbref :DatabaseReference
    private lateinit var pendingappointmentRV :RecyclerView
    private lateinit var appointmentArraylist :ArrayList<PendingAppointment>

//    val accept = findViewById<Button>(R.id.accept)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pending_appointments)

        val back =findViewById<ImageView>(R.id.back)
        back.setOnClickListener{
            val intent = Intent(this, AdminAppointmentoptions::class.java)
            startActivity(intent)
            finish()
        }

        pendingappointmentRV = findViewById(R.id.recyclerView)
        pendingappointmentRV.layoutManager = LinearLayoutManager(this)
        pendingappointmentRV.setHasFixedSize(true)



        appointmentArraylist= arrayListOf<PendingAppointment>()
        getAppData()


//
//
    }

    private fun getAppData() {

//        dbref= FirebaseDatabase.getInstance().getReference("appointments")

        database= FirebaseDatabase.getInstance()
        databaseReference= database.getReferenceFromUrl("https://saloonify-59c4c-default-rtdb.firebaseio.com/saloonify/appointments")
        databaseReference.addValueEventListener(object:ValueEventListener{
            override fun onDataChange(p0: DataSnapshot) {

                appointmentArraylist.clear()
                if (p0.exists()){
                    for (appointmentSnapshot in p0.children){
                        val appointment = appointmentSnapshot.getValue(PendingAppointment::class.java)
                        appointmentArraylist.add(appointment!!)
                    }
                    pendingappointmentRV.adapter = PendingAppointmentAdapter(appointmentArraylist)
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