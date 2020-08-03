package com.babysleep.ui

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.babysleep.ui.naturesounds.NatureSoundsFragment

class SoundsCollectionAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment = when (position) {
        0 -> NatureSoundsFragment()
        1 -> NoisesFragment()
        else -> throw IllegalStateException("wrong pager position $position")
    }
}