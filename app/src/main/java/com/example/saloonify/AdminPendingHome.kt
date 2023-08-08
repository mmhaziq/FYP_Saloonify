package com.example.saloonify

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class AdminPendingHome:AppCompatActivity() {
    lateinit var database : FirebaseDatabase
    lateinit var databaseReference: DatabaseReference
    //    private lateinit var dbref :DatabaseReference
    private lateinit var pendinghomeRV :RecyclerView
    private lateinit var homeArraylist :ArrayList<PendingHome>

    //    val accept = findViewById<Button>(R.id.accept)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pending_home)

        val back =findViewById<ImageView>(R.id.back)
        back.setOnClickListener{
            val intent = Intent(this, AdminHomeoptions::class.java)
            startActivity(intent)
            finish()
        }

        pendinghomeRV = findViewById(R.id.recyclerView)
        pendinghomeRV.layoutManager = LinearLayoutManager(this)
        pendinghomeRV.setHasFixedSize(true)



        homeArraylist= arrayListOf<PendingHome>()
        getAppData()


//
//
    }

    private fun getAppData() {

//        dbref= FirebaseDatabase.getInstance().getReference("appointments")

        database= FirebaseDatabase.getInstance()
        databaseReference= database.getReferenceFromUrl("https://saloonify-59c4c-default-rtdb.firebaseio.com/saloonify/home")
        databaseReference.addValueEventListener(object:ValueEventListener{
            override fun onDataChange(p0: DataSnapshot) {
                homeArraylist.clear()
                if (p0.exists()){
                    for (appointmentSnapshot in p0.children){
                        val home = appointmentSnapshot.getValue(PendingHome::class.java)
                        homeArraylist.add(home!!)
                    }
                    pendinghomeRV.adapter = PendingHomeAdapter(homeArraylist)
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