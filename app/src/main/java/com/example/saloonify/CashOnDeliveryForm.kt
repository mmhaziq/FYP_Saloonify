package com.example.saloonify

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.*
import kotlin.random.Random

class CashOnDeliveryForm : AppCompatActivity() {
    var re = Regex("^03[0-9]{9}$")
    lateinit var database : FirebaseDatabase
    lateinit var databaseReference: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cash_on_delivery_form)
//        val data = intent.getSerializableExtra("pname") as? ProductData


        database= FirebaseDatabase.getInstance()
        databaseReference= database.getReferenceFromUrl("https://saloonify-59c4c-default-rtdb.firebaseio.com/saloonify")
        val name = findViewById<EditText>(R.id.name_cod)
        val mob = findViewById<EditText>(R.id.mobnumber_cod)
        val address = findViewById<EditText>(R.id.address_cod)
        val id =databaseReference.push().key

        val btn_cod = findViewById<Button>(R.id.submit_cod)

        btn_cod.setOnClickListener{
            var cod_name = name.text.toString().trim()
            var cod_mob = mob.text.toString().trim()
            var cod_address = address.text.toString().trim()
            var ids =id.toString()
            val random = Random.nextInt(1000, 9999).toString()
            var idCounter = 1


            if(cod_name.isEmpty() || cod_mob.isEmpty() || cod_address.isEmpty()){
                Toast.makeText(this,"Empty fields not allowed", Toast.LENGTH_SHORT).show()
            }
            else if(!cod_mob.matches(re)){
                mob.setError("Enter valid phone number")
                mob.requestFocus()
            }
            else if(!cod_name.matches(Regex("^[a-zA-Z]{3,}( {1,2}[a-zA-Z]{3,}){0,}\$"))){
                name.setError("Enter valid name")
                name.requestFocus()
            }
            else{
                databaseReference.child("saloonify").addValueEventListener(object :
                    ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot){
                        databaseReference.child("Orders").child("name").setValue(cod_name)
                        databaseReference.child("Orders").child("number").setValue(cod_mob)
                        databaseReference.child("Orders").child("address").setValue(cod_address)
                        databaseReference.child("Orders").child("order number").setValue(random)
//                        databaseReference.child("cart").removeValue()
                        databaseReference.child("Confirm orders").child(cod_mob).child("name").setValue(cod_name)
                        databaseReference.child("Confirm orders").child(cod_mob).child("number").setValue(cod_mob)
                        databaseReference.child("Confirm orders").child(cod_mob).child("address").setValue(cod_address)
                        databaseReference.child("Confirm orders").child(cod_mob).child("order_number").setValue(random)
//                        databaseReference.child("Confirm orders").child(cod_mob).child("pname").setValue(data)

//                        idCounter++

                        val intent = Intent(applicationContext, UserBill::class.java)
                        startActivity(intent)
                        finish()

                        Toast.makeText(applicationContext,"Your order is successfully placed", Toast.LENGTH_SHORT).show()
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Toast.makeText(applicationContext,error.toString(), Toast.LENGTH_SHORT).show()
                    }

                }
                )
            }
        }
    }
    override fun onBackPressed() {
        val intent = Intent(this, UserDashboard::class.java)
        startActivity(intent)
        finish()
    }
}