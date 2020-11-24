package com.babysleep.presentation.start

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.babysleep.domain.StartInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class StartViewModel @Inject constructor(private val interactor: StartInteractor) :
    ViewModel(), CoroutineScope {

    override val coroutineContext: CoroutineContext = Dispatchers.Main
    private val _authComplete = MutableLiveData<Unit>()

    val authComplete: LiveData<Unit> get() = _authComplete

    init {
        launch {
            interactor.initState()
            _authComplete.value = Unit
        }
    }

    fun onStartClick() = interactor.openSounds()

}