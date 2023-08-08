package com.example.saloonify

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class PendingHomeAdapter(private val pendingHomelist: ArrayList<PendingHome>):RecyclerView.Adapter<PendingHomeAdapter.MyViewHolder>(){


    lateinit var database : FirebaseDatabase
    lateinit var databaseReference: DatabaseReference

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MyViewHolder {
        val pendingAppView= LayoutInflater.from(p0.context).inflate(R.layout.pending_home_cardview,p0,false)
        return MyViewHolder(pendingAppView)


    }


    override fun onBindViewHolder(p0: MyViewHolder, p1: Int) {
        val currentitem=pendingHomelist[p1]
        p0.fullname.text= currentitem.fullname
        p0.email.text= currentitem.email
        p0.phone.text= currentitem.contact
        p0.cnic.text= currentitem.cnic
        p0.address.text = currentitem.address
        p0.stylist.text= currentitem.stylist
        p0.date.text= currentitem.date
        p0.category.text=currentitem.category
        p0.time.text=currentitem.time

        p0.bindhome(pendingHomelist[p1], p1)
    }

    override fun getItemCount(): Int {
        return pendingHomelist.size
    }

    fun deleteItem(p1: Int) {

        pendingHomelist.removeAt(p1)
        notifyDataSetChanged()

    }
    inner class MyViewHolder(val pendingHomeView :View):RecyclerView.ViewHolder(pendingHomeView){

        val fullname:TextView=pendingHomeView.findViewById(R.id.a_fullname)
        val email:TextView=pendingHomeView.findViewById(R.id.a_email)
        val phone:TextView=pendingHomeView.findViewById(R.id.a_phone)
        val cnic:TextView=pendingHomeView.findViewById(R.id.a_cnic)
        val address:TextView=pendingHomeView.findViewById(R.id.a_address)
        val stylist:TextView=pendingHomeView.findViewById(R.id.a_stylist)
        val date:TextView=pendingHomeView.findViewById(R.id.a_date)

        val category:TextView=pendingHomeView.findViewById(R.id.phomecategory)
        val time:TextView=pendingHomeView.findViewById(R.id.phometime)

        fun bindhome(property: PendingHome, index: Int){

            val fullname = pendingHomeView.findViewById<TextView>(R.id.a_fullname)
            val email = pendingHomeView.findViewById<TextView>(R.id.a_email)
            val phone = pendingHomeView.findViewById<TextView>(R.id.a_phone)
            val cnic = pendingHomeView.findViewById<TextView>(R.id.a_cnic)
            val address = pendingHomeView.findViewById<TextView>(R.id.a_address)
            val stylist = pendingHomeView.findViewById<TextView>(R.id.a_stylist)
            val date = pendingHomeView.findViewById<TextView>(R.id.a_date)
            val category = pendingHomeView.findViewById<TextView>(R.id.phomecategory)
            val time = pendingHomeView.findViewById<TextView>(R.id.phometime)
//            val cnic = pendingAppView.findViewById<TextView>(R.id.a_cnic)
//            val cnic_value = cnic.text.toString().trim()

            val fullname_value = fullname.text.toString().trim()
            val email_value = email.text.toString().trim()
            val phone_value = phone.text.toString().trim()
            val cnic_value = cnic.text.toString().trim()

            val address_value = address.text.toString().trim()
            val stylist_value = stylist.text.toString().trim()
            val date_value = date.text.toString().trim()
            val category_value = category.text.toString().trim()
            val time_value = time.text.toString().trim()


        val accept:Button=pendingHomeView.findViewById(R.id.accept)
        val decline:ImageButton=pendingHomeView.findViewById(R.id.delete)
            accept.setOnClickListener{v:View ->

                database= FirebaseDatabase.getInstance()
                databaseReference= database.getReferenceFromUrl("https://saloonify-59c4c-default-rtdb.firebaseio.com/saloonify")


                databaseReference.child("saloonify").addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot){
                        databaseReference.child("confirmed_home").child(cnic_value).child("fullname").setValue(fullname_value)
                        databaseReference.child("confirmed_home").child(cnic_value).child("email").setValue(email_value)
                        databaseReference.child("confirmed_home").child(cnic_value).child("contact").setValue(phone_value)
                        databaseReference.child("confirmed_home").child(cnic_value).child("cnic").setValue(cnic_value)
                        databaseReference.child("confirmed_home").child(cnic_value).child("address").setValue(address_value)
                        databaseReference.child("confirmed_home").child(cnic_value).child("stylist").setValue(stylist_value)
                        databaseReference.child("confirmed_home").child(cnic_value).child("date").setValue(date_value)
                        databaseReference.child("confirmed_home").child(cnic_value).child("category").setValue(category_value)
                        databaseReference.child("confirmed_home").child(cnic_value).child("time").setValue(time_value)
                        databaseReference.child("confirmed_home").child(cnic_value).child("status").setValue("Confirmed")

                    }

                    override fun onCancelled(error: DatabaseError) {
                        Toast.makeText(pendingHomeView.context,error.toString(),Toast.LENGTH_SHORT).show()
                    }

                }
                )

                databaseReference.child("saloonify").addListenerForSingleValueEvent(object :
                    ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot){

                        databaseReference.child("home").child(cnic_value).removeValue()

                    }

                    override fun onCancelled(error: DatabaseError) {
                        Toast.makeText(pendingHomeView.context,error.toString(),Toast.LENGTH_SHORT).show()
                    }

                }
                )

                Toast.makeText(pendingHomeView.context,"Accepted",Toast.LENGTH_SHORT).show()

            }

            decline.setOnClickListener{v:View ->

                database= FirebaseDatabase.getInstance()
                databaseReference= database.getReferenceFromUrl("https://saloonify-59c4c-default-rtdb.firebaseio.com/saloonify")


                databaseReference.child("saloonify").addListenerForSingleValueEvent(object :
                    ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot){
//                        databaseReference.child("appointments").child(cnic_value).removeValue()

                        databaseReference.child("home").child(cnic_value).removeValue()

                    }

                    override fun onCancelled(error: DatabaseError) {
                        Toast.makeText(pendingHomeView.context,error.toString(),Toast.LENGTH_SHORT).show()
                    }

                }
                )

//                deleteItem()
                Toast.makeText(pendingHomeView.context,"Declined",Toast.LENGTH_SHORT).show()

            }



    }
    }
}
