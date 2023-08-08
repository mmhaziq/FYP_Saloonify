package com.example.saloonify

import android.content.Intent
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.random.Random
import kotlin.random.Random.Default.nextInt

class UserProductListAdapter(private var userProductlst:ArrayList<ProductData>):RecyclerView.Adapter<UserProductListAdapter.UserProductHolder>(){

    lateinit var database : FirebaseDatabase
    lateinit var databaseReference: DatabaseReference

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserProductHolder {
        val productView=LayoutInflater.from(parent.context).inflate(R.layout.user_item_list_cardview,
        parent,false)
        return UserProductHolder(productView)
    }

    override fun onBindViewHolder(holder: UserProductHolder, position: Int) {
        val currentitem = userProductlst[position]
        holder.UserProductName.setText(currentitem.pname.toString())
        holder.UserProductCategory.setText(currentitem.pcategory.toString())
        holder.UserProductPrice.setText(currentitem.pprice.toString())
//        holder.UserProductName.setText(currentitem.pname.toString())

        val bytes = android.util.Base64.decode(currentitem.productImg,Base64.DEFAULT)
        val bitmap =BitmapFactory.decodeByteArray(bytes,0,bytes.size)
        holder.UserProductImg.setImageBitmap(bitmap)

        holder.bind(userProductlst[position], position)
    }

    override fun getItemCount(): Int {
        return userProductlst.size
    }

    fun searchDataList(searchList: ArrayList<ProductData>){
        userProductlst = searchList
        notifyDataSetChanged()
    }

    inner class UserProductHolder(val cartView :View):RecyclerView.ViewHolder(cartView){
        val UserProductName:TextView =cartView.findViewById(R.id.uProductName)
        val UserProductCategory:TextView =cartView.findViewById(R.id.uProductCategory)
        val UserProductPrice:TextView =cartView.findViewById(R.id.uProductPrice)
        val UserProductImg:ImageView =cartView.findViewById(R.id.uProductPicture)

//        val product_name = cartView.findViewById<TextView>(R.id.uProductName)
//        val product_price = cartView.findViewById<TextView>(R.id.uProductPrice)
        fun bind(property: ProductData, index: Int){

    database= FirebaseDatabase.getInstance()
    databaseReference= database.getReferenceFromUrl("https://saloonify-59c4c-default-rtdb.firebaseio.com/saloonify")

    val product_name = cartView.findViewById<TextView>(R.id.uProductName)
            val product_price = cartView.findViewById<TextView>(R.id.uProductPrice)

//            val cnic_value = cnic.text.toString().trim()
            val pname_value = product_name.text.toString().trim()
            val pprice_value = product_price.text.toString().trim()

            val random = Random.nextInt(11, 100).toString()
//
//
//            var idCounter = 1


    val id =databaseReference.push().key



//             val intent = data
//             nodeId=intent.getStringExtra("itm_id").toString()

//            val id =databaseReference.push().key

//    val id = databaseReference.child("cart").push().key

            val addcart: ImageView =cartView.findViewById(R.id.add_to_cart)


            addcart.setOnClickListener{v:View ->



                databaseReference.child("saloonify").addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot){
                        databaseReference.child("cart").child(random).child("pid").setValue(random)
                        databaseReference.child("cart").child(random).child("pname").setValue(pname_value)
                        databaseReference.child("cart").child(random).child("pprice").setValue(pprice_value)

//                        for (i in 1..itemCount) { // Generate 5 incremented IDs, you can adjust the range as needed
////                            val newNodeRef = databaseReference.child("Confirm orders").push() // Create a new child node under "nodes"
////                            newNodeRef.setValue(idCounter.toString()) // Assign the incremented ID as the node's value

//                            databaseReference.child("Confirm orders").child(idCounter.toString()).child("pid").setValue(random)
                            databaseReference.child("Confirm orders products").child(id.toString()).child(id.toString()).child("pname").setValue(pname_value)
//                            databaseReference.child("Confirm orders products").child(id.toString()).child(id.toString()).child("pprice").setValue(pprice_value)


//                        }
//                        idCounter++
                    }
                    override fun onCancelled(error: DatabaseError) {
                        Toast.makeText(cartView.context,error.toString(),Toast.LENGTH_SHORT).show()
                    }

                }
                )

//                databaseReference.child("saloonify").addListenerForSingleValueEvent(object :
//                    ValueEventListener {
//                    override fun onDataChange(dataSnapshot: DataSnapshot){
//
//                        databaseReference.child("appointments").child(cnic_value).removeValue()
//
//                    }
//
//                    override fun onCancelled(error: DatabaseError) {
//                        Toast.makeText(pendingAppView.context,error.toString(),Toast.LENGTH_SHORT).show()
//                    }
//
//                }
//                )
                Toast.makeText(cartView.context,"Added to cart", Toast.LENGTH_SHORT).show()

            }

//    database= FirebaseDatabase.getInstance()
//    databaseReference= database.getReferenceFromUrl("https://saloonify-59c4c-default-rtdb.firebaseio.com/saloonify")
//
//    val sourceNodeRef = databaseReference.child("cart")
//    sourceNodeRef.addValueEventListener(object : ValueEventListener {
//        override fun onDataChange(dataSnapshot: DataSnapshot) {
//            // Get the data from the snapshot
//            val data = dataSnapshot.getValue(ProductData::class.java)
//            // Pass the data to the new activity
//            val intent = Intent(cartView.context, CashOnDeliveryForm::class.java)
//            intent.putExtra("pname", data)
//            cartView.context.startActivity(intent)
//        }
//
//        override fun onCancelled(databaseError: DatabaseError) {
//            // Handle the error
//        }
//    })


}
    }
}