package com.example.saloonify

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class AdminConfirmHome:AppCompatActivity() {
    lateinit var database : FirebaseDatabase
    lateinit var databaseReference: DatabaseReference
    //    private lateinit var dbref :DatabaseReference
    private lateinit var confirmHomeRV :RecyclerView
    private lateinit var confirmHomeArraylist :ArrayList<ConfirmHome>

    //    val accept = findViewById<Button>(R.id.accept)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirmhome)

        val back =findViewById<ImageView>(R.id.back)
        back.setOnClickListener{
            val intent = Intent(this, AdminHomeoptions::class.java)
            startActivity(intent)
            finish()
        }

        confirmHomeRV = findViewById(R.id.recyclerView)
        confirmHomeRV.layoutManager = LinearLayoutManager(this)
        confirmHomeRV.setHasFixedSize(true)



        confirmHomeArraylist= arrayListOf<ConfirmHome>()
        getAppData()


//
//
    }

    private fun getAppData() {

//        dbref= FirebaseDatabase.getInstance().getReference("appointments")

        database= FirebaseDatabase.getInstance()
        databaseReference= database.getReferenceFromUrl("https://saloonify-59c4c-default-rtdb.firebaseio.com/saloonify/confirmed_home")
        databaseReference.addValueEventListener(object:ValueEventListener{
            override fun onDataChange(p0: DataSnapshot) {

                confirmHomeArraylist.clear()
                if (p0.exists()){
                    for (appointmentSnapshot in p0.children){
                        val appointment = appointmentSnapshot.getValue(ConfirmHome::class.java)
                        confirmHomeArraylist.add(appointment!!)
                    }
                    confirmHomeRV.adapter = ConfirmHomeAdapter(confirmHomeArraylist)
                }
            }

            override fun onCancelled(p0: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    override fun onBackPressed() {
        val intent = Intent(this, AdminHomeoptions::class.java)
        startActivity(intent)
        finish()
    }
}