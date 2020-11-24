package com.babysleep.domain

import com.babysleep.data.auth.AuthProvider
import com.babysleep.navigation.SoundsScreen
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import ru.terrakok.cicerone.Router
import javax.inject.Inject

interface StartInteractor {

    suspend fun initState(): Boolean
    fun openSounds()
}

class StartInteractorImpl @Inject constructor(
    private val authProvider: AuthProvider,
    private val router: Router
) : StartInteractor {

    override suspend fun initState(): Boolean = authProvider.authenticate()

    override fun openSounds() {
        router.replaceScreen(SoundsScreen)
    }
}