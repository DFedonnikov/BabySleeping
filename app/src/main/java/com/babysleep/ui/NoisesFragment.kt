package com.babysleep.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.babysleep.presentation.NoisesViewModel
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalFoundationApi
@AndroidEntryPoint
class NoisesFragment: Fragment() {

    private val viewModel: NoisesViewModel by viewModels()
    private val soundControlViewModel: SoundControlViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = ComposeView(requireContext()).apply { setContent { BuildContent() } }

    @Composable
    private fun BuildContent() {
        MaterialTheme {
            val items by viewModel.noises.observeAsState()
            LazyVerticalGrid(
                content = {
                    items(items ?: emptyList()) { item ->
                        SoundItem(
                            state = item.renderState,
                            modifier = Modifier.clickable {
                                (item.renderState as? RenderData)?.let {
                                    viewModel.setSelected(item.id)
                                    soundControlViewModel.onSoundItemSelected(it.soundUrl)
                                }
                            },
                        )
                    }
                },
                cells = GridCells.Fixed(3)
            )
        }
    }
}