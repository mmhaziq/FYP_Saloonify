package com.example.saloonify

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class UserBill : AppCompatActivity() {

    lateinit var database : FirebaseDatabase
    lateinit var databaseReference: DatabaseReference

    private lateinit var uitemRecyclerView: RecyclerView
    private lateinit var uitemArrayList: ArrayList<ProductData>
    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_bill)
        val name = findViewById<TextView>(R.id.bill_name)
//        val amount = findViewById<TextView>(R.id.bill_amount)
        val ordernumber = findViewById<TextView>(R.id.bill_ordernumber)
        val totalamount = findViewById<TextView>(R.id.bill_amount)

        database = FirebaseDatabase.getInstance()
        databaseReference =
            database.getReferenceFromUrl("https://saloonify-59c4c-default-rtdb.firebaseio.com/saloonify")

        databaseReference.child("Orders").get().addOnSuccessListener {


            val billname = it.child("name").value?.toString()
            val billordernumber = it.child("order number").value?.toString()


            name.text = billname ?: ""
            ordernumber.text = billordernumber ?: ""


        }
            .addOnFailureListener {
                Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
            }
        databaseReference.child("total").get().addOnSuccessListener {

            val billtotalamount = it.child("amount").value?.toString()

            totalamount.text = billtotalamount ?: ""

        }.addOnFailureListener {
            Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
        }


        uitemRecyclerView=findViewById(R.id.cart_item_list)

        uitemRecyclerView.layoutManager= LinearLayoutManager(this)
        uitemRecyclerView.hasFixedSize()
        uitemArrayList= arrayListOf<ProductData>()
        getItemData()


        val confirmBill = findViewById<Button>(R.id.confirm_bill)

        confirmBill.setOnClickListener{

            database= FirebaseDatabase.getInstance()
            databaseReference= database.getReferenceFromUrl("https://saloonify-59c4c-default-rtdb.firebaseio.com/saloonify")


            databaseReference.child("cart").removeValue { error, _ ->
                if (error != null) {
                    // Handle the error here
                    Log.e("Firebase", "Failed to remove 'cart' node: $error")
                } else {
                    // Removal succeeded, continue with other operations or intents
                    databaseReference.child("Orders").removeValue()
                    databaseReference.child("total").removeValue()
                    val intent = Intent(this, SplashScreenTick::class.java)
                    startActivity(intent)
                    finish()
                }
            }


//            databaseReference.child("cart").removeValue()
//            databaseReference.child("Orders").removeValue()
//            databaseReference.child("total").removeValue()
//        val intent = Intent(this, SplashScreenTick::class.java)
//        startActivity(intent)
//        finish()
        }


    }
    private fun getItemData() {
        database= FirebaseDatabase.getInstance()
        databaseReference= database.getReferenceFromUrl("https://saloonify-59c4c-default-rtdb.firebaseio.com/saloonify/cart")

        databaseReference.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for (itmsnapshot in snapshot.children){
                        val item =itmsnapshot.getValue(ProductData::class.java)
                        uitemArrayList.add(item!!)
                    }
                    uitemRecyclerView.adapter =UserBillAdapter(uitemArrayList)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
    override fun onBackPressed() {
        val intent = Intent(this, UserDashboard::class.java)
        startActivity(intent)
        finish()
    }
}