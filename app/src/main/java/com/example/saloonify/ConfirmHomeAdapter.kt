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

class ConfirmHomeAdapter(private val confirmHomeApplist: ArrayList<ConfirmHome>):RecyclerView.Adapter<ConfirmHomeAdapter.MyViewHolder>(){

    lateinit var database : FirebaseDatabase
    lateinit var databaseReference: DatabaseReference

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MyViewHolder {
        val confirmHomeAppView= LayoutInflater.from(p0.context).inflate(R.layout.confirm_home_cardview,p0,false)
        return MyViewHolder(confirmHomeAppView)


    }

    override fun onBindViewHolder(p0: MyViewHolder, p1: Int) {

        val currentitem=confirmHomeApplist[p1]
        p0.fullname.text= currentitem.fullname
        p0.email.text= currentitem.email
        p0.phone.text= currentitem.contact
        p0.cnic.text= currentitem.cnic
        p0.address.text= currentitem.address
        p0.stylist.text= currentitem.stylist
        p0.date.text= currentitem.date

        p0.category.text=currentitem.category
        p0.time.text=currentitem.time
        p0.bind(confirmHomeApplist[p1], p1)

    }




    override fun getItemCount(): Int {
        return confirmHomeApplist.size
    }


    inner class MyViewHolder(val confirmHomeAppView :View):RecyclerView.ViewHolder(confirmHomeAppView){

        val fullname = confirmHomeAppView.findViewById<TextView>(R.id.a_fullname)
        val email = confirmHomeAppView.findViewById<TextView>(R.id.a_email)
        val phone = confirmHomeAppView.findViewById<TextView>(R.id.a_phone)
        val cnic = confirmHomeAppView.findViewById<TextView>(R.id.a_cnic)
        val address = confirmHomeAppView.findViewById<TextView>(R.id.a_address)
        val stylist = confirmHomeAppView.findViewById<TextView>(R.id.a_stylist)
        val date = confirmHomeAppView.findViewById<TextView>(R.id.a_date)
        val category:TextView=confirmHomeAppView.findViewById(R.id.cappcategory)
        val time:TextView=confirmHomeAppView.findViewById(R.id.capptime)

        fun bind(property: ConfirmHome, index: Int){
            val complete:Button=confirmHomeAppView.findViewById(R.id.completeBtn)

            val cnic = confirmHomeAppView.findViewById<TextView>(R.id.a_cnic)
            val cnic_value = cnic.text.toString().trim()
            complete.setOnClickListener{v:View ->



                database= FirebaseDatabase.getInstance()
                databaseReference= database.getReferenceFromUrl("https://saloonify-59c4c-default-rtdb.firebaseio.com/saloonify")


                databaseReference.child("saloonify").addListenerForSingleValueEvent(object :
                    ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot){

                        databaseReference.child("confirmed_home").child(cnic_value).removeValue()

                    }

                    override fun onCancelled(error: DatabaseError) {
                        Toast.makeText(confirmHomeAppView.context,error.toString(),Toast.LENGTH_SHORT).show()
                    }

                }
                )

                Toast.makeText(confirmHomeAppView.context,"Completed",Toast.LENGTH_SHORT).show()

            }
        }
    }


}
