package com.vr.personalchef.GettingRecipeDetails

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.viewpager.widget.ViewPager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import com.vr.personalchef.GettingRecipeDetails.WalkthroughFragments.ViewPagerAdapter
import com.vr.personalchef.R

class RecipeUploaderActivity : AppCompatActivity() {
    lateinit var viewPager: ViewPager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_uploader)

        viewPager= findViewById(R.id.viewpager)

        viewPager.adapter = ViewPagerAdapter(supportFragmentManager)

    }

}