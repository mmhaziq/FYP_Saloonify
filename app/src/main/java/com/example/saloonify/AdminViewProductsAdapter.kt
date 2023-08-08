package com.example.saloonify

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AdminViewProductsAdapter (private val viewProductlist: ArrayList<ViewProducts>): RecyclerView.Adapter<AdminViewProductsAdapter.MyViewHolder>() {

    lateinit var database : FirebaseDatabase
    lateinit var databaseReference: DatabaseReference

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): AdminViewProductsAdapter.MyViewHolder {
        val viewProductView= LayoutInflater.from(p0.context).inflate(R.layout.productorder_cardview,p0,false)
        return MyViewHolder(viewProductView)


    }

    override fun onBindViewHolder(p0: AdminViewProductsAdapter.MyViewHolder, p1: Int) {

        val currentitem=viewProductlist[p1]
        p0.name.text= currentitem.name
        p0.order.text= currentitem.order_number
        p0.phone.text= currentitem.number
        p0.address.text= currentitem.address


    }
    override fun getItemCount(): Int {
        return viewProductlist.size
    }

    inner class MyViewHolder(val  viewProductView : View):RecyclerView.ViewHolder(viewProductView){

        val name = viewProductView.findViewById<TextView>(R.id.personName)
        val order = viewProductView.findViewById<TextView>(R.id.personOrderNumber)
        val phone = viewProductView.findViewById<TextView>(R.id.personNumber)
        val address = viewProductView.findViewById<TextView>(R.id.personAddress)

    }
}