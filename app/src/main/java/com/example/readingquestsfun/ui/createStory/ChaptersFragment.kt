package com.example.readingquestsfun.ui.createStory

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.activityViewModels
import com.example.readingquestsfun.R
import com.example.readingquestsfun.databinding.FragmentChaptersBinding
import com.example.readingquestsfun.ui.MainActivity
import com.example.readingquestsfun.utils.Resource
import com.example.readingquestsfun.viewModels.StoryEditViewModel
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.snackbar.Snackbar

class ChaptersFragment : Fragment() {

    private val _viewModel: StoryEditViewModel by activityViewModels()
    private lateinit var _binding: FragmentChaptersBinding

    private fun checkedChangeListener(id: String) {
        _binding.checkPublish.setOnCheckedChangeListener { _, checked ->
            if (checked) {
                Snackbar.make(
                    requireView(),
                    "Теперь эту историю могут видеть другие пользователи",
                    Snackbar.LENGTH_SHORT
                ).show()
                _viewModel.publish(id, true)
            } else {
                Snackbar.make(
                    requireView(),
                    "Теперь эту историю не могут видеть другие пользователи",
                    Snackbar.LENGTH_SHORT
                ).show()
                _viewModel.publish(id, false)
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

        val toolbar = requireActivity().findViewById<MaterialToolbar>(R.id.toolbar)

        toolbar.findViewById<ImageView>(R.id.btn_delete).visibility = VISIBLE
        toolbar.findViewById<ImageView>(R.id.btn_edit).visibility = VISIBLE
        toolbar.findViewById<ImageView>(R.id.btn_confirm).visibility = VISIBLE

        toolbar.findViewById<ImageView>(R.id.btn_confirm).setOnClickListener {
            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
        }

        _viewModel.storyModel.observe(viewLifecycleOwner) { story ->
            toolbar.title = "Редактировать: ${story.title}"
            _binding.tvStoryName.text = story.title
            _binding.tvStoryAuthor.text = story.author
            _binding.tvStoryDescr.text = story.description
            _binding.checkPublish.isChecked = story.published

            toolbar.findViewById<ImageView>(R.id.btn_delete).setOnClickListener {

                Snackbar.make(
                    requireView(), "Удалить историю?", Snackbar.LENGTH_SHORT
                ).setAction("Удалить", View.OnClickListener { _viewModel.delete(story._id) }).show()
            }

            toolbar.findViewById<ImageView>(R.id.btn_edit).setOnClickListener {

                parentFragmentManager.beginTransaction().replace(
                    R.id.fragment_create_story, EditStoryFragment.newInstance(false, story._id)
                ).commit()
            }

            checkedChangeListener(story._id)
        }

        _viewModel.deleteStory.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {
                    _binding.flLoading.visibility = VISIBLE
                }

                is Resource.Success -> {
                    _binding.flLoading.visibility = VISIBLE
                    val intent = Intent(requireContext(), MainActivity::class.java)
                    startActivity(intent)
                }

                is Resource.Error -> {
                    Snackbar.make(
                        requireView(), it.message.toString(), Snackbar.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}