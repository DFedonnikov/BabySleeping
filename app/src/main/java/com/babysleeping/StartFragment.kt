package com.babysleeping

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.babysleeping.databinding.LayoutStartFragmentBinding

class StartFragment : Fragment() {

    private var _binding: LayoutStartFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = LayoutStartFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }
}