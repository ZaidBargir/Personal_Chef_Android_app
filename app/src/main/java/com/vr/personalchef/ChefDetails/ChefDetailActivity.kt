package com.vr.personalchef.ChefDetails

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.vr.personalchef.Fragments.HomeFragment
import com.vr.personalchef.R

class ChefDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chef_detail)

        supportFragmentManager.beginTransaction()
            .replace(R.id.chefDetailFrameLayout, ChefBasicInfoFragment())
            .commit()
    }
}