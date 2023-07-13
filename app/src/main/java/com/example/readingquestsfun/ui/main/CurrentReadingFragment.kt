package com.example.readingquestsfun.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.readingquestsfun.R
import com.example.readingquestsfun.databinding.FragmentCurrentReadingBinding
import com.example.readingquestsfun.viewModels.CurrentReadingViewModel
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class CurrentReadingFragment : Fragment() {

    private lateinit var _binding: FragmentCurrentReadingBinding
    private val _viewModel: CurrentReadingViewModel by activityViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCurrentReadingBinding.inflate(inflater)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

}