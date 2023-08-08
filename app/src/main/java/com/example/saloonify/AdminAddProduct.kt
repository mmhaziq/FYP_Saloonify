package com.example.saloonify

import android.R
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.saloonify.databinding.ActivityAdminAddProductBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.io.ByteArrayOutputStream

class AdminAddProduct : AppCompatActivity() {
    var productImg:String? =""
    var nodeId= ""
    var productcategory = arrayOf("Skin care","Hair care","Color Cosmetics","Fragrances","Personal Care")



    lateinit var database : FirebaseDatabase
    lateinit var databaseReference: DatabaseReference

    private lateinit var binding: ActivityAdminAddProductBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminAddProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val arrayAdapter= ArrayAdapter<String>(this, R.layout.simple_spinner_item,productcategory)

        binding.productCategory.adapter = arrayAdapter

        binding.productCategory.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
//                Toast.makeText(applicationContext,"hehe",Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
    }

    fun upload_product(view: View) {
        val pName = binding.productName.text.toString()
        val pPrice = binding.productPrice.text.toString()
        val pCategory = binding.productCategory.selectedItem.toString()
        val pQuantity = binding.productQuantity.text.toString()
        database= FirebaseDatabase.getInstance()
        databaseReference= database.getReferenceFromUrl("https://saloonify-59c4c-default-rtdb.firebaseio.com/saloonify")


        val products= ProductData(pName,pPrice,pCategory,pQuantity,productImg)

        val id =databaseReference.push().key

        databaseReference.child("product").child(id.toString()).setValue(products).addOnSuccessListener {
            binding.productName.text.clear()
            binding.productPrice.text.clear()
            binding.productQuantity.text.clear()
            productImg = ""
            Toast.makeText(applicationContext,"Product inserted", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener{
            binding.productName.text.clear()
            binding.productPrice.text.clear()
            binding.productQuantity.text.clear()
            productImg = ""
            Toast.makeText(applicationContext,"Product can not be inserted", Toast.LENGTH_SHORT).show()
        }
//        databaseReference.child("product").child(app_phone).child("email").setValue(app_email)
//        databaseReference.child("product").child(app_phone).child("contact").setValue(app_phone)




    }
    fun upload_img(view: View) {
        var myfileintent = Intent(Intent.ACTION_GET_CONTENT)
        myfileintent.setType("image/*")
        ActivityResultLauncher.launch(myfileintent)
    }



    private val ActivityResultLauncher = registerForActivityResult<Intent,ActivityResult>(
        ActivityResultContracts.StartActivityForResult()
    ){result:ActivityResult ->
        if(result.resultCode== RESULT_OK){
            val uri = result.data!!.data
            try {

                val inputStream = contentResolver.openInputStream(uri!!)
                val myBitmap = BitmapFactory.decodeStream(inputStream)
                val stream = ByteArrayOutputStream()
                myBitmap.compress(Bitmap.CompressFormat.PNG,100,stream)

                val bytes =stream.toByteArray()
                productImg = Base64.encodeToString(bytes,Base64.DEFAULT)

                binding.productPic.setImageBitmap(myBitmap)
                inputStream!!.close()
                Toast.makeText(this,"Image selected",Toast.LENGTH_SHORT).show()


            }catch (ex: Exception){
                Toast.makeText(this,ex.message.toString(),Toast.LENGTH_LONG).show()
            }
        }

    }

    private val itemResultLauncher = registerForActivityResult<Intent,ActivityResult>(
        ActivityResultContracts.StartActivityForResult()
    ){result:ActivityResult ->
        if(result.resultCode== 2){

            val intent = result.data
            if (intent != null){
                nodeId=intent.getStringExtra("itm_id").toString()
            }
            database= FirebaseDatabase.getInstance()
            databaseReference= database.getReferenceFromUrl("https://saloonify-59c4c-default-rtdb.firebaseio.com/saloonify")

            databaseReference.child("product").child(nodeId).get().addOnSuccessListener {
                if(it.exists()){
                    binding.productName.setText(it.child("pname").value.toString())
                    binding.productPrice.setText(it.child("pprice").value.toString())
                    binding.productCategory.setSelection(getIndex(binding.productCategory, it.child("pcategory").value.toString()))
                    binding.productQuantity.setText(it.child("pquantity").value.toString())
                    productImg = it.child("productImg").value.toString()

                    val bytes = Base64.decode(productImg,Base64.DEFAULT)
                    val bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.size)
                    binding.productPic.setImageBitmap(bitmap)

                    binding.updateBtn.visibility=View.VISIBLE
                    binding.deleteBtn.visibility=View.VISIBLE
                    binding.saveBtn.visibility=View.INVISIBLE
                }else{
                    Toast.makeText(this,"item not found", Toast.LENGTH_SHORT).show()
                }
            }.addOnFailureListener{
                Toast.makeText(this,it.toString(), Toast.LENGTH_SHORT).show()
            }


        }

    }
    fun showList(view: View){
        var i:Intent
        i= Intent (this,AdmininventoryActivity::class.java)
        itemResultLauncher.launch(i)
    }
    fun getIndex(spinner: Spinner, value: String): Int {
        for (i in 0 until spinner.count) {
            if (spinner.getItemAtPosition(i).toString() == value) {
                return i
            }
        }
        return 0 // return 0 if the value is not found in the Spinner's list of items
    }

    fun update_product(view: View) {

        val pName = binding.productName.text.toString()
        val pPrice = binding.productPrice.text.toString()
        val pCategory = binding.productCategory.selectedItem.toString()
        val pQuantity = binding.productQuantity.text.toString()
        database= FirebaseDatabase.getInstance()
        databaseReference= database.getReferenceFromUrl("https://saloonify-59c4c-default-rtdb.firebaseio.com/saloonify")


        val products= ProductData(pName,pPrice,pCategory,pQuantity,productImg)

        databaseReference.child("product").child(nodeId).setValue(products).addOnSuccessListener {
            binding.productName.text.clear()
            binding.productPrice.text.clear()
            binding.productQuantity.text.clear()
            productImg = ""
            binding.updateBtn.visibility=View.INVISIBLE
            binding.deleteBtn.visibility=View.INVISIBLE
            binding.saveBtn.visibility=View.VISIBLE
            Toast.makeText(applicationContext,"Product updated", Toast.LENGTH_SHORT).show()

        }.addOnFailureListener{

            Toast.makeText(applicationContext,"Product can not be updated", Toast.LENGTH_SHORT).show()
        }

    }
    fun delete_product(view: View) {

        database= FirebaseDatabase.getInstance()
        databaseReference= database.getReferenceFromUrl("https://saloonify-59c4c-default-rtdb.firebaseio.com/saloonify")

        databaseReference.child("product").child(nodeId).removeValue()

        binding.productName.text.clear()
        binding.productPrice.text.clear()
        binding.productQuantity.text.clear()
        productImg = ""

        Toast.makeText(applicationContext,"Product removed", Toast.LENGTH_SHORT).show()


    }
}