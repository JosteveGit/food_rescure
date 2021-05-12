package com.example.foodrecue.pojo

class FoodDetails {
    var id: String? = null
    lateinit var imagePath: String
    lateinit var title: String
    lateinit var desc: String
    lateinit var date: String
    lateinit var pickUpTimes: String
    lateinit var location: String
    lateinit var quantity: String
    lateinit var shared: String

    internal constructor()

    internal constructor(
        imagePath: String,
        title: String,
        desc: String,
        date: String,
        pickUpTimes: String,
        location: String,
        quantity: String
    ){
        this.imagePath = imagePath
        this.title = title
        this.desc = desc
        this.date = date
        this.pickUpTimes = pickUpTimes
        this.quantity = quantity
        this.location = location
    }

}