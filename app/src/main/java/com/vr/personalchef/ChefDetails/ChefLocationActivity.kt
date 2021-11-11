package com.vr.personalchef.ChefDetails

import android.content.Intent
import android.os.Bundle
import android.widget.*
import android.widget.AdapterView.OnItemClickListener
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.vr.personalchef.Fragments.ProfileFragment
import com.vr.personalchef.R


class ChefLocationActivity : AppCompatActivity() {


    val db = FirebaseFirestore.getInstance()
    lateinit var btnNextLocation: Button

    //Country
    lateinit var txtCountry: String
    lateinit var autocompleteCountry: AutoCompleteTextView
    var countryDataList = ArrayList<String>()
    lateinit var adapterCountry: ArrayAdapter<String>


    //State
    lateinit var txtState: String
    lateinit var autocompleteState: AutoCompleteTextView
    var stateDataList = ArrayList<String>()
    lateinit var adapterState: ArrayAdapter<String>

    //City
    lateinit var txtCity: String
    lateinit var autocompleteCity: AutoCompleteTextView
    var cityDataList = ArrayList<String>()
    lateinit var adapterCity: ArrayAdapter<String>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chef_location)
        btnNextLocation = findViewById(R.id.btnNextLocation)
        autocompleteCountry = findViewById(R.id.autoCompleteTextViewCountry)
        autocompleteState = findViewById(R.id.autoCompleteTextViewState)
        autocompleteCity = findViewById(R.id.autoCompleteTextViewCity)


        //Country
        val databaseReferenceCountry = db.collection("UserCountry")
        databaseReferenceCountry.get()
            .addOnCompleteListener(OnCompleteListener<QuerySnapshot>() { task ->
                val doc = task.result
                for (documentSnapshot in doc!!.documents) {
                    val countryName = documentSnapshot["country"].toString()
                    countryDataList.add(countryName)
                }
            })
        adapterCountry = ArrayAdapter<String>(
            this, android.R.layout.simple_spinner_dropdown_item, countryDataList
        )

        autocompleteCountry.setAdapter(adapterCountry)

        autocompleteCountry.threshold = 1

        autocompleteCountry.setOnItemClickListener(OnItemClickListener { parent, view, position, id ->
            txtCountry = countryDataList[position]
            getUserState(txtCountry)
        })
















        btnNextLocation.setOnClickListener {
            val currentUser = FirebaseAuth.getInstance().currentUser?.uid
            var db = FirebaseFirestore.getInstance()



            val documentReference =
                db.collection("Users").document(currentUser.toString()).collection("Location")
                    .document("location")

            documentReference.update("country", txtCountry)
            documentReference.update("state", txtState)
            documentReference.update("city",txtCity)




            val i = Intent(this,ChefEducationActivity::class.java)
            startActivity(i)
        }

    }


    private fun getUserState(country: String) {
        //State
        val databaseReferenceState =
            db.collection("UserState").document(country).collection("State")
        databaseReferenceState.get()
            .addOnCompleteListener(OnCompleteListener<QuerySnapshot>() { task ->
                val doc = task.result
                for (documentSnapshot in doc!!.documents) {
                    val stateName = documentSnapshot["state"].toString()
                    stateDataList.add(stateName)
                }
            })

        adapterState = ArrayAdapter<String>(
            this, android.R.layout.simple_spinner_dropdown_item, stateDataList
        )
        autocompleteState.setAdapter(adapterState)

        autocompleteState.threshold = 1

        autocompleteState.setOnItemClickListener(OnItemClickListener { parent, view, position, id ->

            txtState = stateDataList[position]
            getUserCity(txtState)

        })
    }


    private fun getUserCity(State: String) {
        //State
        val databaseReferenceCity = db.collection("UserCity").document(State).collection("City")
        databaseReferenceCity.get()
            .addOnCompleteListener(OnCompleteListener<QuerySnapshot>() { task ->
                val doc = task.result
                for (documentSnapshot in doc!!.documents) {
                    val cityName = documentSnapshot["city"].toString()
                    cityDataList.add(cityName)
                }
            })

        adapterCity = ArrayAdapter<String>(
            this, android.R.layout.simple_spinner_dropdown_item, cityDataList
        )
        autocompleteCity.setAdapter(adapterCity)

        autocompleteCity.threshold = 1

        autocompleteCity.setOnItemClickListener(OnItemClickListener { parent, view, position, id ->

            txtCity = cityDataList[position]

        })
    }


}