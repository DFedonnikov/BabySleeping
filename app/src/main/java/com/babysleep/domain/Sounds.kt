package com.babysleep.domain

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
class Sounds(
    val nature: List<Nature> = emptyList(),
    val noise: List<Noise> = emptyList()
)

@IgnoreExtraProperties
class Nature(
    val imageUrl: String = "",
    var audioUrl: String = "",
    val titleEn: String = "",
    val titleRu: String = "",
    val color: Color? = null
)

@IgnoreExtraProperties
class Color(
    val highlightColorHex: String,
    val textColorHex: String
) {
    constructor(): this("", "")
}

@IgnoreExtraProperties
class Noise(
    val imageUrl: String = "",
    val audioUrl: String = "",
    val titleEn: String = "",
    val titleRu: String = "",
    val color: Color
)