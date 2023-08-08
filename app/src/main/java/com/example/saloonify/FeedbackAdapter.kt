package com.example.saloonify

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class FeedbackAdapter(private val feedbacklist: ArrayList<Feedback>):RecyclerView.Adapter<FeedbackAdapter.MyViewHolder>(){

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MyViewHolder {
        val pendingAppView= LayoutInflater.from(p0.context).inflate(R.layout.feedbacks_cardview,p0,false)
        return MyViewHolder(pendingAppView)


    }


    override fun onBindViewHolder(p0: MyViewHolder, p1: Int) {
        val currentitem=feedbacklist[p1]
        p0.fullname.text= currentitem.fullname
        p0.email.text= currentitem.email
        p0.phone.text= currentitem.contact
        p0.feedback.text= currentitem.feedback
    }

    override fun getItemCount(): Int {
        return feedbacklist.size
    }
    class MyViewHolder(feedbackView :View):RecyclerView.ViewHolder(feedbackView){

        val fullname:TextView=feedbackView.findViewById(R.id.a_fullname)
        val email:TextView=feedbackView.findViewById(R.id.a_email)
        val phone:TextView=feedbackView.findViewById(R.id.a_phone)
        val feedback:TextView=feedbackView.findViewById(R.id.a_msg)


        }

        private fun deleteItem(p1: Int) {
            TODO("Not yet implemented")
        }

    }

