package com.babysleep.extensions

import android.graphics.Color
import java.lang.Exception

fun String?.parseColor(): Int = this?.let {
    try {
        Color.parseColor(this)
    } catch (e: Exception) {
        Color.WHITE
    }
} ?: Color.WHITE