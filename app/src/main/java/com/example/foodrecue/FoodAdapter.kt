import android.app.Activity
import android.database.Cursor
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.foodrecue.R
import com.example.foodrecue.pojo.FoodDetails


class FoodAdapter
    (
    private val activity: Activity,
    private var foods: List<FoodDetails>
) :
    RecyclerView.Adapter<FoodAdapter.MyView>() {
    inner class MyView(view: View) : RecyclerView.ViewHolder(view) {
        var foodTitle: TextView = view.findViewById<View>(R.id.foodTitle) as TextView
        var foodDesc: TextView = view.findViewById<View>(R.id.foodDescription) as TextView
        var foodImage: ImageView = view.findViewById<View>(R.id.foodImage) as ImageView
        val shareButton: Button = view.findViewById<Button>(R.id.shareButton)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyView {

        val itemView: View = LayoutInflater
            .from(parent.context)
            .inflate(
                R.layout.food_item,
                parent,
                false
            )

        return MyView(itemView)
    }

    override fun onBindViewHolder(
        holder: MyView,
        position: Int
    ) {

        holder.foodTitle.text = foods[position].title
        holder.foodDesc.text = foods[position].desc


        val myBitmap = BitmapFactory.decodeFile(foods[position].imagePath)

        holder.foodImage.setImageBitmap(myBitmap)

        holder.shareButton.setOnClickListener {
            val database = SimpleDatabase(activity)
            database.setFoodToShared(foods[position])
            Toast.makeText(activity, "Shared", Toast.LENGTH_LONG).show()
        }

    }


    fun setNotes(foods: List<FoodDetails>) {
        this.foods = foods
        notifyDataSetChanged()
    }


    override fun getItemCount() = foods.size

}




