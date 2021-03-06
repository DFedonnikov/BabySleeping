package com.babysleep.presentation.sounds

import androidx.lifecycle.*
import com.babysleep.domain.SoundsInteractor
import com.babysleep.ui.RenderData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class NatureSoundsViewModel @Inject constructor(
    private val interactor: SoundsInteractor,
    private val builder: SoundsBuilder
) :
    ViewModel(), CoroutineScope {
    override val coroutineContext: CoroutineContext = Dispatchers.Main

    private val soundsLiveData = MutableLiveData<List<SoundsItem>>()

    val sounds: LiveData<List<SoundsItem>> by lazy { soundsLiveData }

    init {
        soundsLiveData.value = builder.loadingItems
        launch {
            interactor.getNatureSounds()
                .map { builder.build(it) }
                .collect { soundsLiveData.value = it }
        }
    }

    fun setSelected(id: Int) {
        soundsLiveData.value
            ?.mapNotNull {
                val state = it.renderState as? RenderData ?: return@mapNotNull null
                it.copy(renderState = state.copy(isSelected = id == it.id))
            }
            ?.let { soundsLiveData.value = it }
    }
}