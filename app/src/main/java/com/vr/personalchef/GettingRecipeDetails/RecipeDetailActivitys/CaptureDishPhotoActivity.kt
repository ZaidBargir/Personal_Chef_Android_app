package com.vr.personalchef.GettingRecipeDetails.RecipeDetailActivitys

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import com.vr.personalchef.MainActivity
import com.vr.personalchef.R


class CaptureDishPhotoActivity : AppCompatActivity() {


    lateinit var database: DatabaseReference

    // Layout 1
    lateinit var imgDishPhoto: ImageView
    lateinit var btnUploadDish: Button
    lateinit var resultUri: Uri
    lateinit var animationViewUpload: LottieAnimationView
    lateinit var etDishName: EditText


    // Get Recipe Basic Details
    lateinit var etDishMainIngredient: EditText
    lateinit var etDishRecipeList: EditText
    lateinit var imgDownloadUrl: Uri


    // Get Recipe Direction
    lateinit var etDishRecipeDirection: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_capture_dish_photo)
        //Layout 1
        imgDishPhoto = findViewById(R.id.imgDishPhoto)
        etDishName = findViewById(R.id.etDishName)
        btnUploadDish = findViewById(R.id.btnUploadDish)
        animationViewUpload = findViewById(R.id.animationViewUpload)


        //Layout 2
        etDishMainIngredient = findViewById(R.id.etMainIngredient)
        etDishRecipeList = findViewById(R.id.etDishRecipeList)

        //Layout 3
        etDishRecipeDirection = findViewById(R.id.etDishRecipeDirection)


        //Layout Image Capture
        imgDishPhoto.setOnClickListener {
            CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(this)
        }

        //Main Variables
        val currentUser = FirebaseAuth.getInstance().currentUser?.uid
        var db = FirebaseFirestore.getInstance()
        val s = FirebaseStorage.getInstance().reference


        //Main Upload Button
        btnUploadDish.setOnClickListener {


            val dishName = etDishName.text.toString()


            // Recipe Detail Structure
            var recipeDetails = HashMap<String, Any>()
            recipeDetails["dishname"] = etDishName.text.toString()
            recipeDetails["mainingredient"] = etDishMainIngredient.text.toString()
            recipeDetails["recipelist"] = etDishRecipeList.text.toString()
            recipeDetails["direction"] = etDishRecipeDirection.text.toString()





            db.collection("UserRecipeDetail").document(currentUser.toString())
                .collection("Dishes").document(etDishName.text.toString()).set(recipeDetails)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Uploading Please Wait", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, task.exception?.message, Toast.LENGTH_SHORT).show()
                    }
                }




            s.child("FoodPhoto").child(currentUser.toString()).child(etDishName.text.toString())
                .putFile(resultUri)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Uploaded", Toast.LENGTH_SHORT).show()
                        val i = Intent(this, RecipeDoneActivity::class.java)
                        i.putExtra("dish_key", dishName.toString())
                        startActivity(i)
                    } else {
                        Toast.makeText(this, task.exception?.message, Toast.LENGTH_SHORT).show()
                    }
                }


        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)
            if (resultCode == RESULT_OK) {
                resultUri = result.uri
                imgDishPhoto.setImageURI(resultUri)
                animationViewUpload.visibility = View.GONE
            }
        }
    }


}