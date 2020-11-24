package com.babysleep.ui

import android.net.Uri
import androidx.annotation.IntRange
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SoundControlViewModel @Inject constructor() : ViewModel() {

    private var _soundChangeLiveData = MutableLiveData<Uri>()
    private var _soundLevelLiveData = MutableLiveData<Int>()

    val soundChangeLiveData: LiveData<Uri> get() = _soundChangeLiveData
    val soundLevelLiveData: LiveData<Int> get() = _soundLevelLiveData

    fun onSoundItemSelected(soundUri: Uri) {
        _soundChangeLiveData.value = soundUri
    }

    fun onSoundLevelChanged(@IntRange(from = 0, to = 100) level: Int) {

    }
}