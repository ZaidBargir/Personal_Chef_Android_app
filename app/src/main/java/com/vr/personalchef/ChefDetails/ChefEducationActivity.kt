package com.vr.personalchef.ChefDetails

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.vr.personalchef.Fragments.HomeFragment
import com.vr.personalchef.Fragments.ProfileFragment
import com.vr.personalchef.MainActivity
import com.vr.personalchef.R


class ChefEducationActivity : AppCompatActivity() {


    lateinit var etUserEducation: EditText
    lateinit var btnFinish: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chef_education)
        etUserEducation = findViewById(R.id.etUserEducation)
        btnFinish = findViewById(R.id.btnFinish)

        val currentUser = FirebaseAuth.getInstance().currentUser?.uid
        var db = FirebaseFirestore.getInstance()


        btnFinish.setOnClickListener {

            val documentReference =
                db.collection("Users").document(currentUser.toString()).collection("Education")
                    .document("usereducation")



            var userEducationDetails = HashMap<String, Any>()
            userEducationDetails["education"] = etUserEducation.text.toString()



            documentReference.set(userEducationDetails).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Education Details Uploaded", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, task.exception?.message, Toast.LENGTH_SHORT).show()
                }
            }

            val i = Intent(this, MainActivity::class.java)
            startActivity(i)

        }


    }

    override fun onBackPressed() {

    }
}