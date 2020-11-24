package com.babysleep.presentation.naturesounds

import android.net.Uri
import com.babysleep.domain.Nature
import com.babysleep.extensions.parseColor
import com.babysleep.ui.Loading
import com.babysleep.ui.RenderData
import com.google.firebase.storage.StorageReference
import javax.inject.Inject

interface NatureSoundsBuilder {

    val loadingItems: List<SoundsItem>
    fun build(list: List<Nature>): List<SoundsItem>
}

class NatureSoundsBuilderImpl @Inject constructor(private val storageReference: StorageReference) :
    NatureSoundsBuilder {

    override val loadingItems = Array(6) { SoundsItem(id = it, renderState = Loading) }.toList()

    override fun build(list: List<Nature>): List<SoundsItem> {
        return list.mapIndexed { index, sound ->
            SoundsItem(
                id = index,
                renderState = RenderData(
                    iconUrl = sound.imageUrl.takeIf { it.isNotEmpty() }?.let {
                        storageReference.child(it)
                    },
                    soundUrl = Uri.parse(sound.audioUrl),
                    title = sound.titleEn,
                    highlightTextColor = sound.color?.textColorHex.parseColor(),
                    highlightBackgroundColor = sound.color?.highlightColorHex.parseColor()
                )
            )
        }
    }
}