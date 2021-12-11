package com.vr.personalchef.Fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.firestore.*
import com.vr.personalchef.HomeFragmentRecyclerView.MyAdapterHomeFragment
import com.vr.personalchef.HomeFragmentRecyclerView.Recipe
import com.vr.personalchef.HomeFragmentRecyclerView.onRecipeItemClickListener
import com.vr.personalchef.R
import com.vr.personalchef.RecipeDetails.RecipeDetailActivity


class HomeFragment : Fragment(), onRecipeItemClickListener {

    lateinit var recyclerView: RecyclerView
    lateinit var recipeArrayList: ArrayList<Recipe>
    lateinit var myAdapterHomeFragment: MyAdapterHomeFragment
    lateinit var db: FirebaseFirestore
    var currentUser = FirebaseAuth.getInstance().currentUser?.uid


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        recyclerView = view.findViewById(R.id.recyclerRecipes)
        recyclerView.layoutManager = GridLayoutManager(activity, 2)
        recyclerView.setHasFixedSize(true)
        var currentUser = FirebaseAuth.getInstance().currentUser?.uid
        recipeArrayList = arrayListOf()
        myAdapterHomeFragment = MyAdapterHomeFragment(recipeArrayList, this)
        recyclerView.adapter = myAdapterHomeFragment
        EventChangeListener()

        return view
    }


    private fun EventChangeListener() {
        db = FirebaseFirestore.getInstance()
        db.collection("AllDetails").addSnapshotListener(object :
            EventListener<QuerySnapshot> {
            override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {

                if (error != null) {
                    Log.e("FireStore error", error.message.toString())
                    return
                }

                for (dc: DocumentChange in value?.documentChanges!!) {

                    if (dc.type == DocumentChange.Type.ADDED) {
                        recipeArrayList.add(dc.document.toObject(Recipe::class.java))
                    }
                }

                myAdapterHomeFragment.notifyDataSetChanged()

            }

        })
    }


    override fun onItemClick(recipeList: Recipe, position: Int) {

        val i = Intent(activity, RecipeDetailActivity::class.java)
        i.putExtra("dishname",recipeList.dishname)
        i.putExtra("imgurl",recipeList.imgurl)
        i.putExtra("mainingredient",recipeList.mainingredient)
        i.putExtra("recipelist",recipeList.recipelist)
        i.putExtra("direction",recipeList.direction)
        startActivity(i)


    }


}