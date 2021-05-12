package com.example.foodrecue.pojo

class UserDetails {

    lateinit var fullName: String
    lateinit var emailAddress: String
    lateinit var phone: String
    lateinit var password: String
    lateinit var address: String

    var id: String? = null

    internal constructor()

    internal constructor(
         fullName: String,
         emailAddress: String,
         phone: String,
         password: String,
         address: String
    ){
        this.fullName = fullName
        this.emailAddress = emailAddress
        this.phone = phone
        this.password = password
        this.address = address
    }
}