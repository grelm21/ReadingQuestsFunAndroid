package com.example.readingquestsfun.ui.createStory

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import com.example.readingquestsfun.R
import com.example.readingquestsfun.databinding.FragmentChaptersBinding
import com.example.readingquestsfun.rvadapters.StoryChaptersAdapter
import com.example.readingquestsfun.ui.MainActivity
import com.example.readingquestsfun.utils.Resource
import com.example.readingquestsfun.viewModels.ChaptersViewModel
import com.example.readingquestsfun.viewModels.StoryEditViewModel
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class ChaptersFragment : Fragment() {

    private val _viewModel: ChaptersViewModel by viewModel {
        parametersOf(_storyId)
    }

    private lateinit var _binding: FragmentChaptersBinding

    private val _storyId by lazy {
        arguments?.getString(EditStoryFragment.STORY_ID)
    }

    private val _chaptersAdapter by lazy {
        StoryChaptersAdapter { chapter ->
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_create_story, EditChapterFragment.newInstance("EDIT", chapter._id))
                .addToBackStack("edit_chapter").commit()
        }
    }

    private fun checkedChangeListener(id: String) {
        _binding.checkPublish.setOnCheckedChangeListener { _, checked ->
            if (checked) {
                Snackbar.make(
                    requireView(),
                    "Теперь эту историю могут видеть другие пользователи",
                    Snackbar.LENGTH_SHORT
                ).show()
//                _viewModel.publish(id, true)
            } else {
                Snackbar.make(
                    requireView(),
                    "Теперь эту историю не могут видеть другие пользователи",
                    Snackbar.LENGTH_SHORT
                ).show()
//                _viewModel.publish(id, false)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        _binding = FragmentChaptersBinding.inflate(layoutInflater)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        _viewModel
        val toolbar = requireActivity().findViewById<MaterialToolbar>(R.id.toolbar)

        toolbar.findViewById<ImageView>(R.id.btn_delete).visibility = VISIBLE
        toolbar.findViewById<ImageView>(R.id.btn_edit).visibility = VISIBLE
        toolbar.findViewById<ImageView>(R.id.btn_confirm).visibility = VISIBLE

        _binding.rvChapters.adapter = _chaptersAdapter


        /**
         * настраиваем FAB'ы
         */
        _binding.apply {
            fabExtended.shrink()

            fabExtended.setOnClickListener {
                if (fabExtended.isExtended) {
                    fabExtended.shrink()
                    fabChapter.visibility = GONE
                    fabChapterText.visibility = GONE
                    fabItem.visibility = GONE
                    fabItemText.visibility = GONE
                } else {
                    fabExtended.extend()
                    fabChapter.visibility = VISIBLE
                    fabChapterText.visibility = VISIBLE
                    fabItem.visibility = VISIBLE
                    fabItemText.visibility = VISIBLE
                }
            }

            fabChapter.setOnClickListener {
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_create_story, EditChapterFragment.newInstance("NEW", _storyId))
                    .addToBackStack("new_chapter").commit()
            }
        }

        toolbar.findViewById<ImageView>(R.id.btn_confirm).setOnClickListener {
            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
        }

        _viewModel.storyModel.observe(viewLifecycleOwner) { story ->
            when (story) {
                is Resource.Success -> {
                    toolbar.title = "Редактировать: ${story.data!!.title}"
                    _binding.tvStoryName.text = story.data.title
                    _binding.tvStoryAuthor.text = story.data.author
                    _binding.tvStoryDescr.text = story.data.description
//            _binding.checkPublish.isChecked = story.published

                    toolbar.findViewById<ImageView>(R.id.btn_delete).setOnClickListener {

//                Snackbar.make(
//                    requireView(), "Удалить историю?", Snackbar.LENGTH_SHORT
//                ).setAction("Удалить", View.OnClickListener { _viewModel.delete(story._id) }).show()
                    }

                    toolbar.findViewById<ImageView>(R.id.btn_edit).setOnClickListener {

                        parentFragmentManager.beginTransaction().replace(
                            R.id.fragment_create_story,
                            EditStoryFragment.newInstance(false, story.data._id)
                        ).commit()
                    }

                    checkedChangeListener(story.data._id)
                }

                is Resource.Error -> {
                    Log.i("ERROR", story.message.toString())
                }

                is Resource.Loading -> {}
            }
        }

        _viewModel.chapters.observe(viewLifecycleOwner) { chapters ->
            when (chapters) {
                is Resource.Success -> {
                    _chaptersAdapter.submitList(chapters.data)
                }

                is Resource.Loading -> {}
                is Resource.Error -> {}
            }
        }
    }

    companion object {

        //        const val IS_NEW = "is_new"
        const val STORY_ID = "story_id"

        fun newInstance(
//            isNew: Boolean,
            storyId: String?
        ) = ChaptersFragment().apply {
            arguments = bundleOf(
//                IS_NEW to isNew,
                STORY_ID to storyId
            )
        }
    }
}