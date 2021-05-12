package com.example.foodrecue

import SimpleDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils.isEmpty
import android.widget.Toast
import com.example.foodrecue.pojo.UserDetails
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        val database: SimpleDatabase = SimpleDatabase(this)

        saveButton.setOnClickListener {
            val fields = arrayOf<String>(
                fullNameEditText.text.toString(),
                email.text.toString(),
                phone.text.toString(),
                address.text.toString(),
                password.text.toString(),
                confirmPassword.text.toString()
            )
            if (fields.any { it.isEmpty() }) {
                Toast.makeText(this, "Fill all fields", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if (confirmPassword.text.toString() != password.text.toString()) {
                Toast.makeText(this, "Passwords don't match", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            val userDetails: UserDetails = UserDetails(
                fullName = fullNameEditText.text.toString(),
                emailAddress = email.text.toString(),
                phone = phone.text.toString(),
                address = address.text.toString(),
                password = password.text.toString()
            )
            database.createUser(userDetails)
            Toast.makeText(this, "User Created", Toast.LENGTH_LONG).show()
        }
    }
}