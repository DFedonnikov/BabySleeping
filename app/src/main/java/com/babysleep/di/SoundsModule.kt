package com.babysleep.di

import com.babysleep.data.SoundsRepositoryImpl
import com.babysleep.domain.SoundsInteractor
import com.babysleep.domain.SoundsInteractorImpl
import com.babysleep.domain.SoundsRepository
import com.babysleep.presentation.sounds.SoundsBuilder
import com.babysleep.presentation.sounds.SoundsBuilderImpl
import com.google.firebase.database.DatabaseReference
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

private const val DATABAS_SOUNDS_CHILD = "sounds"
private const val DATABASE_NATURE_SOUNDS_CHILD = "nature"
private const val DATABASE_NOISES_CHILD = "noise"

@Module
@InstallIn(ViewModelComponent::class)
object SoundsModule {

    @Provides
    fun provideSoundsInteractor(impl: SoundsInteractorImpl): SoundsInteractor = impl

    @Provides
    fun provideSoundsRepository(impl: SoundsRepositoryImpl): SoundsRepository = impl

    @Provides
    @NatureSoundsDataSource
    fun provideNatureSoundsDataSource(reference: DatabaseReference): DatabaseReference = reference
        .child(DATABAS_SOUNDS_CHILD)
        .child(DATABASE_NATURE_SOUNDS_CHILD)

    @Provides
    @NoisesDataSource
    fun provideNoisesDataSource(reference: DatabaseReference): DatabaseReference = reference
        .child(DATABAS_SOUNDS_CHILD)
        .child(DATABASE_NOISES_CHILD)

    @Provides
    fun provideNatureSoundsMapper(impl: SoundsBuilderImpl): SoundsBuilder = impl
}