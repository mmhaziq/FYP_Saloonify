package com.example.saloonify

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class AdmininventoryActivity : AppCompatActivity() {
    lateinit var database : FirebaseDatabase
    lateinit var databaseReference: DatabaseReference
    private lateinit var itemRecyclerView: RecyclerView
    private lateinit var itemArrayList: ArrayList<ProductData>
    private val nodeList=ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admininventory)

        itemRecyclerView = findViewById(R.id.recyclerViewProducts)

        itemRecyclerView.layoutManager=LinearLayoutManager(this)
        itemRecyclerView.hasFixedSize()
        itemArrayList= arrayListOf<ProductData>()
        getItemData()




        val back = findViewById<ImageView>(R.id.back)
        back.setOnClickListener {
            val intent = Intent(this, AdminDashboard::class.java)
            startActivity(intent)
            finish()
        }

        val add = findViewById<ImageView>(R.id.add)
        add.setOnClickListener {
            val intent = Intent(this, AdminAddProduct::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun getItemData() {
        database= FirebaseDatabase.getInstance()
        databaseReference= database.getReferenceFromUrl("https://saloonify-59c4c-default-rtdb.firebaseio.com/saloonify/product")

        databaseReference.addValueEventListener(object:ValueEventListener{

            override fun onDataChange(snapshot: DataSnapshot) {
                itemArrayList.clear()
                if(snapshot.exists()){
                    for(itmsnapshot in snapshot.children){
                        val item = itmsnapshot.getValue(ProductData::class.java)
                        itemArrayList.add(item!!)
                        nodeList.add(itmsnapshot.key.toString())
                    }
                    var adapter = AdminProductAdapter(itemArrayList)
                    itemRecyclerView.adapter = adapter

                    adapter.setOnItemClickListener(object :AdminProductAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {
                            val nodePath:String = nodeList[position]
                            val intent =Intent()
                            intent.putExtra("itm_id", nodePath)
                            setResult(2,intent)
                            finish()
                        }

                    })
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }

    override fun onBackPressed() {
        val intent = Intent(this, AdminDashboard::class.java)
        startActivity(intent)
        finish()
    }
}