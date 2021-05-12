package com.example.foodrecue

import SimpleDatabase
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.foodrecue.pojo.UserDetails
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        signUpButon.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }

        loginButton.setOnClickListener {
            val database = SimpleDatabase(this)
            val allUsers: List<UserDetails> = database.allUsers

            val foundUser = allUsers.any {
                it.emailAddress == username.text.toString() && it.password == password.text.toString()
            }
            if (foundUser) {
                startActivity(Intent(this, HomeActivity::class.java))
            } else {
                Toast.makeText(this, "User not found", Toast.LENGTH_LONG).show()
            }

        }

    }
}