package com.babysleep.di

import android.content.Context
import coil.ImageLoader
import coil.util.CoilUtils
import com.babysleep.data.StorageReferenceFetcher
import com.babysleep.data.auth.AuthProvider
import com.babysleep.data.auth.AuthProviderImpl
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import dagger.Module
import dagger.Provides
import dagger.hilt.DefineComponent
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import okhttp3.OkHttpClient
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

private const val SHARED_PREFERENCES_NAME = "BabySleepSharedPreferences"

@ExperimentalCoroutinesApi
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideImageLoader(@ApplicationContext context: Context): ImageLoader =
        ImageLoader.Builder(context)
            .apply {
                componentRegistry { add(StorageReferenceFetcher(context)) }
                okHttpClient {
                    OkHttpClient.Builder()
                        .cache(CoilUtils.createDefaultCache(context))
                        .build()
                }
            }.build()

    @Provides
    fun provideIoCoroutineContext(): CoroutineContext = Dispatchers.IO

    @Provides
    @Singleton
    fun provideAuthProvider(impl: AuthProviderImpl): AuthProvider = impl

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = Firebase.auth

    @Provides
    @Singleton
    fun provideFirebaseDatabaseReference(): DatabaseReference =
        Firebase.database.apply { setPersistenceEnabled(true) }
            .reference.apply { keepSynced(true) }

    @Provides
    @Singleton
    fun provideFirebaseStorage(): StorageReference = Firebase.storage.reference

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context) =
        context.getSharedPreferences(
            SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE
        )
}