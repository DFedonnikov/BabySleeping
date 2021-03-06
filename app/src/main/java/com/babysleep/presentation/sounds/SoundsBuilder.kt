package com.babysleep.presentation.sounds

import android.net.Uri
import com.babysleep.domain.Sound
import com.babysleep.extensions.parseColor
import com.babysleep.ui.Loading
import com.babysleep.ui.RenderData
import javax.inject.Inject

interface SoundsBuilder {

    val loadingItems: List<SoundsItem>
    fun build(list: List<Sound>): List<SoundsItem>
}

class SoundsBuilderImpl @Inject constructor() : SoundsBuilder {

    override val loadingItems = Array(6) { SoundsItem(id = it, renderState = Loading) }.toList()

    override fun build(list: List<Sound>): List<SoundsItem> {
        return list.mapIndexed { index, sound ->
            SoundsItem(
                id = index,
                renderState = RenderData(
                    iconUrl = Uri.parse(sound.imageUrl),
                    soundUrl = Uri.parse(sound.audioUrl),
                    title = sound.titleEn,
                    highlightTextColor = sound.color?.textColorHex.parseColor(),
                    highlightBackgroundColor = sound.color?.highlightColorHex.parseColor()
                )
            )
        }
    }
}