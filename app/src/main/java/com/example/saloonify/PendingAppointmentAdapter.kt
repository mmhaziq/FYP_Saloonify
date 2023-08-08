package com.example.saloonify

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import java.util.*
import kotlin.collections.ArrayList


class PendingAppointmentAdapter(private val pendingApplist: ArrayList<PendingAppointment>):RecyclerView.Adapter<PendingAppointmentAdapter.MyViewHolder>(){

    lateinit var database : FirebaseDatabase
    lateinit var databaseReference: DatabaseReference

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MyViewHolder {
        val pendingAppView= LayoutInflater.from(p0.context).inflate(R.layout.pending_appointments_cardview,p0,false)
        return MyViewHolder(pendingAppView)


    }

    override fun onBindViewHolder(p0: MyViewHolder, p1: Int) {

        val currentitem=pendingApplist[p1]
        p0.fullname.text= currentitem.fullname
        p0.email.text= currentitem.email
        p0.phone.text= currentitem.contact
        p0.cnic.text= currentitem.cnic
        p0.stylist.text= currentitem.stylist
        p0.date.text= currentitem.date
        p0.category.text=currentitem.category
        p0.time.text=currentitem.time

        p0.bind(pendingApplist[p1], p1)

    }

//    override fun onBindViewHolder(p0: MyViewHolder, p1: Int) {
//        val currentitem=pendingApplist[p1]
//        p0.fullname.text= currentitem.fullname
//        p0.email.text= currentitem.email
//        p0.phone.text= currentitem.contact
//        p0.cnic.text= currentitem.cnic
//        p0.stylist.text= currentitem.stylist
//        p0.date.text= currentitem.date
//
//
//    }


    override fun getItemCount(): Int {
        return pendingApplist.size
    }



    fun deleteItem(p1: Int) {

        pendingApplist.removeAt(p1)
        notifyDataSetChanged()

    }

    inner class MyViewHolder(val pendingAppView :View):RecyclerView.ViewHolder(pendingAppView){

        val fullname = pendingAppView.findViewById<TextView>(R.id.a_fullname)
        val email = pendingAppView.findViewById<TextView>(R.id.a_email)
        val phone = pendingAppView.findViewById<TextView>(R.id.a_phone)
        val cnic = pendingAppView.findViewById<TextView>(R.id.a_cnic)
        val stylist = pendingAppView.findViewById<TextView>(R.id.a_stylist)
        val date = pendingAppView.findViewById<TextView>(R.id.a_date)
        val category:TextView=pendingAppView.findViewById(R.id.pappcategory)
        val time:TextView=pendingAppView.findViewById(R.id.papptime)
        fun bind(property: PendingAppointment, index: Int){



            val fullname = pendingAppView.findViewById<TextView>(R.id.a_fullname)
            val email = pendingAppView.findViewById<TextView>(R.id.a_email)
            val phone = pendingAppView.findViewById<TextView>(R.id.a_phone)
            val cnic = pendingAppView.findViewById<TextView>(R.id.a_cnic)
            val stylist = pendingAppView.findViewById<TextView>(R.id.a_stylist)
            val date = pendingAppView.findViewById<TextView>(R.id.a_date)
            val category = pendingAppView.findViewById<TextView>(R.id.pappcategory)
            val time = pendingAppView.findViewById<TextView>(R.id.papptime)
//            val cnic = pendingAppView.findViewById<TextView>(R.id.a_cnic)
            val cnic_value = cnic.text.toString().trim()

            val fullname_value = fullname.text.toString().trim()
            val email_value = email.text.toString().trim()
            val phone_value = phone.text.toString().trim()
            val stylist_value = stylist.text.toString().trim()
            val date_value = date.text.toString().trim()
            val category_value = category.text.toString().trim()
            val time_value = time.text.toString().trim()



//            val imageView = view.findViewById<ImageView>(R.id.imageView)
//            val description = view.findViewById<TextView>(R.id.tvDescription)
//            val button = view.findViewById<Button>(R.id.button)
//        val fullname:TextView=pendingAppView.findViewById(R.id.a_fullname)
//        val email:TextView=pendingAppView.findViewById(R.id.a_email)
//        val phone:TextView=pendingAppView.findViewById(R.id.a_phone)
//        val cnic:TextView=pendingAppView.findViewById(R.id.a_cnic)
//        val stylist:TextView=pendingAppView.findViewById(R.id.a_stylist)
//        val date:TextView=pendingAppView.findViewById(R.id.a_date)

        val accept:Button=pendingAppView.findViewById(R.id.accept)
        val decline:ImageButton=pendingAppView.findViewById(R.id.delete)

            accept.setOnClickListener{v:View ->

                database= FirebaseDatabase.getInstance()
                databaseReference= database.getReferenceFromUrl("https://saloonify-59c4c-default-rtdb.firebaseio.com/saloonify")


                databaseReference.child("saloonify").addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot){
                        databaseReference.child("confirmed").child(cnic_value).child("fullname").setValue(fullname_value)
                        databaseReference.child("confirmed").child(cnic_value).child("email").setValue(email_value)
                        databaseReference.child("confirmed").child(cnic_value).child("contact").setValue(phone_value)
                        databaseReference.child("confirmed").child(cnic_value).child("cnic").setValue(cnic_value)
                        databaseReference.child("confirmed").child(cnic_value).child("stylist").setValue(stylist_value)
                        databaseReference.child("confirmed").child(cnic_value).child("date").setValue(date_value)
                        databaseReference.child("confirmed").child(cnic_value).child("status").setValue("Confirmed")
                        databaseReference.child("confirmed").child(cnic_value).child("category").setValue(category_value)
                        databaseReference.child("confirmed").child(cnic_value).child("time").setValue(time_value)

                    }

                    override fun onCancelled(error: DatabaseError) {
                        Toast.makeText(pendingAppView.context,error.toString(),Toast.LENGTH_SHORT).show()
                    }

                }
                )

