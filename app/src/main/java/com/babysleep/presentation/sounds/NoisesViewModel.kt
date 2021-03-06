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
class NoisesViewModel @Inject constructor(
    private val interactor: SoundsInteractor,
    private val builder: SoundsBuilder
) :
    ViewModel(), CoroutineScope {
    override val coroutineContext: CoroutineContext = Dispatchers.Main

    private val noisesLiveData = MutableLiveData<List<SoundsItem>>()

    val noises: LiveData<List<SoundsItem>> by lazy { noisesLiveData }

    init {
        noisesLiveData.value = builder.loadingItems
        launch {
            interactor.getNoises()
                .map { builder.build(it) }
                .collect { noisesLiveData.value = it }
        }
    }

    fun setSelected(id: Int) {
        noisesLiveData.value
            ?.mapNotNull {
                val state = it.renderState as? RenderData ?: return@mapNotNull null
                it.copy(renderState = state.copy(isSelected = id == it.id))
            }
            ?.let { noisesLiveData.value = it }
    }
}