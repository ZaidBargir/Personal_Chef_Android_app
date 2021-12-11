package com.vr.personalchef.RecipeDetails

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.vr.personalchef.R
import org.w3c.dom.Text

class RecipeDetailActivity : AppCompatActivity() {

    lateinit var imgDishPhoto : ImageView
    lateinit var txtDishName : TextView
    lateinit var txtDishMainIngredient: TextView
    lateinit var txtDishList : TextView
    lateinit var txtDishDirection : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_detail)

        imgDishPhoto = findViewById(R.id.imgDishPhotoDetailActivity)
        txtDishName = findViewById(R.id.txtDishNameDetailActivity)
        txtDishMainIngredient = findViewById(R.id.txtRecipeMainIngredientDetailActivity)
        txtDishList = findViewById(R.id.txtRecipeIngredientListDetailActivity)
        txtDishDirection = findViewById(R.id.txtRecipeDirectionDetailActivity)

        txtDishName.text = intent.getStringExtra("dishname")
        txtDishMainIngredient.text = intent.getStringExtra("mainingredient")
        txtDishList.text = intent.getStringExtra("recipelist")
        txtDishDirection.text = intent.getStringExtra("direction")
        Glide.with(this)
            .load(intent.getStringExtra("imgurl"))
            .into(imgDishPhoto)

    }
}