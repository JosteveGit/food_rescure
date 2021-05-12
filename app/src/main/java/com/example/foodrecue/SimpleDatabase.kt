import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.foodrecue.pojo.FoodDetails
import com.example.foodrecue.pojo.UserDetails
import java.lang.String
import java.util.ArrayList

class SimpleDatabase(context: Context?) :
    SQLiteOpenHelper(
        context,
        DATABASE_NAME,
        null,
        DATABASE_VERSION
    ) {
    // creating tables
    override fun onCreate(db: SQLiteDatabase) {
        val createUserTableSql =
            "CREATE TABLE $USER_TABLE ($USER_KEY_ID INTEGER PRIMARY KEY, $USER_KEY_FULL_NAME TEXT, $USER_KEY_EMAIL TEXT, $USER_KEY_PHONE TEXT, $USER_KEY_ADDRESS TEXT, $USER_KEY_PASSWORD TEXT)"
        val createFoodTableSql =
            "CREATE TABLE $FOOD_TABLE ($FOOD_KEY_ID INTEGER PRIMARY KEY, $FOOD_KEY_TITLE TEXT, $FOOD_KEY_DESC TEXT, $FOOD_KEY_DATE TEXT, $FOOD_KEY_PICK_UP_TIMES TEXT, $FOOD_KEY_QUANTITY TEXT, $FOOD_KEY_LOCATION TEXT, $FOOD_KEY_SHARED TEXT, $FOOD_KEY_IMAGE_PATH TEXT)"
        db.execSQL(createUserTableSql)
        db.execSQL(createFoodTableSql)
    }


    //Create a food.
    fun addFood(food: FoodDetails): Long {
        val db = this.writableDatabase
        val v = ContentValues()
        v.put(FOOD_KEY_TITLE, food.title)
        v.put(FOOD_KEY_DESC, food.desc)
        v.put(FOOD_KEY_DATE, food.date)
        v.put(FOOD_KEY_PICK_UP_TIMES, food.pickUpTimes)
        v.put(FOOD_KEY_QUANTITY, food.quantity)
        v.put(FOOD_KEY_LOCATION, food.location)
        v.put(FOOD_KEY_SHARED, "no")
        v.put(FOOD_KEY_IMAGE_PATH, food.imagePath)
        return db.insert(FOOD_TABLE, null, v)
    }


    //Get all foods.
    val allFoods: List<FoodDetails>
        get() {
            val allFoods: MutableList<FoodDetails> = ArrayList<FoodDetails>()
            val query =
                "SELECT * FROM $FOOD_TABLE ORDER BY $FOOD_KEY_ID DESC"
            val db = this.readableDatabase
            val cursor = db.rawQuery(query, null)
            Log.d("Log Cursor", cursor.count.toString())
            if (cursor.moveToFirst()) {
                do {
                    val food = FoodDetails()
                    food.id = (cursor.getString(0))
                    food.title = (cursor.getString(1))
                    food.desc = (cursor.getString(2))
                    food.date = (cursor.getString(3))
                    food.pickUpTimes = (cursor.getString(4))
                    food.quantity = (cursor.getString(5))
                    food.location = (cursor.getString(6))
                    food.shared = (cursor.getString(7))
                    food.imagePath = cursor.getString(8)
                    allFoods.add(food)
                } while (cursor.moveToNext())
            }
            Log.d("Log All Foods.", allFoods.size.toString())

            return allFoods
        }


    //Set a food to shared.
    fun setFoodToShared(food: FoodDetails): Int {
        val db = this.writableDatabase
        val c = ContentValues()
        Log.d(
            "Edited",
            "Edited Title: -> ${food.shared.toString()} ID -> ${food.id}"
        )
        c.put(FOOD_KEY_SHARED, "yes")
        return db.update(
            FOOD_TABLE,
            c,
            "$FOOD_KEY_ID=?",
            arrayOf(String.valueOf(food.id))
        )
    }


    //Create a user.
    fun createUser(user: UserDetails): Long {
        val db = this.writableDatabase
        val v = ContentValues()
        v.put(USER_KEY_FULL_NAME, user.fullName)
        v.put(USER_KEY_EMAIL, user.emailAddress)
        v.put(USER_KEY_PHONE, user.phone)
        v.put(USER_KEY_ADDRESS, user.address)
        v.put(USER_KEY_PASSWORD, user.password)
        return db.insert(USER_TABLE, null, v)
    }

    //Get all users.
    val allUsers: List<UserDetails>
        get() {
            val allUsers: MutableList<UserDetails> = ArrayList<UserDetails>()
            val query =
                "SELECT * FROM $USER_TABLE ORDER BY $USER_KEY_ID DESC"
            val db = this.readableDatabase
            val cursor = db.rawQuery(query, null)
            Log.d("Log Cursor", cursor.count.toString())
            if (cursor.moveToFirst()) {
                do {
                    val user = UserDetails()
                    user.id = (cursor.getString(0))
                    user.fullName = (cursor.getString(1))
                    user.emailAddress = (cursor.getString(2))
                    user.phone = (cursor.getString(3))
                    user.address = (cursor.getString(4))
                    user.password = (cursor.getString(5))
                    allUsers.add(user)
                } while (cursor.moveToNext())
            }
            Log.d("Log All Users.", allFoods.size.toString())

            return allUsers
        }


    override fun onUpgrade(
        db: SQLiteDatabase,
        oldVersion: Int,
        newVersion: Int
    ) {
        if (oldVersion >= newVersion) return
        db.execSQL("DROP TABLE IF EXISTS $USER_TABLE")
        db.execSQL("DROP TABLE IF EXISTS $FOOD_TABLE")

        onCreate(db)
    }

    companion object {
        // declare require values
        private const val DATABASE_VERSION = 2
        private const val DATABASE_NAME = "SimpleDB"


        private const val USER_TABLE = "UserTable"
        private const val FOOD_TABLE = "FoodTable"


        //USER COLUMNS
        private const val USER_KEY_ID = "id"
        private const val USER_KEY_FULL_NAME = "full_name"
        private const val USER_KEY_EMAIL = "email"
        private const val USER_KEY_PHONE = "phone"
        private const val USER_KEY_ADDRESS = "address"
        private const val USER_KEY_PASSWORD = "password"

        //FOOD COLUMNS
        private const val FOOD_KEY_ID = "id"
        private const val FOOD_KEY_TITLE = "title"
        private const val FOOD_KEY_DESC = "description"
        private const val FOOD_KEY_DATE = "date"
        private const val FOOD_KEY_PICK_UP_TIMES = "pick_up_times"
        private const val FOOD_KEY_QUANTITY = "quantity"
        private const val FOOD_KEY_LOCATION = "location"
        private const val FOOD_KEY_SHARED = "shared"
        private const val FOOD_KEY_IMAGE_PATH = "image_path"


    }
}