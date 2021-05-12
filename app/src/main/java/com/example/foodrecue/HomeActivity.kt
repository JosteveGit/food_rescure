package com.example.foodrecue

import FoodAdapter
import SimpleDatabase
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_home.*


class HomeActivity : AppCompatActivity() {

    lateinit var adapter: FoodAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val database: SimpleDatabase = SimpleDatabase(this)
        val allFoods = database.allFoods
        adapter = FoodAdapter(this, allFoods)


        val manager = LinearLayoutManager(
            this@HomeActivity,
            LinearLayoutManager.VERTICAL,
            false
        )

        rec!!.layoutManager = manager

        rec!!.adapter = adapter

        addItemButton.setOnClickListener {
            startActivity(Intent(this, AddANewFoodItemActivity::class.java))
        }
    }

    override fun onResume() {
        val database: SimpleDatabase = SimpleDatabase(this)
        val allFoods = database.allFoods
        adapter.setNotes(allFoods)
        super.onResume()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.myList -> {
                startActivity(Intent(this, MyListActivity::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }
}