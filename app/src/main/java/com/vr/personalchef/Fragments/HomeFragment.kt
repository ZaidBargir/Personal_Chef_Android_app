package com.vr.personalchef.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.vr.personalchef.R


class HomeFragment : Fragment() {

    lateinit var recyclerRecipe : RecyclerView
    val currentUser = FirebaseAuth.getInstance().currentUser?.uid
    var db = FirebaseFirestore.getInstance()
    lateinit var documentReference: DocumentReference
    lateinit var mDatabase : DatabaseReference


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        recyclerRecipe=view.findViewById(R.id.recyclerRecipes)




        return view
    }






}