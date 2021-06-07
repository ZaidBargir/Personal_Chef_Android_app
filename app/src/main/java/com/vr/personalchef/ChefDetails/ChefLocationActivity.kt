package com.vr.personalchef.ChefDetails

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.vr.personalchef.R


class ChefLocationActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    lateinit var spCountry :Spinner
    lateinit var databaseReference: DocumentReference
    lateinit var adapter: ArrayAdapter<String>
    var  spinnerDataList = ArrayList<String>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chef_location)
        spCountry = findViewById(R.id.spCountry)
        val db = FirebaseFirestore.getInstance()
        val databaseReference = db.collection("UserCountry")

        databaseReference.get().addOnCompleteListener(OnCompleteListener<QuerySnapshot>() { task ->
            val doc = task.result
            for (documentSnapshot in doc!!.documents) {
                val countryName = documentSnapshot["country"].toString()
               spinnerDataList.add(countryName)
            }
        })


        adapter = ArrayAdapter<String>(
            this, android.R.layout.simple_spinner_dropdown_item, spinnerDataList
        )
        spCountry.adapter=adapter

    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        TODO("Not yet implemented")
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}