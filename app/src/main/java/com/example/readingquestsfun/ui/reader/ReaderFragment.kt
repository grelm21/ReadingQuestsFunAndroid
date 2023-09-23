package com.example.readingquestsfun.ui.reader

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearSnapHelper
import com.example.readingquestsfun.R
import com.example.readingquestsfun.databinding.FragmentReaderBinding
import com.example.readingquestsfun.rvadapters.ChapterConditionsAdapter
import com.example.readingquestsfun.utils.Resource
import com.example.readingquestsfun.viewModels.ReaderViewModel
import com.google.android.material.appbar.MaterialToolbar
import org.koin.androidx.viewmodel.ext.android.viewModel

class ReaderFragment : Fragment() {

    private lateinit var _binding: FragmentReaderBinding
    private val _viewModel: ReaderViewModel by activityViewModels()

    private val _chapterId by lazy {
        arguments?.getString(CHAPTER_ID)
    }

    private val _conditionAdapter by lazy {
        ChapterConditionsAdapter { condition, index ->
            LootDialogFragment.newInstance(index, "CONDITION")
                .show(childFragmentManager, "LootDialog")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReaderBinding.inflate(layoutInflater)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.findViewById<ImageView>(R.id.menu_button)?.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .add(R.id.reader_fragment, EquipmentFragment()).addToBackStack("equip")
                .commit()
        }


        _binding.rvConditions.adapter = _conditionAdapter
        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(_binding.rvConditions)

        _viewModel.getChapter(_chapterId!!)

        _viewModel.chapter.observe(viewLifecycleOwner) { chapter ->
            when (chapter) {
                is Resource.Loading -> {}
                is Resource.Success -> {
                    _binding.tvText.text = if (chapter.data!!.demo) chapter.data!!.text else ""

                    chapter.data.let{
                        /**
                         * получаем айтемы юзера
                         */
                        _viewModel.getUserItems()
                        _viewModel.getConditions(it.next_chapter_id)

                        /**
                         * очищаем зарезервированные айтемы юзера
                         */
                        _viewModel.clearReservedItems()

                        if (it.demo) {
                            _binding.tvText.text = it.text

                            var index = 0
                            it.loot.forEach { _ ->
                                LootDialogFragment.newInstance(index, "LOOT")
                                    .show(childFragmentManager, "LootDialog")
                                index++
                            }
                        } else {
                            LootDialogFragment.newInstance(0, "DEMO")
                                .show(childFragmentManager, "LootDialog")
                        }
                    }
                }

                is Resource.Error -> {}
            }
        }

//        _viewModel.chapter.value.let { chapter ->
//
//            chapter?.data?.let{
//                /**
//                 * получаем айтемы юзера
//                 */
//                _viewModel.getUserItems(it.story_id)
//                _viewModel.getConditions(it.next_chapter_id)
//
//                /**
//                 * очищаем зарезервированные айтемы юзера
//                 */
//                _viewModel.clearReservedItems()
//
//                if (it.demo) {
//                    _binding.tvText.text = it.text
//
//                    var index = 0
//                    it.loot.forEach { _ ->
//                        LootDialogFragment.newInstance(index, "LOOT")
//                            .show(childFragmentManager, "LootDialog")
//                        index++
//                    }
//                } else {
//                    LootDialogFragment.newInstance(0, "DEMO")
//                        .show(childFragmentManager, "LootDialog")
//                }
//            }
//        }

        _viewModel.conditions.observe(viewLifecycleOwner) { conditions ->
            _conditionAdapter.submitList(conditions.toMutableList())
        }
    }

    companion object {
        private const val CHAPTER_ID = "chapter_id"
        fun newInstance(chapterId: String) = ReaderFragment().apply {
            arguments = bundleOf(CHAPTER_ID to chapterId)
        }
    }
}