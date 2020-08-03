package com.babysleep.domain

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
class Sounds(
    val nature: List<Nature> = emptyList(),
    val noise: List<Noise> = emptyList()
)

@IgnoreExtraProperties
class Nature(
    val audioUrl: String = "",
    val titleEn: String = "",
    val titleRu: String = ""
)

@IgnoreExtraProperties
class Noise(
    val audioUrl: String = "",
    val titleEn: String = "",
    val titleRu: String = ""
)