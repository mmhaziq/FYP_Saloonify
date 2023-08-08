package com.example.saloonify


import android.app.DatePickerDialog
import android.app.PendingIntent.getActivity
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
import java.util.*
import java.util.regex.Pattern


class UserBookAppointment: AppCompatActivity() {
    val stylists = arrayOf("Random","Tooba","Zulaikha","Nasreen")

    val categories = arrayOf("Skincare","Beautification","Hair styling","Hair cutting")

    val appointment_time = arrayOf("10:00 AM","10:30 AM","11:00 AM","11:30 AM","12:00 PM","12:30 PM","01:00 PM","01:30 PM"
        ,"02:00 PM","02:30 PM","03:00 PM","03:30 PM","04:00 PM")




    var dateformat: TextView? = null
    var year = 0
    var month:kotlin.Int = 0
    var day:kotlin.Int = 0

    var re = Regex("^03[0-9]{9}$")
    lateinit var database : FirebaseDatabase
    lateinit var databaseReference: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_userbookappointment)

        database= FirebaseDatabase.getInstance()
        databaseReference= database.getReferenceFromUrl("https://saloonify-59c4c-default-rtdb.firebaseio.com/saloonify")
        val name = findViewById<EditText>(R.id.name)
        val email = findViewById<EditText>(R.id.email)
        val phone = findViewById<EditText>(R.id.phone)
        val cnic = findViewById<EditText>(R.id.cnic)
        val spinner = findViewById<Spinner>(R.id.spinner)
        val category = findViewById<Spinner>(R.id.spinner_category)
        val ap_time = findViewById<Spinner>(R.id.spinner_time)
//        val date = findViewById<DatePicker>(R.id.datePicker1)

//        val today = Calendar.getInstance()
//        date.init(today.get(Calendar.YEAR),today.get(Calendar.MONTH),today.get(Calendar.DAY_OF_MONTH)){
//                view,year,month,day ->
//            var month = month+1
//            val selected_date = "$day/$month/$year"
//            Toast.makeText(this,selected_date,Toast.LENGTH_SHORT).show()

        val datePicker = findViewById<DatePicker>(R.id.datePicker1)
        var msg:String = ""
        val today = Calendar.getInstance()
        datePicker.init(today.get(Calendar.YEAR), today.get(Calendar.MONTH),
            today.get(Calendar.DAY_OF_MONTH)

        ) { view, year, month, day ->
            val month = month + 1
            msg = "$day/$month/$year"




//            datePicker.getDatePicker().setMinDate(Calendar.getTimeInMillis());
//            Toast.makeText(this@MainActivity, msg, Toast.LENGTH_SHORT).show()
        }

        val mCalendar = Calendar.getInstance()
        val mDialog = DatePickerDialog(this, { _, mYear, mMonth, mDay ->
            mCalendar[Calendar.YEAR] = mYear
            mCalendar[Calendar.MONTH] = mMonth
            mCalendar[Calendar.DAY_OF_MONTH] = mDay
        }, mCalendar[Calendar.YEAR], mCalendar[Calendar.MONTH], mCalendar[Calendar.DAY_OF_MONTH])
        val minDay = 13
        val minMonth = 1
        val minYear = 2023
        mCalendar.set(minYear, minMonth-1, minDay)
        mDialog.datePicker.minDate = mCalendar.timeInMillis
        val message_date:String=msg.toString().trim()




        val btn_book = findViewById<Button>(R.id.booknow)
        btn_book.setOnClickListener{
            var app_fullname = name.text.toString().trim()
            var app_email = email.text.toString().trim()
            var app_phone = phone.text.toString().trim()
            var app_cnic = cnic.text.toString().trim()
            var app_stylist = spinner.selectedItem.toString().trim()
            var app_category = category.selectedItem.toString().trim()
            var app_time = ap_time.selectedItem.toString().trim()

            var app_date= msg.toString().trim()



            if(app_fullname.isEmpty() || app_email.isEmpty() || app_phone.isEmpty() || app_cnic.isEmpty() || app_date.equals("")){
                Toast.makeText(this,"Empty fields not allowed", Toast.LENGTH_SHORT).show()
            }else if(!Patterns.EMAIL_ADDRESS.matcher(app_email).matches()){
                email.setError("Enter valid email")
                email.requestFocus()
            }
            else if(!app_phone.matches(re)){
                phone.setError("Enter valid phone number")
                phone.requestFocus()
            }
            else if(!app_fullname.matches(Regex("^[a-zA-Z]{3,}( {1,2}[a-zA-Z]{3,}){0,}\$"))){
                name.setError("Enter valid name")
                name.requestFocus()
            }
            else if(!app_cnic.matches(Regex("^[0-9]{13}$"))){
                cnic.setError("Enter valid name")
                cnic.requestFocus()
            }
            else{
                databaseReference.child("saloonify").addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot){
                        databaseReference.child("appointments").child(app_cnic).child("fullname").setValue(app_fullname)
                        databaseReference.child("appointments").child(app_cnic).child("email").setValue(app_email)
                        databaseReference.child("appointments").child(app_cnic).child("contact").setValue(app_phone)
                        databaseReference.child("appointments").child(app_cnic).child("cnic").setValue(app_cnic)
                        databaseReference.child("appointments").child(app_cnic).child("stylist").setValue(app_stylist)
                        databaseReference.child("appointments").child(app_cnic).child("date").setValue(app_date)
                        databaseReference.child("appointments").child(app_cnic).child("category").setValue(app_category)
                        databaseReference.child("appointments").child(app_cnic).child("time").setValue(app_time)


                        Toast.makeText(applicationContext,"Appointment pending", Toast.LENGTH_SHORT).show()
                        val intent = Intent(applicationContext, UserDashboard::class.java)
                        startActivity(intent)
                        finish()
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Toast.makeText(applicationContext,error.toString(),Toast.LENGTH_SHORT).show()
                    }

                }
                )
            }
        }

        val arrayAdapter= ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,stylists)

        spinner.adapter= arrayAdapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
//                Toast.makeText(applicationContext,"hehe",Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

        val categoryAdapter= ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,categories)

        category.adapter= categoryAdapter
        category.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
//                Toast.makeText(applicationContext,"hehe",Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

        val timeAdapter= ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,appointment_time)

        ap_time.adapter= timeAdapter
        ap_time.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
//                Toast.makeText(applicationContext,"hehe",Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }




    }

    override fun onBackPressed() {
        val intent = Intent(this, UserDashboard::class.java)
        startActivity(intent)
        finish()
    }
}
