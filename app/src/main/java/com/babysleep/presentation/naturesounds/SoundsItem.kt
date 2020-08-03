package com.babysleep.presentation.naturesounds

import com.babysleep.ui.SoundItemLayout
import java.util.*

data class SoundsItem(
    val id: String = UUID.randomUUID().toString(),
    val renderData: SoundItemLayout.RenderData
)