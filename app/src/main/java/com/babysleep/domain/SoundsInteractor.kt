package com.babysleep.domain

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface SoundsInteractor {

    fun getNatureSounds(): Flow<List<Nature>>
    fun getNoises(): Flow<List<Noise>>
}

class SoundsInteractorImpl @Inject constructor(private val repository: SoundsRepository) :
    SoundsInteractor {

    override fun getNatureSounds(): Flow<List<Nature>> = repository.getNatureSounds()

    override fun getNoises(): Flow<List<Noise>> = repository.getNoises()
}