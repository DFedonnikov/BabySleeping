package com.babysleep.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.babysleep.databinding.LayoutNoisesFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SoundsFragment : Fragment() {

    private var _binding: LayoutNoisesFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = LayoutNoisesFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.contentPager.adapter = SoundsCollectionAdapter(this)
    }
}