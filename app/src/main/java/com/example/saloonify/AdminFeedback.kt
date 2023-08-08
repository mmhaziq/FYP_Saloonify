package com.example.saloonify

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class AdminFeedback:AppCompatActivity() {
    lateinit var database : FirebaseDatabase
    lateinit var databaseReference: DatabaseReference
    //    private lateinit var dbref :DatabaseReference
    private lateinit var feedbackRV :RecyclerView
    private lateinit var feedbacklist :ArrayList<Feedback>

    //    val accept = findViewById<Button>(R.id.accept)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adminfeedback)

        val back =findViewById<ImageView>(R.id.back)
        back.setOnClickListener{
            val intent = Intent(this, AdminDashboard::class.java)
            startActivity(intent)
            finish()
        }

        feedbackRV = findViewById(R.id.recyclerView)
        feedbackRV.layoutManager = LinearLayoutManager(this)
        feedbackRV.setHasFixedSize(true)



        feedbacklist= arrayListOf<Feedback>()
        getAppData()


//
//
    }

    private fun getAppData() {

//        dbref= FirebaseDatabase.getInstance().getReference("appointments")

        database= FirebaseDatabase.getInstance()
        databaseReference= database.getReferenceFromUrl("https://saloonify-59c4c-default-rtdb.firebaseio.com/saloonify/feedback")
        databaseReference.addValueEventListener(object:ValueEventListener{
            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists()){
                    for (appointmentSnapshot in p0.children){
                        val appointment = appointmentSnapshot.getValue(Feedback::class.java)
                        feedbacklist.add(appointment!!)
                    }
                    feedbackRV.adapter = FeedbackAdapter(feedbacklist)
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