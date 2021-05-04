package com.vr.personalchef.Fragments

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.vr.personalchef.ChefDetails.ChefDetailActivity
import com.vr.personalchef.R
import com.vr.personalchef.loginActivity.LoginActivity


class ProfileFragment : Fragment() {

    private lateinit var btnLogout: ImageButton
    private lateinit var btnProfileEdit: ImageButton
    lateinit var imgProfilePic : ImageView
    lateinit var userName: TextView
    lateinit var userBio : TextView
    lateinit var photoUrl : Uri

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        btnLogout = view.findViewById(R.id.imgBtnLogout)
        btnProfileEdit = view.findViewById(R.id.imgBtnProfileEdit)
        imgProfilePic =view.findViewById(R.id.imgProfilePhoto)
        userName = view.findViewById(R.id.txtUserName)
        userBio = view.findViewById(R.id.txtBio)
        val auth = FirebaseAuth.getInstance()
        val db = FirebaseFirestore.getInstance()
        val s= FirebaseStorage.getInstance().reference
        val userId = auth.currentUser?.uid
        db.collection("Users").document(userId.toString()).get().addOnCompleteListener { task ->
            if (task.result!!.exists()){
                userName.text = task.result!!.getString("name")
                userBio.text = task.result!!.getString("bio")
            }
        }

       val storageReference = s.child("UserProfilePhoto").child(userId.toString())
        storageReference.downloadUrl.addOnSuccessListener { uri ->
            photoUrl = uri
          Glide.with(activity!!).load(photoUrl).into(imgProfilePic)
        }

        btnProfileEdit.setOnClickListener {
            goToActivity(ChefDetailActivity())
        }
        btnLogout.setOnClickListener {
            auth.signOut()
           goToActivity(LoginActivity())
        }

        return view
    }

    private fun goToActivity(goActivity: Activity){
        val i = Intent(activity, goActivity::class.java)
        startActivity(i)
    }
}