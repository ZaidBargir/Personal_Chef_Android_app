package com.vr.personalchef.GettingRecipeDetails

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import com.vr.personalchef.R

class RecipeUploaderActivity : AppCompatActivity() {
    lateinit var imgDishPhoto :ImageView
    lateinit var resultUri :Uri
    lateinit var btnNextDish: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_uploader)
        imgDishPhoto = findViewById(R.id.imgDishPhotoUpload)
        btnNextDish = findViewById(R.id.btnNextToDescription)
        var s = FirebaseStorage.getInstance().reference
        val currentUser = FirebaseAuth.getInstance().currentUser?.uid
        btnNextDish.setOnClickListener {
            s.child("FoodPhoto").child(currentUser.toString()).putFile(resultUri).addOnCompleteListener {task ->
                if (task.isSuccessful){
                    Toast.makeText(this,"Dish Image Uploaded", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(this,task.exception?.message, Toast.LENGTH_SHORT).show()
                }
            }
        }


        imgDishPhoto.setOnClickListener {
            CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(this);
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)
            if (resultCode == RESULT_OK) {
                resultUri = result.uri
                imgDishPhoto.setImageURI(resultUri)
            }
        }
    }

}