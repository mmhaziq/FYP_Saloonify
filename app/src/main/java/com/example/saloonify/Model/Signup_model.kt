package com.example.saloonify.Model

class Signup_model() {

    lateinit var username :String
    lateinit var password: String
   // lateinit var id:String

    constructor(username: String,password:String):this(){
        this.username = username
        this.password = password
     //   this.id = id

    }

}