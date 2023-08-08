package com.example.saloonify

import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.random.Random

class UserCartAdapter(private var cartProductlist:ArrayList<ProductData>): RecyclerView.Adapter<UserCartAdapter.CartProductHolder>(){
    lateinit var database : FirebaseDatabase
    lateinit var databaseReference: DatabaseReference

    private var total: Double = 0.0
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserCartAdapter.CartProductHolder {
        val productView= LayoutInflater.from(parent.context).inflate(R.layout.cart_list_cardview,
            parent,false)
        return CartProductHolder(productView)
    }

    override fun onBindViewHolder(holder: CartProductHolder, position: Int) {
        val currentitem = cartProductlist[position]
        holder.CartProductName.setText(currentitem.pname.toString())
        holder.CartProductPrice.setText(currentitem.pprice.toString())

        val price = currentitem.pprice?.toDoubleOrNull()
        if (price != null) {
            total += price
            val formattedTotal = String.format(Locale.getDefault(), "%.2f", total)
            (holder.itemView.context as? AppCompatActivity)?.findViewById<TextView>(R.id.tvTotal)?.text =
                "Total: $formattedTotal"
        }

        holder.bind(cartProductlist[position], position)
    }

    override fun getItemCount(): Int {
        return cartProductlist.size
    }

    fun calculateTotalPrice(): Double {
        var totalPrice = 0.0
        for (product in cartProductlist) {
//            val price = product.pprice.toDoubleOrNull()
            val price = product.pprice?.toDoubleOrNull() ?: 0.0

            if (price != null) {
                totalPrice += price
            }
        }
        return totalPrice
    }

    inner class CartProductHolder(val cartView : View):RecyclerView.ViewHolder(cartView) {
        val CartProductName: TextView = cartView.findViewById(R.id.cProductName)
        val CartProductPrice: TextView = cartView.findViewById(R.id.cProductPrice)
//        val TotalPriceTextView: TextView = cartView.findViewById(R.id.total)

//        private var random: String? = null
        fun bind(property: ProductData, index: Int){

            val product_name = cartView.findViewById<TextView>(R.id.cProductName)
            val product_price = cartView.findViewById<TextView>(R.id.cProductPrice)

    val total = cartView.findViewById<TextView>(R.id.tvTotal)
            val cname_value = product_name.text.toString().trim()
            val cprice_value = product_price.text.toString().trim()


//           val random = Random.toString().trim()
//            val removecartitem: ImageView =cartView.findViewById(R.id.remove_from_cart)
//            val id = databaseReference.child("cart").key

//            removecartitem.setOnClickListener{v:View ->
//
//
//
//                database= FirebaseDatabase.getInstance()
//                databaseReference= database.getReferenceFromUrl("https://saloonify-59c4c-default-rtdb.firebaseio.com/saloonify")
//
//
//                databaseReference.child("saloonify").addListenerForSingleValueEvent(object :
//                    ValueEventListener {
//                    override fun onDataChange(dataSnapshot: DataSnapshot){
//
//
//                        databaseReference.child("cart").child(random).removeValue()
//
//
//                        Toast.makeText(cartView.context, "Removed from cart", Toast.LENGTH_SHORT).show()
//                    }
//
////                        databaseReference.child("cart").child(random).removeValue()
////
////                    }
//
//                    override fun onCancelled(error: DatabaseError) {
//                        Toast.makeText(cartView.context,error.toString(), Toast.LENGTH_SHORT).show()
//                    }
//
//                }
//                )
//    }
//        }
    }
    }

}
