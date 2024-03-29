package com.example.readingquestsfun.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.readingquestsfun.databinding.FragmentCurrentReadingBinding
import com.example.readingquestsfun.rvadapters.GodModeStoriesAdapter
import com.example.readingquestsfun.ui.createStory.AdminActivity
import com.example.readingquestsfun.ui.reader.ReaderActivity
import com.example.readingquestsfun.utils.Resource
import com.example.readingquestsfun.viewModels.CurrentReadingViewModel
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class CurrentReadingFragment : Fragment() {

    private lateinit var _binding: FragmentCurrentReadingBinding
    private val _viewModel: CurrentReadingViewModel by activityViewModel()

    private val _godModeStoriesAdapter by lazy {
        GodModeStoriesAdapter (_onClick = { story ->
            val intent = Intent(requireContext(), ReaderActivity::class.java)
            intent.putExtra("STORY_ID", story._id)
            startActivity(intent)
        },
        _onClickEdit = {story ->
            val intent = Intent(requireContext(), AdminActivity::class.java)
            intent.putExtra("IS_NEW", false)
            intent.putExtra("STORY_ID", story._id)
            startActivity(intent)
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCurrentReadingBinding.inflate(inflater)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding.rvStories.adapter = _godModeStoriesAdapter

        _viewModel.allStories.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {

                }

                is Resource.Success -> {
                    _godModeStoriesAdapter.submitList(it.data)
                }

                is Resource.Error -> {
                    Log.i("CHAPTERS_ERROR", it.message.toString())
                }
            }
        }

        _viewModel.getAllStories()
    }

}