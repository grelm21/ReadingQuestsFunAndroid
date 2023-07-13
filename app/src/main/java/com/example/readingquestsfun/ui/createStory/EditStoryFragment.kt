package com.example.readingquestsfun.ui.createStory

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import android.widget.Toolbar
import androidx.core.os.bundleOf
import com.example.readingquestsfun.R
import com.example.readingquestsfun.databinding.FragmentEditStoryBinding
import com.example.readingquestsfun.utils.Resource
import com.example.readingquestsfun.viewModels.StoryEditViewModel
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import kotlin.random.Random

class EditStoryFragment : Fragment() {

    private lateinit var _binding: FragmentEditStoryBinding
    private lateinit var toolbar: Toolbar
    private val _viewModel: StoryEditViewModel by activityViewModel()

    private val _isNew by lazy {
        arguments?.getBoolean(IS_NEW, true) ?: true
    }

    private val _storyId by lazy {
        arguments?.getString(STORY_ID)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentEditStoryBinding.inflate(layoutInflater)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toolbar = requireActivity().findViewById<MaterialToolbar>(R.id.toolbar)

        toolbar.findViewById<ImageView>(R.id.btn_delete).visibility = View.GONE
        toolbar.findViewById<ImageView>(R.id.btn_edit).visibility = View.GONE

        if (_isNew) {
            toolbar.title = "Добавить новую историю"

            toolbar.findViewById<ImageView>(R.id.btn_confirm).setOnClickListener {
                _viewModel.addNewStory(
                    _binding.etName.text.toString(),
                    _binding.etDescription.text.toString()
                )

                _viewModel.storyModelDraft.observe(viewLifecycleOwner) { response ->
                    when (response) {
                        is Resource.Success -> {
                            _binding.flLoading.visibility = View.GONE

                            parentFragmentManager.beginTransaction()
                                .replace(R.id.fragment_create_story, ChaptersFragment()).commit()
                        }

                        is Resource.Error -> {
                            _binding.flLoading.visibility = View.GONE
                            Snackbar.make(
                                requireView(),
                                response.message.toString(),
                                Snackbar.LENGTH_SHORT
                            )
                                .show()
                        }

                        is Resource.Loading -> {
                            _binding.flLoading.visibility = View.VISIBLE
                        }
                    }
                }
            }
        } else {
            _viewModel.getStory(_storyId!!)

            _viewModel.storyModelDraft.observe(viewLifecycleOwner) { response ->
                when (response) {
                    is Resource.Success -> {
                        _binding.flLoading.visibility = View.GONE
                        _binding.etName.setText(response.data!!.title)
                        _binding.etDescription.setText(response.data.description)

                        toolbar.findViewById<ImageView>(R.id.btn_confirm).setOnClickListener {
                            _viewModel.editStory(
                                response.data._id,
                                _binding.etName.text.toString(),
                                _binding.etDescription.text.toString()
                            )
                        }
                    }

                    is Resource.Error -> {
                        _binding.flLoading.visibility = View.GONE
                        Snackbar.make(
                            requireView(),
                            response.message.toString(),
                            Snackbar.LENGTH_SHORT
                        )
                            .show()
                    }

                    is Resource.Loading -> {
                        _binding.flLoading.visibility = View.VISIBLE
                    }
                }
            }
        }

        _viewModel.editedStoryModel.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    _binding.flLoading.visibility = View.GONE

                    parentFragmentManager.beginTransaction()
                        .replace(R.id.fragment_create_story, ChaptersFragment()).commit()
                }

                is Resource.Error -> {
                    _binding.flLoading.visibility = View.GONE
                    Snackbar.make(
                        requireView(),
                        response.message.toString(),
                        Snackbar.LENGTH_SHORT
                    )
                        .show()
                }

                is Resource.Loading -> {
                    _binding.flLoading.visibility = View.VISIBLE
                }
            }
        }
    }

    companion object {

        const val IS_NEW = "is_new"
        const val STORY_ID = "story_id"

        fun newInstance(isNew: Boolean, storyId: String?) = EditStoryFragment().apply {
            arguments = bundleOf(IS_NEW to isNew, STORY_ID to storyId)
        }
    }
}