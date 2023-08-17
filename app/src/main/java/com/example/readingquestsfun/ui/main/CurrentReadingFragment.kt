package com.example.readingquestsfun.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.marginEnd
import androidx.core.view.marginRight
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.readingquestsfun.R
import com.example.readingquestsfun.databinding.FragmentCurrentReadingBinding
import com.example.readingquestsfun.rvadapters.GodModeStoriesAdapter
import com.example.readingquestsfun.ui.MainActivity
import com.example.readingquestsfun.utils.Resource
import com.example.readingquestsfun.utils.SwipeToDeleteCallback
import com.example.readingquestsfun.viewModels.CurrentReadingViewModel
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class CurrentReadingFragment : Fragment() {

    private lateinit var _binding: FragmentCurrentReadingBinding
    private val _viewModel: CurrentReadingViewModel by activityViewModel()
    private val _swipeHandler by lazy {
        object : SwipeToDeleteCallback(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                val itemIndex = viewHolder.adapterPosition
                Snackbar.make(
                    requireView(), "Удалить историю?", Snackbar.LENGTH_SHORT
                ).setAction("Удалить") {
                    _viewModel.delete(_godModeStoriesAdapter.currentList[itemIndex]._id)
                    _godModeStoriesAdapter.notifyItemRemoved(itemIndex)
                }.show()
            }
        }
    }
    
    private val _godModeStoriesAdapter by lazy { GodModeStoriesAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCurrentReadingBinding.inflate(inflater)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding.rvStories.adapter = _godModeStoriesAdapter



        val itemTouchHelper = ItemTouchHelper(_swipeHandler)
        itemTouchHelper.attachToRecyclerView(_binding.rvStories)

        _viewModel.allStories.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {

                }

                is Resource.Success -> {
                    _godModeStoriesAdapter.submitList(it.data)
                }

                is Resource.Error -> {

                }
            }
        }

        _viewModel.deleteStory.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {
//                    _binding.flLoading.visibility = View.VISIBLE
                }

                is Resource.Success -> {
                    Snackbar.make(
                        requireView(), "Удалено", Snackbar.LENGTH_SHORT
                    ).show()
                }

                is Resource.Error -> {
                    Snackbar.make(
                        requireView(), it.message.toString(), Snackbar.LENGTH_SHORT
                    ).show()
                }
            }
        }

        _viewModel.getAllStories()
    }

}