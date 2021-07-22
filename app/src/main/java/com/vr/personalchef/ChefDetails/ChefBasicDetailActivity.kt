package com.vr.personalchef.ChefDetails

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import com.vr.personalchef.Fragments.HomeFragment
import com.vr.personalchef.R

class ChefBasicDetailActivity : AppCompatActivity() {

    lateinit var imgProfilePhoto: ImageView
    lateinit var etUserName: EditText
    lateinit var etPhoneNumber: EditText
    lateinit var etUserBio: EditText
    lateinit var etUserEmail: EditText
    lateinit var btnNext: Button
    lateinit var resultUri: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chef_detail)
        imgProfilePhoto = findViewById(R.id.imgProfilePhotoUpload)
        etUserName = findViewById(R.id.etUserName)
        etPhoneNumber = findViewById(R.id.etUserPhoneNumber)
        etUserBio = findViewById(R.id.etUserBio)
        etUserEmail = findViewById(R.id.etUserEmail)
        btnNext = findViewById(R.id.btnNext)
        val currentUser = FirebaseAuth.getInstance().currentUser?.uid
        imgProfilePhoto.setOnClickListener {
            CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(this)
        }


        btnNext.setOnClickListener {
            var s = FirebaseStorage.getInstance().reference
            var db = FirebaseFirestore.getInstance()

            s.child("UserProfilePhoto").child(currentUser.toString()).putFile(resultUri)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Profile Photo Uploaded", Toast.LENGTH_SHORT).show()
                        val i = Intent(this, ChefLocationActivity::class.java)
                        startActivity(i)
                    } else {
                        Toast.makeText(this, task.exception?.message, Toast.LENGTH_SHORT).show()
                    }
                }

            var userBasicDetails = HashMap<String, Any>()
            userBasicDetails["name"] = etUserName.text.toString()
            userBasicDetails["phone"] = etPhoneNumber.text.toString()
            userBasicDetails["bio"] = etUserBio.text.toString()
            userBasicDetails["email"] = etUserEmail.text.toString()

            db.collection("Users").document(currentUser.toString()).set(userBasicDetails)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Data Uploaded", Toast.LENGTH_SHORT).show()
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
                imgProfilePhoto.setImageURI(resultUri)
            }
        }
    }
}