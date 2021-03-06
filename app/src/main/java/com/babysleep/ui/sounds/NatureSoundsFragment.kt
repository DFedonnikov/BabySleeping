package com.babysleep.ui.sounds

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.tooling.preview.Preview
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.babysleep.presentation.sounds.NatureSoundsViewModel
import com.babysleep.ui.RenderData
import com.babysleep.ui.SoundControlViewModel
import com.babysleep.ui.SoundItem
import dagger.hilt.android.AndroidEntryPoint


@ExperimentalFoundationApi
@AndroidEntryPoint
class NatureSoundsFragment : Fragment() {

    private val viewModel: NatureSoundsViewModel by viewModels()
    private val soundControlViewModel: SoundControlViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = ComposeView(requireContext()).apply { setContent { BuildContent() } }

    @Composable
    @Preview("content")
    private fun BuildContent() {
        MaterialTheme {
            val items by viewModel.sounds.observeAsState()
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