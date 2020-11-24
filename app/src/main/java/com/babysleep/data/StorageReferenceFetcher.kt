package com.babysleep.data

import android.content.Context
import coil.bitmap.BitmapPool
import coil.decode.DataSource
import coil.decode.Options
import coil.fetch.FetchResult
import coil.fetch.Fetcher
import coil.fetch.SourceResult
import coil.size.Size
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import okio.buffer
import okio.source
import java.io.File
import java.lang.Exception
import javax.inject.Inject

class StorageReferenceFetcher @Inject constructor(
    private val context: Context
) : Fetcher<StorageReference> {

    override suspend fun fetch(
        pool: BitmapPool,
        data: StorageReference,
        size: Size,
        options: Options
    ): FetchResult {
        val file = File(context.cacheDir.path + data.path)
        return if (file.exists()) {
            SourceResult(
                dataSource = DataSource.DISK,
                source = file.inputStream().source().buffer(),
                mimeType = null
            )
        } else {
            saveToDisk(file, data)
            SourceResult(
                dataSource = DataSource.NETWORK,
                source = data.stream.await().stream.source().buffer(),
                mimeType = null
            )
        }
    }

    private suspend fun saveToDisk(file: File, data: StorageReference) {
        try {
            coroutineScope {
                launch {
                    file.parentFile?.mkdirs()
                    file.createNewFile()
                    file.writeBytes(data.stream.await().stream.readBytes())
                }
            }
        } catch (e: Exception) {
            println(e)
        }
    }

    override fun key(data: StorageReference) = data.path

    override fun handles(data: StorageReference) = true
}