                databaseReference.child("saloonify").addListenerForSingleValueEvent(object :
                    ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot){

                        databaseReference.child("appointments").child(cnic_value).removeValue()

                    }

                    override fun onCancelled(error: DatabaseError) {
                        Toast.makeText(pendingAppView.context,error.toString(),Toast.LENGTH_SHORT).show()
                    }

                }
                )
//                try {
//                    val emailIntent = Intent(Intent.ACTION_SEND)
//                    val subject = "Appointment Confirmation"
//                    val message = "Your appointment is confirmed. Please check the application"
//                    lateinit var uri: Uri
//
//                    emailIntent.type = "plain/text"
//                    emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(email_value))
//                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
//                    emailIntent.putExtra(Intent.EXTRA_STREAM, uri)
//                    emailIntent.putExtra(Intent.EXTRA_TEXT, message)
////                    this.bind(Intent.createChooser(emailIntent,"Sending email"))
//                }catch (t:Throwable){
//                    Toast.makeText(pendingAppView.context,"Request failed",Toast.LENGTH_SHORT).show()
//                }



                Toast.makeText(pendingAppView.context,"Accepted",Toast.LENGTH_SHORT).show()

            }

            decline.setOnClickListener{v:View ->



                database= FirebaseDatabase.getInstance()
                databaseReference= database.getReferenceFromUrl("https://saloonify-59c4c-default-rtdb.firebaseio.com/saloonify")


                databaseReference.child("saloonify").addListenerForSingleValueEvent(object :
                    ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot){

                        databaseReference.child("appointments").child(cnic_value).removeValue()

                    }

                    override fun onCancelled(error: DatabaseError) {
                        Toast.makeText(pendingAppView.context,error.toString(),Toast.LENGTH_SHORT).show()
                    }

                }
                )

//                deleteItem(index)

//                val index = itemCount
//                FirebaseDatabase.getInstance().getReference("appointments").child(cnic_value).setValue(null)


//                println(index)



                Toast.makeText(pendingAppView.context,"Declined",Toast.LENGTH_SHORT).show()

            }



//        private fun deleteItem() {
//            TODO("Not yet implemented")
//        }
//
//        fun deleteItem(index: Int){
//            pendinglist.removeAt(index)
//            notifyDataSetChanged()
//        }




    }}


}
