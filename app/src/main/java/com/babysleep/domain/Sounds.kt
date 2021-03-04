package com.babysleep.domain

import com.google.firebase.database.IgnoreExtraProperties

sealed class Sound(
    open val imageUrl: String,
    open val audioUrl: String,
    open val titleEn: String,
    open val titleRu: String,
    open val color: Color?
)

@IgnoreExtraProperties
class Nature(
    override var imageUrl: String = "",
    override var audioUrl: String = "",
    override val titleEn: String = "",
    override val titleRu: String = "",
    override val color: Color? = null
) : Sound(imageUrl, audioUrl, titleEn, titleRu, color)

@IgnoreExtraProperties
class Color(
    val highlightColorHex: String,
    val textColorHex: String
) {
    constructor() : this("", "")
}

@IgnoreExtraProperties
class Noise(
    override var imageUrl: String = "",
    override var audioUrl: String = "",
    override val titleEn: String = "",
    override val titleRu: String = "",
    override val color: Color? = null
) : Sound(imageUrl, audioUrl, titleEn, titleRu, color)