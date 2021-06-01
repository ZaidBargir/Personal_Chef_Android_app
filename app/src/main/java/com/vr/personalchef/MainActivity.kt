package com.vr.personalchef

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.vr.personalchef.Fragments.BookingFragment
import com.vr.personalchef.Fragments.HomeFragment
import com.vr.personalchef.Fragments.ProfileFragment
import com.vr.personalchef.Fragments.ScanFragment
import com.vr.personalchef.GettingRecipeDetails.RecipeUploaderActivity

class MainActivity : AppCompatActivity() {

    lateinit var bottomNavigationView: BottomNavigationView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction()
            .replace(R.id.frameLayout, HomeFragment())
            .commit()
        bottomNavigationView = findViewById(R.id.bottomNavigation)

        bottomNavigationView.setOnNavigationItemSelectedListener {

            when (it.itemId) {
                R.id.homeId -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frameLayout, HomeFragment())
                        .commit()
                }
                R.id.bookId -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frameLayout, BookingFragment())
                        .commit()
                }
                R.id.addId -> {
                    val i = Intent(this@MainActivity, RecipeUploaderActivity::class.java)
                    startActivity(i)
                }

                R.id.scanId -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frameLayout, ScanFragment())
                        .commit()
                }
                R.id.profileId -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frameLayout, ProfileFragment())
                        .commit()
                }
            }
            return@setOnNavigationItemSelectedListener true
        }


    }



    override fun onBackPressed() {
        finishAffinity()
    }

}



