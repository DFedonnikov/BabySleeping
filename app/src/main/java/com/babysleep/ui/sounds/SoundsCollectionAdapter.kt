package com.babysleep.ui.sounds

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.babysleep.ui.sounds.NatureSoundsFragment
import com.babysleep.ui.sounds.NoisesFragment

class SoundsCollectionAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 2

    @ExperimentalFoundationApi
    override fun createFragment(position: Int): Fragment = when (position) {
        0 -> NatureSoundsFragment()
        1 -> NoisesFragment()
        else -> throw IllegalStateException("wrong pager position $position")
    }
}