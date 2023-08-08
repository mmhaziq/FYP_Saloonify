package com.example.saloonify

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

//import android.util.Property as

class ConfirmAppointmentAdapter(private val confirmApplist: ArrayList<ConfirmAppointment>):RecyclerView.Adapter<ConfirmAppointmentAdapter.MyViewHolder>(){

    lateinit var database : FirebaseDatabase
    lateinit var databaseReference: DatabaseReference

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MyViewHolder {
        val confirmAppView= LayoutInflater.from(p0.context).inflate(R.layout.confirm_appointment_card_view,p0,false)
        return MyViewHolder(confirmAppView)


    }

    override fun onBindViewHolder(p0: MyViewHolder, p1: Int) {

        val currentitem=confirmApplist[p1]
        p0.fullname.text= currentitem.fullname
        p0.email.text= currentitem.email
        p0.phone.text= currentitem.contact
        p0.cnic.text= currentitem.cnic
        p0.stylist.text= currentitem.stylist
        p0.date.text= currentitem.date
        p0.category.text=currentitem.category
        p0.time.text=currentitem.time

        p0.bind(confirmApplist[p1], p1)

    }




    override fun getItemCount(): Int {
        return confirmApplist.size
    }


    inner class MyViewHolder(val  confirmAppView :View):RecyclerView.ViewHolder(confirmAppView){

        val fullname = confirmAppView.findViewById<TextView>(R.id.a_fullname)
        val email = confirmAppView.findViewById<TextView>(R.id.a_email)
        val phone = confirmAppView.findViewById<TextView>(R.id.a_phone)
        val cnic = confirmAppView.findViewById<TextView>(R.id.a_cnic)
        val stylist = confirmAppView.findViewById<TextView>(R.id.a_stylist)
        val date = confirmAppView.findViewById<TextView>(R.id.a_date)
        val category= confirmAppView.findViewById<TextView>(R.id.capcategory)
        val time=confirmAppView.findViewById<TextView>(R.id.captime)

        fun bind(property: ConfirmAppointment, index: Int){
            val complete:Button=confirmAppView.findViewById(R.id.completeBtn)

            val cnic = confirmAppView.findViewById<TextView>(R.id.a_cnic)
            val cnic_value = cnic.text.toString().trim()
            complete.setOnClickListener{v:View ->



                database= FirebaseDatabase.getInstance()
                databaseReference= database.getReferenceFromUrl("https://saloonify-59c4c-default-rtdb.firebaseio.com/saloonify")


                databaseReference.child("saloonify").addListenerForSingleValueEvent(object :
                    ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot){

                        databaseReference.child("confirmed").child(cnic_value).removeValue()

                    }

                    override fun onCancelled(error: DatabaseError) {
                        Toast.makeText(confirmAppView.context,error.toString(),Toast.LENGTH_SHORT).show()
                    }

                }
                )

                Toast.makeText(confirmAppView.context,"Completed",Toast.LENGTH_SHORT).show()

            }
        }
        }


}
