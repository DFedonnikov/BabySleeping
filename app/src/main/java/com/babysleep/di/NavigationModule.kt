package com.babysleep.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router

@Module
@InstallIn(ActivityComponent::class)
object NavigationModule {

    @Provides
    @ActivityScoped
    fun provideCicerone() = Cicerone.create()

    @Provides
    @ActivityScoped
    fun provideRouter(cicerone: Cicerone<Router>) = cicerone.router

    @Provides
    @ActivityScoped
    fun provideNavigationHolder(cicerone: Cicerone<Router>) = cicerone.navigatorHolder

}