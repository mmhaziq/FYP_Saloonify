package com.example.saloonify

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class UserCartActivity : AppCompatActivity() {
    lateinit var database : FirebaseDatabase
    lateinit var databaseReference: DatabaseReference
    private lateinit var uitemRecyclerView: RecyclerView
    private lateinit var uitemArrayList: ArrayList<ProductData>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_cart)

        val deleteCart = findViewById<ImageView>(R.id.cart_delete)
        val proceedCart = findViewById<ImageView>(R.id.proceedcart)
        val total = findViewById<TextView>(R.id.tvTotal)
        deleteCart.setOnClickListener {
            database= FirebaseDatabase.getInstance()
            databaseReference= database.getReferenceFromUrl("https://saloonify-59c4c-default-rtdb.firebaseio.com/saloonify")


            databaseReference.child("cart").removeValue()
            Toast.makeText(applicationContext,"Your cart is empty now", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, UserProductList::class.java)
            startActivity(intent)
            finish()
        }

        proceedCart.setOnClickListener {
            database= FirebaseDatabase.getInstance()
            databaseReference= database.getReferenceFromUrl("https://saloonify-59c4c-default-rtdb.firebaseio.com/saloonify")

            val total_amount = total.text.toString().trim()
            databaseReference.child("total").child("amount").setValue(total_amount)
//            databaseReference.child("Total amount").

            val intent = Intent(this, UserProceedOrder::class.java)
            startActivity(intent)
            finish()
        }

        uitemRecyclerView=findViewById(R.id.cart_item_list)

        uitemRecyclerView.layoutManager= LinearLayoutManager(this)
        uitemRecyclerView.hasFixedSize()
        uitemArrayList= arrayListOf<ProductData>()
        getItemData()
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
                        uitemRecyclerView.adapter =UserCartAdapter(uitemArrayList)
//                        val totalPrice = (uitemRecyclerView.adapter as? UserCartAdapter)?.calculateTotalPrice()
//                        val totalPriceTextView = findViewById<TextView>(R.id.total)
//                        totalPriceTextView.text = "Total Price: $totalPrice"
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