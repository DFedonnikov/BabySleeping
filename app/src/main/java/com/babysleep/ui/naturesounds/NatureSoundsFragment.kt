package com.babysleep.ui.naturesounds

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.babysleep.R
import com.babysleep.databinding.LayoutNatureSoundsFragmentBinding
import com.babysleep.extensions.parseColor
import com.babysleep.presentation.naturesounds.NatureSoundsViewModel
import com.babysleep.presentation.naturesounds.SoundsItem
import com.babysleep.ui.SoundItemLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NatureSoundsFragment : Fragment() {

    private var _binding: LayoutNatureSoundsFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: NatureSoundsViewModel by viewModels()

    private val adapter = SoundsAdapter {  }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = LayoutNatureSoundsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel
        with(binding) {
            natureSoundsList.adapter = adapter
            natureSoundsList.layoutManager = GridLayoutManager(context, 3)
            adapter.submitList(
                listOf(
                    SoundsItem(
                        renderData = SoundItemLayout.RenderData(
                            background = R.drawable.bg_waterfall,
                            title = "Waterfall",
                            highlightTextColor = "#D65B93".parseColor(),
                            highlightBackgroundColor = "#33D65B93".parseColor(),
                            isSelected = true
                        )
                    ),
                    SoundsItem(
                        renderData = SoundItemLayout.RenderData(
                            background = R.drawable.bg_waterfall,
                            title = "Waterfall",
                            highlightTextColor = "#D65B93".parseColor(),
                            highlightBackgroundColor = "#33D65B93".parseColor()
                        )
                    ),
                    SoundsItem(
                        renderData = SoundItemLayout.RenderData(
                            background = R.drawable.bg_waterfall,
                            title = "Waterfall",
                            highlightTextColor = "#D65B93".parseColor(),
                            highlightBackgroundColor = "#33D65B93".parseColor()
                        )
                    ),
                    SoundsItem(
                        renderData = SoundItemLayout.RenderData(
                            background = R.drawable.bg_waterfall,
                            title = "Waterfall",
                            highlightTextColor = "#D65B93".parseColor(),
                            highlightBackgroundColor = "#33D65B93".parseColor()
                        )
                    ),
                    SoundsItem(
                        renderData = SoundItemLayout.RenderData(
                            background = R.drawable.bg_waterfall,
                            title = "Waterfall",
                            highlightTextColor = "#D65B93".parseColor(),
                            highlightBackgroundColor = "#33D65B93".parseColor()
                        )
                    ),
                    SoundsItem(
                        renderData = SoundItemLayout.RenderData(
                            background = R.drawable.bg_waterfall,
                            title = "Waterfall",
                            highlightTextColor = "#D65B93".parseColor(),
                            highlightBackgroundColor = "#33D65B93".parseColor()
                        )
                    )
                )
            )
            natureSoundsList.addItemDecoration(object : RecyclerView.ItemDecoration() {

                private val offset = requireContext().resources.getDimensionPixelOffset(R.dimen.offset)

                override fun getItemOffsets(
                    outRect: Rect,
                    view: View,
                    parent: RecyclerView,
                    state: RecyclerView.State
                ) {
                    outRect.right = offset
                    outRect.bottom = offset
                }
            })
        }
    }
}