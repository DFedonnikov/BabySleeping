package com.babysleep.di

import android.content.Context
import com.google.android.exoplayer2.database.DatabaseProvider
import com.google.android.exoplayer2.database.ExoDatabaseProvider
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import com.google.android.exoplayer2.upstream.cache.CacheDataSourceFactory
import com.google.android.exoplayer2.upstream.cache.CacheEvictor
import com.google.android.exoplayer2.upstream.cache.LeastRecentlyUsedCacheEvictor
import com.google.android.exoplayer2.upstream.cache.SimpleCache
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.qualifiers.ActivityContext
import java.io.File

private const val CACHE_SIZE = 10 * 1024 * 1024L
private const val USER_AGENT = "BabySleep"

@Module
@InstallIn(FragmentComponent::class)
object SoundCacheModule {

    @Provides
    fun provideCacheFolder(@ActivityContext context: Context) = File(context.filesDir, "soundcache")

    @Provides
    fun provideCacheEvictor(): CacheEvictor = LeastRecentlyUsedCacheEvictor(CACHE_SIZE)

    @Provides
    fun provideDatabaseProvider(@ActivityContext context: Context): DatabaseProvider =
        ExoDatabaseProvider(context)

    @Provides
    fun provideCache(folder: File, cacheEvictor: CacheEvictor, databaseProvider: DatabaseProvider) =
        SimpleCache(folder, cacheEvictor, databaseProvider)

    @Provides
    fun provideHttpDataSourceFactory() = DefaultHttpDataSourceFactory(USER_AGENT)

    @Provides
    fun provideCacheDataSourceFactory(
        cache: SimpleCache,
        httpDataSourceFactory: DefaultHttpDataSourceFactory
    ) = CacheDataSourceFactory(cache, httpDataSourceFactory)
}