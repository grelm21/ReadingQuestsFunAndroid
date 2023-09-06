package com.example.readingquestsfun.ui.reader

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.example.readingquestsfun.R
import com.example.readingquestsfun.databinding.FragmentStoryBinding
import com.example.readingquestsfun.utils.Resource
import com.example.readingquestsfun.viewModels.ReaderViewModel
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class StoryFragment : Fragment() {

    private lateinit var _binding: FragmentStoryBinding
    private val _viewModel: ReaderViewModel by activityViewModel()

    private val _storyId by lazy{
        arguments?.getString(STORY_ID)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentStoryBinding.inflate(layoutInflater)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        _viewModel.story.observe(viewLifecycleOwner){story ->
            when(story){
                is Resource.Loading -> {}
                is Resource.Success -> {
                    _binding.tvStoryName.text = story.data!!.chapters[0]
                        _binding.btnRead.setOnClickListener {
                        parentFragmentManager.beginTransaction().replace(R.id.reader_fragment, ReaderFragment.newInstance(story.data!!.chapters[0])).commit()
                    }
                }
                is Resource.Error -> {}
            }
        }

        _viewModel.getStory(_storyId!!)
    }

    companion object{
        private const val STORY_ID = "story_id"
        fun newInstance(storyId: String) = StoryFragment().apply {
            arguments = bundleOf(STORY_ID to storyId)
        }
    }
}