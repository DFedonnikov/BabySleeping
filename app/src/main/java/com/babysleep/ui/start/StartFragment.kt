package com.babysleep.ui.start

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.babysleep.databinding.LayoutStartFragmentBinding
import com.babysleep.presentation.start.StartViewModel
import dagger.hilt.android.AndroidEntryPoint
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@AndroidEntryPoint
class StartFragment : Fragment() {

    private var _binding: LayoutStartFragmentBinding? = null
    private val binding get() = _binding!!

    val viewModel: StartViewModel by viewModels()

    @Inject
    lateinit var router: Router

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = LayoutStartFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.startButton.setOnClickListener { viewModel.onStartClick() }
        viewModel.authComplete.observe(viewLifecycleOwner, Observer {
            binding.startButton.isVisible = true
        })
    }
}