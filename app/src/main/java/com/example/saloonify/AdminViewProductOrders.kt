package com.example.saloonify

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class AdminViewProductOrders : AppCompatActivity() {
    lateinit var database : FirebaseDatabase
    lateinit var databaseReference: DatabaseReference
    //    private lateinit var dbref :DatabaseReference
    private lateinit var productordersRV : RecyclerView
    private lateinit var productorderlist :ArrayList<ViewProducts>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_view_product_orders)

        productordersRV = findViewById(R.id.productsV)
        productordersRV.layoutManager = LinearLayoutManager(this)
        productordersRV.setHasFixedSize(true)
        productorderlist= arrayListOf<ViewProducts>()
        getAppData()
    }

    private fun getAppData() {
        database= FirebaseDatabase.getInstance()
        databaseReference= database.getReferenceFromUrl("https://saloonify-59c4c-default-rtdb.firebaseio.com/saloonify/Confirm orders")
        databaseReference.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {

                productorderlist.clear()
                if (p0.exists()){
                    for (productSnapshot in p0.children){
                        val product = productSnapshot.getValue(ViewProducts::class.java)
                        productorderlist.add(product!!)
                    }
                    productordersRV.adapter = AdminViewProductsAdapter(productorderlist)
                }
            }

            override fun onCancelled(p0: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}