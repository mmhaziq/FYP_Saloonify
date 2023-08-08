package com.example.saloonify

import android.graphics.BitmapFactory
import android.media.Image
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class AdminProductAdapter(private val productlist:ArrayList<ProductData>):RecyclerView.Adapter<AdminProductAdapter.productHolder>() {

    private lateinit var mListener:onItemClickListener
    interface onItemClickListener{
        fun onItemClick(position: Int)
    }
    fun setOnItemClickListener(listener: onItemClickListener){
        mListener=listener
    }

    class productHolder(itemView: View,listner: onItemClickListener):RecyclerView.ViewHolder(itemView) {


        lateinit var database : FirebaseDatabase
        lateinit var databaseReference: DatabaseReference

        val itemName: TextView = itemView.findViewById(R.id.productName)
        val itemPrice: TextView = itemView.findViewById(R.id.productPrice)
        val itemCategoryy: TextView = itemView.findViewById(R.id.productCat)
        val itemQuantity: TextView = itemView.findViewById(R.id.productStock)
        val itemimg: ImageView = itemView.findViewById(R.id.productPic)

        init {
            itemView.setOnClickListener{
                listner.onItemClick(adapterPosition)
            }
        }
//        fun bind(property: ProductData, index: Int) {
//            val delete_btn: ImageView = itemView.findViewById(R.id.deleteProduct)
//            delete_btn.setOnClickListener { v: View ->
//
//
//                database = FirebaseDatabase.getInstance()
//                databaseReference = database.getReferenceFromUrl("https://saloonify-59c4c-default-rtdb.firebaseio.com/saloonify")
//
//
//                databaseReference.child("product").addListenerForSingleValueEvent(object :
//                    ValueEventListener {
//                    override fun onDataChange(dataSnapshot: DataSnapshot) {
//
//                        val id =databaseReference.key
//                        databaseReference.child("Product removed").child(id.toString()).removeValue()
//
//                    }
//
//                    override fun onCancelled(error: DatabaseError) {
//                        Toast.makeText(itemView.context, error.toString(), Toast.LENGTH_SHORT)
//                            .show()
//                    }
//
//                }
//                )
//
////                Toast.makeText(itemView.context, "Completed", Toast.LENGTH_SHORT).show()
//
//            }
//
//        }

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): productHolder {
        val productView = LayoutInflater.from(parent.context).inflate(R.layout.admin_inventory_cardview,parent,false)
        return productHolder(productView,mListener)
    }

    override fun onBindViewHolder(holder: productHolder, position: Int) {
        val currentProduct = productlist[position]
        holder.itemName.text = currentProduct.pname.toString()
        holder.itemPrice.text= currentProduct.pprice.toString()
        holder.itemCategoryy.text = currentProduct.pcategory.toString()
        holder.itemQuantity.text = currentProduct.pquantity.toString()
//        holder.bind(productlist[position],position)
        val bytes = android.util.Base64.decode(currentProduct.productImg,android.util.Base64.DEFAULT)

        val bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.size)
        holder.itemimg.setImageBitmap(bitmap)

//        holder.bind(productlist[position],position)
    }

    override fun getItemCount(): Int {
        return productlist.size
    }
}