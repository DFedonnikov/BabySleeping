package com.babysleep.data

import android.content.SharedPreferences
import androidx.core.content.edit
import com.babysleep.di.NatureSoundsDataSource
import com.babysleep.di.NoisesDataSource
import com.babysleep.domain.Nature
import com.babysleep.domain.SoundsRepository
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


class SoundsRepositoryImpl @Inject constructor(
    @NatureSoundsDataSource private val natureSoundsDataSource: DatabaseReference,
    @NoisesDataSource private val noisesDataSource: DatabaseReference,
    private val storageReference: StorageReference,
    private val sharedPreferences: SharedPreferences
) :
    SoundsRepository {

    override fun getNatureSounds(): Flow<List<Nature>> {
        return callbackFlow {
            natureSoundsDataSource.addValueEventListener(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                    cancel("Sounds API call error", error.toException())
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    snapshot.getValue<List<Nature>>()?.let {
                        launch {
                            it.map {
                                it.audioUrl = buildSoundUrl(it.audioUrl)
                                it
                            }
                            send(it)
                        }
                    }
                }
            })
            awaitClose { natureSoundsDataSource.removeValue() }
        }
    }

    private suspend fun buildSoundUrl(audioUrl: String): String {
        return sharedPreferences.getString(audioUrl, null) ?: run {
            val fullUrl = storageReference.child(audioUrl).downloadUrl.await().toString()
            sharedPreferences.edit { putString(audioUrl, fullUrl) }
            fullUrl
        }
    }
}