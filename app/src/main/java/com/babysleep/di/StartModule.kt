package com.babysleep.di

import com.babysleep.domain.StartInteractor
import com.babysleep.domain.StartInteractorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
interface StartModule {

    @Binds
    @ViewModelScoped
    fun bindStartInteractor(impl: StartInteractorImpl): StartInteractor
}