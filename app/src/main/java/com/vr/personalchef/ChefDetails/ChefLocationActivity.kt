package com.vr.personalchef.ChefDetails

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.vr.personalchef.R


class ChefLocationActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    lateinit var spCountry :Spinner
    lateinit var adapter: ArrayAdapter<String>
    var  spinnerDataList = ArrayList<String>()
    lateinit var selectedCountry : String
    lateinit var btnNextLocation : Button
    lateinit var txtCountry : TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chef_location)
        spCountry = findViewById(R.id.spCountry)
        btnNextLocation = findViewById(R.id.btnNextLocation)
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


        btnNextLocation.setOnClickListener {

        }

    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        selectedCountry = spCountry.selectedItem.toString()
        val currentUser = FirebaseAuth.getInstance().currentUser?.uid
        var db = FirebaseFirestore.getInstance()

        var userLocationDetails = HashMap<String, Any>()
        userLocationDetails["country"]=selectedCountry
        db.collection("Users").document(currentUser.toString()).set(userLocationDetails)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Location Uploaded", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, task.exception?.message, Toast.LENGTH_SHORT).show()
                }
            }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}