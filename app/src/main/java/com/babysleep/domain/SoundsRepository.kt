package com.babysleep.domain

import kotlinx.coroutines.flow.Flow

interface SoundsRepository {

    fun getNatureSounds(): Flow<List<Nature>>
    fun getNoises(): Flow<List<Noise>>
}