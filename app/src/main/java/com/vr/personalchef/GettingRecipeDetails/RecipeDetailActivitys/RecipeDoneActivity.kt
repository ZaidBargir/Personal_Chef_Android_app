package com.vr.personalchef.GettingRecipeDetails.RecipeDetailActivitys

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.vr.personalchef.MainActivity
import com.vr.personalchef.R

class RecipeDoneActivity : AppCompatActivity() {

    lateinit var imgDownloadUrl: Uri
    lateinit var btnRecipeDone : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_done)



        btnRecipeDone = findViewById(R.id.btnRecipeUploadingDone)
        val s = FirebaseStorage.getInstance().reference
        var db = FirebaseFirestore.getInstance()
        val currentUser = FirebaseAuth.getInstance().currentUser?.uid.toString()


        val dishName = intent.getStringExtra("dish_key")

        val storageRef: StorageReference = s.child("FoodPhoto").child(currentUser).child(
            dishName)

        storageRef.downloadUrl.addOnSuccessListener { uri ->
            imgDownloadUrl = uri
        }



        btnRecipeDone.setOnClickListener {

            val documentReference =
                db.collection("UserRecipeDetail").document(currentUser.toString())
                    .collection("Dishes")
                    .document(dishName)

            documentReference.update("imgurl",imgDownloadUrl.toString())

            val i = Intent(this,MainActivity::class.java)
            startActivity(i)

        }


    }
}