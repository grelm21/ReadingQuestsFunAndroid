package com.example.readingquestsfun.ui.createStory

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import com.example.readingquestsfun.R
import com.example.readingquestsfun.databinding.FragmentEditItemDialogBinding
import com.example.readingquestsfun.utils.Resource
import com.example.readingquestsfun.viewModels.ChaptersViewModel
import com.example.readingquestsfun.viewModels.EditChapterViewModel
import com.example.readingquestsfun.viewModels.EditItemDialogViewModel
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class EditItemDialogFragment : DialogFragment() {

    private lateinit var _binding: FragmentEditItemDialogBinding
    private val _viewModel: EditItemDialogViewModel by viewModel {
        parametersOf(
            arguments?.getString(STORY_ID),
            arguments?.getString(ITEM_ID)
        )
    }

    val _parentFragment by lazy { arguments?.getBoolean(FROM_CHAPTERS_SCREEN) }

    private val _chaptersViewModel by lazy { requireParentFragment().getViewModel<ChaptersViewModel>() }

    private val _isNew by lazy { arguments?.getBoolean(IS_NEW) }

    private var _newItem = NewItem("", "", "", "")

    private lateinit var _itemTypeSpinnerAdapter: ArrayAdapter<String>
    private val _itemType = listOf("STATISTICS", "ITEM", "AID")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentEditItemDialogBinding.inflate(layoutInflater)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val textWatcherName by lazy {
            object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    _newItem.name = p0.toString()
                }

                override fun afterTextChanged(p0: Editable?) {
                }
            }
        }

        val textWatcherDescription by lazy {
            object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    _newItem.description = p0.toString()
                }

                override fun afterTextChanged(p0: Editable?) {
                }
            }
        }

        val textWatcherMax by lazy {
            object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    _newItem.max = p0.toString()
                }

                override fun afterTextChanged(p0: Editable?) {
                }
            }
        }

        val itemTypeListener by lazy {
            object : OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    _newItem.type = _itemType[p2]
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {}
            }
        }

        _itemTypeSpinnerAdapter = ArrayAdapter(
            requireContext(),
            androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
            _itemType
        )

        _binding.spinnerItemType.adapter = _itemTypeSpinnerAdapter
        _binding.spinnerItemType.onItemSelectedListener = itemTypeListener

        _viewModel.storyItem.observe(viewLifecycleOwner) {
            if (!_isNew!!) {
                when (it) {
                    is Resource.Success -> {
                        _binding.apply {
                            _binding.etItemName.setText(it.data!!.name)
                            _binding.etItemDescription.setText(it.data.description)
                            _binding.etItemMax.setText(it.data.max.toString())

                            val itemId = _itemType.indexOf(it.data.type)
                            _binding.spinnerItemType.setSelection(itemId)
                        }
                    }

                    is Resource.Loading -> {}
                    is Resource.Error -> {
                        Log.i("ITEM_GET", it.message.toString())
                    }
                }
            }
        }

        _binding.etItemName.addTextChangedListener(textWatcherName)
        _binding.etItemDescription.addTextChangedListener(textWatcherDescription)
        _binding.etItemMax.addTextChangedListener(textWatcherMax)

        _binding.btnSubmit.setOnClickListener {
            if (_isNew!!) {
                if (_newItem.name.isNotBlank() &&
                    _newItem.description.isNotBlank() &&
                    _newItem.max.isNotBlank()
                ) {
                    _viewModel.addItemToStory(
                        _newItem.name,
                        _newItem.description,
                        _newItem.max,
                        _newItem.type
                    )
                }
            }else{
                Log.i("EDIT_ITEM", _newItem.toString())
                _viewModel.editStoryItem(
                    if(_newItem.name.isNullOrBlank()) null else _newItem.name,
                    if(_newItem.description.isNullOrBlank()) null else _newItem.description,
                    if(_newItem.max.isNullOrBlank()) null else _newItem.max,
                    if(_newItem.type.isNullOrBlank()) null else _newItem.type,
                )
            }
            dismiss()
            if (_parentFragment!!) _chaptersViewModel.getStoryItems()
        }

        _binding.btnCancel.setOnClickListener {
            dismiss()
        }
    }

    companion object {

        private const val IS_NEW = "is_new"
        private const val FROM_CHAPTERS_SCREEN = "from_chapters_screen"
        private const val STORY_ID = "story_id"
        private const val ITEM_ID = "item_id"

        @JvmStatic
        fun newInstance(
            isNew: Boolean,
            fromChaptersScreen: Boolean,
            storyId: String?,
            itemId: String?
        ) =
            EditItemDialogFragment().apply {
                arguments = bundleOf(
                    IS_NEW to isNew,
                    FROM_CHAPTERS_SCREEN to fromChaptersScreen,
                    STORY_ID to storyId,
                    ITEM_ID to itemId
                )
            }

        data class NewItem(
            var name: String,
            var description: String,
            var max: String,
            var type: String
        )
    }
}