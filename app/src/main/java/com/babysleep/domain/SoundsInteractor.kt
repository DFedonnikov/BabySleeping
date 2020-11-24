package com.babysleep.domain

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface SoundsInteractor {

    fun getNatureSounds(): Flow<List<Nature>>
}

class SoundsInteractorImpl @Inject constructor(private val repository: SoundsRepository) :
    SoundsInteractor {

    override fun getNatureSounds(): Flow<List<Nature>> = repository.getNatureSounds()
}