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
    private var _pageChangedLiveData = MutableLiveData<IndicatorRender>()

    val soundChangeLiveData: LiveData<Uri> get() = _soundChangeLiveData
    val soundLevelLiveData: LiveData<Int> get() = _soundLevelLiveData
    val pageChangedLiveData: LiveData<IndicatorRender> get() = _pageChangedLiveData

    fun onSoundItemSelected(soundUri: Uri) {
        _soundChangeLiveData.value = soundUri
    }

    fun onSoundLevelChanged(@IntRange(from = 0, to = 100) level: Int) {

    }

    fun onSoundPageScrolled(position: Int, offset: Float) {
        if (position == 1) return
        val natureTitleAlpha = 0.5f + (1 - offset) * 0.5f
        val noiseTitleAlpha = 0.5f + offset * 0.5f
        _pageChangedLiveData.value = IndicatorRender(natureTitleAlpha, noiseTitleAlpha, offset)
    }
}

class IndicatorRender(
    val natureTitleAlpha: Float = 1f,
    val noiseTitleAlpha: Float = 0.5f,
    val bias: Float = 0f
)