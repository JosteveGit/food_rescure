package com.example.foodrecue


import SimpleDatabase
import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.foodrecue.pojo.FoodDetails
import kotlinx.android.synthetic.main.activity_add_a_new_food_item.*


class AddANewFoodItemActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_a_new_food_item)

        val database = SimpleDatabase(this)

        addImage.setOnClickListener {
            ActivityCompat.requestPermissions(
                this@AddANewFoodItemActivity,
                arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ),
                PICK_IMAGE
            )
        }

        saveButton.setOnClickListener {
            if (imageUri == null) {
                Toast.makeText(this, "Please add an image", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            val list = arrayListOf<String>(
                titleE.text.toString(),
                desc.text.toString(),
                date.text.toString(),
                pickuptimes.text.toString(),
                quantity.text.toString(),
                location.text.toString()
            )
            if(list.any { it.isEmpty() }){
                Toast.makeText(this, "Fill all fields", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            val food = FoodDetails(
                title = titleE.text.toString(),
                desc = desc.text.toString(),
                date = date.text.toString(),
                pickUpTimes = pickuptimes.text.toString(),
                quantity = quantity.text.toString(),
                location = location.text.toString(),
                imagePath = imageUri!!
            )

            database.addFood(food)
            Toast.makeText(this, "Food added", Toast.LENGTH_LONG).show()
        }
    }

    val PICK_IMAGE = 1

    var imageUri: String? = null

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE) {
            if (data != null) {
                Log.d("Data is not null", data.data.toString())
                val selectedImage: Uri = data.data!!
                imageUri = selectedImage.toString()
                val filePathColumn =
                    arrayOf(MediaStore.Images.Media.DATA)
                val cursor: Cursor? = contentResolver.query(
                    selectedImage,
                    filePathColumn, null, null, null
                )

                cursor!!.moveToFirst()
                val columnIndex: Int = cursor.getColumnIndex(filePathColumn[0])
                val picturePath: String = cursor.getString(columnIndex)
                imageUri = picturePath
                cursor.close()
                image.setImageBitmap(BitmapFactory.decodeFile(picturePath))
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PICK_IMAGE ->                 // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    val getIntent = Intent(Intent.ACTION_GET_CONTENT)
                    getIntent.type = "image/*"

                    val pickIntent = Intent(
                        Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                    )
                    pickIntent.type = "image/*"

                    val chooserIntent = Intent.createChooser(getIntent, "Select Image")
                    chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayOf(pickIntent))
                    startActivityForResult(chooserIntent, PICK_IMAGE)
                } else {
                    Toast.makeText(this, "Permission not granted", Toast.LENGTH_LONG).show()
                }
        }
    }


}