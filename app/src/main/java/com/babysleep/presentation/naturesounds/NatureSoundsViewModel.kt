package com.babysleep.presentation.naturesounds

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.babysleep.domain.Sounds
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class NatureSoundsViewModel @ViewModelInject constructor(private val database: DatabaseReference) :
    ViewModel(), CoroutineScope {

    override val coroutineContext: CoroutineContext = Dispatchers.IO

    init {
        launch {
            val auth = Firebase.auth
            auth.signInAnonymously()
                .addOnCompleteListener {
                    database.addValueEventListener(object : ValueEventListener {
                        override fun onCancelled(error: DatabaseError) {
                            Log.d("DATABASE", "ERROR: $error")
                        }

                        override fun onDataChange(snapshot: DataSnapshot) {
                            Log.d("DATABASE", "SUCCESS: $snapshot")
                            val data = snapshot.getValue<Sounds>()
                            data?.let { it.nature }
                        }
                    })
                }
        }
    }

}