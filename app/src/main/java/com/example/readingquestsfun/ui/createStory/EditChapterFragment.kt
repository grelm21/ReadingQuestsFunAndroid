package com.example.readingquestsfun.ui.createStory

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.os.bundleOf
import com.example.readingquestsfun.R
import com.example.readingquestsfun.databinding.FragmentEditChapterBinding
import com.example.readingquestsfun.models.ItemModel
import com.example.readingquestsfun.utils.Resource
import com.example.readingquestsfun.viewModels.EditChapterViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import java.lang.Error

class EditChapterFragment : Fragment() {

    private lateinit var _binding: FragmentEditChapterBinding

    private val _viewModel: EditChapterViewModel by viewModel {
        parametersOf(arguments?.getString(ID), arguments?.getString(EDIT_TYPE))
    }

    private val _editType by lazy {
        arguments?.getString(EDIT_TYPE)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentEditChapterBinding.inflate(layoutInflater)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _viewModel

        if (_editType == "NEW") {

            _binding.btnTextEditSubmit.setOnClickListener {
                if (!_binding.etText.text.isNullOrBlank() && !_binding.etNote.text.isNullOrBlank()) {
                    _viewModel.addChapter(_binding.etNote.text.toString(), _binding.etText.text.toString())
                }else{
                    _binding.etLayoutText.error
                    _binding.etLayoutNote.error
                }
            }
        }

//        ArrayAdapter(requireContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item)

        _viewModel.chapter.observe(viewLifecycleOwner) { chapter ->
            when (chapter) {
                is Resource.Success -> {
                    chapter.data?.let {
                        _binding.llButtonsNote.visibility = VISIBLE
                        _binding.llLootCondition.visibility = VISIBLE
                        _binding.etText.setText(it.text)
                    }
                }

                is Resource.Error -> {

                }

                is Resource.Loading -> {}
            }
        }
    }

    companion object {

        const val EDIT_TYPE = "edit_type"
        const val ID = "id"

        @JvmStatic
        fun newInstance(edit_type: String, id: String?) =
            EditChapterFragment().apply {
                arguments = bundleOf(EDIT_TYPE to edit_type, ID to id)
            }
    }
}