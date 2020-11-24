package com.babysleep

import android.app.Application
import coil.ImageLoader
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.HiltAndroidApp
import io.github.rosariopfernandes.firecoil.FireCoil
import javax.inject.Inject

@HiltAndroidApp
class MyApp : Application()