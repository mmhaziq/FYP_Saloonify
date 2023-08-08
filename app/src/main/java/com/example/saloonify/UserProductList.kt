package com.example.saloonify

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class UserProductList : AppCompatActivity() {
    lateinit var database : FirebaseDatabase
    lateinit var databaseReference: DatabaseReference
    private lateinit var uitemRecyclerView: RecyclerView
    lateinit var search: SearchView
//    private lateinit var arrayList: ArrayList<ProductData>
//    private lateinit var adapter: UserProductListAdapter
    private lateinit var uitemArrayList: ArrayList<ProductData>
    private lateinit var searchArrayList: ArrayList<ProductData>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_product_list)


        uitemRecyclerView=findViewById(R.id.user_item_list)

//        uitemArrayList=ArrayList()
//        adapter = UserProductListAdapter(this,uitemArrayList)

        uitemRecyclerView.layoutManager=LinearLayoutManager(this)
        uitemRecyclerView.hasFixedSize()
        uitemArrayList= arrayListOf<ProductData>()
        getItemData()

        val cart = findViewById<ImageButton>(R.id.proceedcart)
        cart.setOnClickListener {
            val intent = Intent(this, UserCartActivity::class.java)
            startActivity(intent)
            finish()
        }

//        uitemRecyclerView.adapter = adapter
//
        search = findViewById(R.id.search)
        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                searchList(newText)
                return true
            }

        })
    }

    fun searchList (text:String){
        val searchList = ArrayList<ProductData>()
        for (dataClass in uitemArrayList){
            if (dataClass.pname?.lowercase()?.contains(text.lowercase()) == true){
                searchList.add(dataClass)
            }
        }
        (uitemRecyclerView.adapter as UserProductListAdapter).searchDataList(searchList)
    }

    private fun getItemData() {
        database= FirebaseDatabase.getInstance()
        databaseReference= database.getReferenceFromUrl("https://saloonify-59c4c-default-rtdb.firebaseio.com/saloonify/product")

        databaseReference.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for (itmsnapshot in snapshot.children){
                        val item =itmsnapshot.getValue(ProductData::class.java)
                        uitemArrayList.add(item!!)
                    }
                    uitemRecyclerView.adapter =UserProductListAdapter(uitemArrayList)
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