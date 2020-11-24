package com.babysleep.data.auth

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.lang.Exception
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

interface AuthProvider {

    suspend fun authenticate(): Boolean
}

@ExperimentalCoroutinesApi
class AuthProviderImpl @Inject constructor(
    override val coroutineContext: CoroutineContext,
    private val firebaseAuth: FirebaseAuth
) : AuthProvider, CoroutineScope {

    override suspend fun authenticate(): Boolean {
        return try {
            val authResult = firebaseAuth.signInAnonymously().await()
            when {
                authResult.user != null -> true
                else -> authenticate()
            }
        } catch (e: Exception) {
            authenticate()
        }
    }
}
