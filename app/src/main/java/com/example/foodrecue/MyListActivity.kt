package com.example.foodrecue

import FoodAdapter
import SimpleDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_home.*

class MyListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_list)

        val database: SimpleDatabase = SimpleDatabase(this)
        val allFoods = database.allFoods.filter { it.shared == "yes" }
        val adapter = FoodAdapter(this, allFoods)


        val manager = LinearLayoutManager(
            this@MyListActivity,
            LinearLayoutManager.VERTICAL,
            false
        )

        rec!!.layoutManager = manager

        rec!!.adapter = adapter

    }
}