package com.example.saloonify

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.*
import kotlin.collections.ArrayList

class UserBillAdapter(private var BillProductlist:ArrayList<ProductData>): RecyclerView.Adapter<UserBillAdapter.BillProductHolder>(){
        lateinit var database : FirebaseDatabase
        lateinit var databaseReference: DatabaseReference

//        private var total: Double = 0.0
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserBillAdapter.BillProductHolder {
            val productView= LayoutInflater.from(parent.context).inflate(R.layout.cart_list_cardview,
                parent,false)
            return BillProductHolder(productView)
        }

        override fun onBindViewHolder(holder: BillProductHolder, position: Int) {
            val currentitem = BillProductlist[position]
            holder.CartProductName.setText(currentitem.pname.toString())
            holder.CartProductPrice.setText(currentitem.pprice.toString())

//            val price = currentitem.pprice?.toDoubleOrNull()
//            if (price != null) {
//                total += price
//                val formattedTotal = String.format(Locale.getDefault(), "%.2f", total)
//                (holder.itemView.context as? AppCompatActivity)?.findViewById<TextView>(R.id.tvTotal)?.text =
//                    "Total: $formattedTotal"
//            }

            holder.bind(BillProductlist[position], position)
        }

        override fun getItemCount(): Int {
            return BillProductlist.size
        }

//        fun calculateTotalPrice(): Double {
//            var totalPrice = 0.0
//            for (product in BillProductlist) {
////            val price = product.pprice.toDoubleOrNull()
//                val price = product.pprice?.toDoubleOrNull() ?: 0.0
//
//                if (price != null) {
//                    totalPrice += price
//                }
//            }
//            return totalPrice
//        }

        inner class BillProductHolder(val billView : View): RecyclerView.ViewHolder(billView) {
            val CartProductName: TextView = billView.findViewById(R.id.cProductName)
            val CartProductPrice: TextView = billView.findViewById(R.id.cProductPrice)
//        val TotalPriceTextView: TextView = cartView.findViewById(R.id.total)

            //        private var random: String? = null
            fun bind(property: ProductData, index: Int){

                val product_name = billView.findViewById<TextView>(R.id.cProductName)
                val product_price = billView.findViewById<TextView>(R.id.cProductPrice)

//                val total = billView.findViewById<TextView>(R.id.tvTotal)
//                val cname_value = product_name.text.toString().trim()
//                val cprice_value = product_price.text.toString().trim()


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
