package com.vr.personalchef.ChefDetails

import android.R.attr
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import com.vr.personalchef.R


class ChefBasicInfoFragment : Fragment() {

    lateinit var imgProfilePhoto: ImageView
    lateinit var etUserName: EditText
    lateinit var etPhoneNumber: EditText
    lateinit var etUserBio: EditText
    lateinit var btnNext: Button
    lateinit var resultUri: Uri
    lateinit var str: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_chef_basic_info, container, false)
        imgProfilePhoto = view.findViewById(R.id.imgProfilePhotoUpload)
        etUserName = view.findViewById(R.id.etUserName)
        etPhoneNumber = view.findViewById(R.id.etUserPhoneNumber)
        etUserBio = view.findViewById(R.id.etUserBio)
        btnNext = view.findViewById(R.id.btnNext)
        val currentUser = FirebaseAuth.getInstance().currentUser?.uid
        imgProfilePhoto.setOnClickListener {
            CropImage.activity()
                .start(activity!!, this);
        }


        btnNext.setOnClickListener {
            var s = FirebaseStorage.getInstance().reference
            var db = FirebaseFirestore.getInstance()

            s.child("UserProfilePhoto").child(currentUser.toString()).putFile(resultUri).addOnCompleteListener {task ->
                if (task.isSuccessful){
                    Toast.makeText(activity,"Profile Uploaded",Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(activity,task.exception?.message,Toast.LENGTH_SHORT).show()
                }
            }

            var userBasicDetails = HashMap<String,Any>()
            userBasicDetails["name"] = etUserName.text.toString()
            userBasicDetails["phone"] = etPhoneNumber.text.toString()
            userBasicDetails["bio"] = etUserBio.text.toString()

            db.collection("Users").document(currentUser.toString()).set(userBasicDetails).addOnCompleteListener { task ->
                if (task.isSuccessful){
                    Toast.makeText(activity,"Data Uploaded",Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(activity,task.exception?.message,Toast.LENGTH_SHORT).show()
                }
            }

        }
        return view
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