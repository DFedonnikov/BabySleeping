package com.babysleep.di

import android.content.Context
import coil.ImageLoader
import coil.decode.SvgDecoder
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
object AppModule {

    @Provides
    @ActivityScoped
    fun provideImageLoader(@ActivityContext context: Context): ImageLoader =
        ImageLoader.Builder(context)
            .componentRegistry { add(SvgDecoder(context)) }
            .build()

    @Provides
    @ActivityScoped
    fun provideFirebaseDatabase() = Firebase.database.reference
}