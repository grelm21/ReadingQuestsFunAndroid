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
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.example.readingquestsfun.R
import com.example.readingquestsfun.databinding.FragmentEditChapterBinding
import com.example.readingquestsfun.models.ChapterModel
import com.example.readingquestsfun.models.ConditionModel
import com.example.readingquestsfun.models.ItemModel
import com.example.readingquestsfun.rvadapters.EditChapterLootAdapter
import com.example.readingquestsfun.utils.Resource
import com.example.readingquestsfun.viewModels.EditChapterViewModel
import com.google.android.material.textfield.TextInputEditText
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import java.lang.Error

class EditChapterFragment : Fragment() {

    private lateinit var _binding: FragmentEditChapterBinding

    private val _viewModel: EditChapterViewModel by viewModel {
        parametersOf(
            arguments?.getString(STORY_ID),
            arguments?.getString(CHAPTER_ID),
            arguments?.getString(EDIT_TYPE)
        )
    }

    private val _editType by lazy {
        arguments?.getString(EDIT_TYPE)
    }

    private var _newCondition = NewCondition("", "", "", "", "")

    private lateinit var itemsConditionAdapter: ArrayAdapter<ItemModel>
    private lateinit var typesConditionAdapter: ArrayAdapter<CheckTypes>
    private lateinit var chaptersListAdapter: ArrayAdapter<ChapterModel>

    private val _checkTypes =
        listOf(CheckTypes("ADD", "ИЗМЕНИТЬ"), CheckTypes("CHECK", "ПРОВЕРИТЬ"))

    private val _lootAdapter by lazy {
        EditChapterLootAdapter(
            { loot ->
                _viewModel.getLoot(loot._id)
                EditLootDialogFragment.newInstance(false).show(childFragmentManager, "new_loot")
            },
            { loot ->
                _viewModel.deleteLoot(loot._id)
            })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        _binding = FragmentEditChapterBinding.inflate(layoutInflater)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding.rvLootItems.adapter = _lootAdapter

        _viewModel

        if (_editType == "NEW") {

            _binding.btnTextEditSubmit.apply {
                isEnabled = true
                setOnClickListener {
                    if (!_binding.etText.text.isNullOrBlank() && !_binding.etNote.text.isNullOrBlank()) {
                        _viewModel.addChapter(
                            _binding.etNote.text.toString(), _binding.etText.text.toString()
                        )
                        Toast.makeText(
                            requireContext(),
                            "Глава добавлена",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        _binding.etLayoutText.error
                        _binding.etLayoutNote.error
                    }
                }
            }
        }

        _viewModel.chapter.observe(viewLifecycleOwner) { chapter ->

            with(chapter is Resource.Loading) {
                _binding.llLoading.isVisible = this
            }

            when (chapter) {
                is Resource.Success -> {
                    chapter.data?.let {
                        _binding.apply {
                            llButtonsNote.visibility = VISIBLE
                            llLootCondition.visibility = VISIBLE
                            etText.setText(it.text)
                            etNote.setText(it.note)
                        }

                        with(it.loot.isNotEmpty()) {
                            _lootAdapter.submitList(it.loot.toMutableList())
                            _binding.rvLootItems.isVisible = this
                            _binding.llNoLoot.isVisible = !this
                        }

                        with(it.demo) {
                            _binding.switchDemo.isChecked = this
                        }


                        it.condition.let { condition ->
                            with(condition != null) {
                                _binding.btnAddCondition.text =
                                    if (this) "Обновить условие" else "Добавить уловие"
                                _binding.flCondition.isVisible = this
                                _binding.btnDeleteCondition.isVisible = this
                                _binding.btnAddCondition.isEnabled = !this
                            }

                            _binding.apply {
                                etConditionText.setText(condition?.text ?: "")
                                etConditionQuantity.setText(
                                    condition?.item?.quantity?.toString() ?: "0"
                                )
                            }

                            with(condition?.ignore_chapter_id != null) {
                                _binding.checkBoxIgnoreChapter.isChecked = this
                                _binding.spinnerIgnoreChapter.isVisible = this
                            }
                        }
                    }
                }

                is Resource.Error -> {

                }

                is Resource.Loading -> {}
            }
        }

        _viewModel.itemList.observe(viewLifecycleOwner) { items ->
            when (items) {
                is Resource.Success -> {
                    itemsConditionAdapter = ArrayAdapter(
                        requireContext(),
                        androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                        items.data!!
                    )

                    typesConditionAdapter = ArrayAdapter(
                        requireContext(),
                        androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                        _checkTypes
                    )

                    _binding.apply {
                        spinnerConditionItem.adapter = itemsConditionAdapter
                        spinnerConditionType.adapter = typesConditionAdapter

                        /**
                         * не могу сделать так, чтобы листенер загружался после получения даты
                         */
                        spinnerConditionItem.apply {
                            onItemSelectedListener = _viewModel.itemList.value?.let { items ->
                                spinnerChangeCondition(
                                    this
                                )
                            }
                        }
                    }

                    _viewModel.chapter.value?.let { chapter ->
                        chapter.data!!.condition?.let { condition ->
                            val itemId = condition.item._id
                            val mappedItemsId = items.data!!.map { it._id }
                            val indexItem = mappedItemsId.indexOf(itemId)

                            val checkType = condition.check
                            val mappedCheck = _checkTypes.map { it.checkType }
                            val indexCheck = mappedCheck.indexOf(checkType)

                            _binding.spinnerConditionItem.setSelection(indexItem)
                            _binding.spinnerConditionType.setSelection(indexCheck)
                        }

                        if (chapter.data.condition == null) {
                            _binding.spinnerConditionItem.setSelection(0)
                            _binding.spinnerConditionType.setSelection(0)
                        }
                    }
                }

                is Resource.Error -> {

                }

                is Resource.Loading -> {}
            }
        }

        _viewModel.chapters.observe(viewLifecycleOwner) { story ->

            when (story) {
                is Resource.Loading -> {}
                is Resource.Success -> {

                    if (_editType == "NEW") {
                        val chapterCount = story.data!!.size
                        _binding.etNote.setText("Глава $chapterCount \n")
                    }

                    chaptersListAdapter = ArrayAdapter(
                        requireContext(),
                        androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                        story.data!!
                    )

                    _binding.spinnerIgnoreChapter.adapter = chaptersListAdapter
                    _binding.spinnerIgnoreChapter.apply {
                        onItemSelectedListener = spinnerChangeCondition(this)
                    }

                }

                is Resource.Error -> {
                    Log.i("CHAPTERS_ERROR", story.message.toString())
                }
            }
        }

        _viewModel.callResponse.observe(viewLifecycleOwner) {
            with(it is Resource.Loading) {
                _binding.llLoading.isVisible = this
            }
        }

        _binding.switchDemo.setOnCheckedChangeListener { _, _ ->
            _viewModel.chapterDemo()
        }

        /**
         * ItemSelectedListener'ы
         */

        _binding.apply {
            spinnerConditionType.apply {
                onItemSelectedListener = spinnerChangeCondition(this)
            }
        }


        /**
         * TextChangedListener'ы
         */

        _binding.apply {
            etConditionText.apply { addTextChangedListener(textChangeCondition(this)) }
            etConditionQuantity.apply { addTextChangedListener(textChangeCondition(this)) }
        }

        /**
         * OnClickListener'ы
         */

        _binding.btnAddLoot.setOnClickListener {
            EditLootDialogFragment.newInstance(true).show(childFragmentManager, "new_loot")
        }

        _binding.btnAddCondition.setOnClickListener {
            _viewModel.chapter.value?.data?.condition.let { condition ->
                with(condition != null) {
                    if (this) {
                        Log.i("CONDIITON_BEFOR", _newCondition.toString())
                        _viewModel.editCondition(
                            _newCondition.itemId,
                            _newCondition.checkType,
                            _newCondition.quantity,
                            _newCondition.text,
                            _newCondition.ingnoreChapter.ifEmpty { null }
//                            null
                        )
                        Toast.makeText(
                            requireContext(),
                            "Условие обновлено",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        if (!_binding.flCondition.isVisible) {
                            _binding.flCondition.isVisible = true
                            _binding.btnAddCondition.isEnabled = false
                        } else {
                            if (_newCondition.text.isBlank()
                                || _newCondition.quantity.isBlank()
                                || _newCondition.checkType.isBlank()
                                || _newCondition.itemId.isBlank()
                            ) {
                                Toast.makeText(
                                    requireContext(),
                                    "Не все поля введены",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            } else {
                                _viewModel.addCondition(
                                    _newCondition.itemId,
                                    _newCondition.checkType,
                                    _newCondition.quantity,
                                    _newCondition.text,
                                    _newCondition.ingnoreChapter
                                )

                                _newCondition = NewCondition("", "", "", "", "")
                            }
                        }
                    }
                }
            }
        }

        _binding.btnDeleteCondition.setOnClickListener {
            _viewModel.deleteCondition()

            Toast.makeText(
                requireContext(),
                "Условие удалено",
                Toast.LENGTH_SHORT
            ).show()
        }

        _binding.checkBoxIgnoreChapter.setOnCheckedChangeListener { _, isChecked ->
            _binding.spinnerIgnoreChapter.isVisible = isChecked
            if (isChecked) {
                Log.i("IGNORE_CHAPTER", ignoreChapterPosition().toString())
                _binding.spinnerIgnoreChapter.setSelection(ignoreChapterPosition())
                _viewModel.chapters.value?.let {
                    _newCondition.ingnoreChapter = it.data?.get(ignoreChapterPosition())!!._id
                }
            } else {
                _newCondition.ingnoreChapter = ""
            }
        }
    }

    private fun ignoreChapterPosition(): Int {
        val ignoreChapterId = _viewModel.chapter.value?.data?.condition?.ignore_chapter_id
        return if (!ignoreChapterId.isNullOrBlank()) {
            val chaptersMapped =
                _viewModel.chapters.value?.data?.map { chapters -> chapters._id }
            chaptersMapped?.indexOf(ignoreChapterId) ?: 0
        } else {
            0
        }
    }

    private fun spinnerChangeCondition(spinner: Spinner) =
        object : OnItemSelectedListener {
            override fun onItemSelected(
                p0: AdapterView<*>?,
                p1: View?,
                p2: Int,
                p3: Long
            ) {
                when (spinner) {
                    _binding.spinnerConditionItem -> {
                        _viewModel.itemList.value?.let { _newCondition.itemId = it.data!![p2]._id }
                    }

                    _binding.spinnerConditionType -> {
                        _newCondition.checkType = _checkTypes[p2].checkType
                    }

                    _binding.spinnerIgnoreChapter -> {
                        _viewModel.chapters.value?.let {
                            _newCondition.ingnoreChapter = it.data!![p2]._id
                        }
                    }
                }
                Log.i("ITEMS_NEW", _newCondition.toString())
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }

    private fun textChangeCondition(field: TextInputEditText) = object : TextWatcher {
        override fun beforeTextChanged(
            p0: CharSequence?, p1: Int, p2: Int, p3: Int
        ) {
        }

        override fun onTextChanged(
            p0: CharSequence?, p1: Int, p2: Int, p3: Int
        ) {
            _binding.btnAddCondition.apply {
                isVisible = true
                isEnabled = true
            }

            when (field) {
                _binding.etConditionText -> _newCondition.text = p0.toString()
                _binding.etConditionQuantity -> _newCondition.quantity = p0.toString()
            }
        }

        override fun afterTextChanged(p0: Editable?) {}
    }


    companion object {
        data class CheckTypes(
            val checkType: String,
            val name: String
        ) {
            override fun toString(): String {
                return name
            }
        }

        data class NewCondition(
            var text: String,
            var quantity: String,
            var itemId: String,
            var checkType: String,
            var ingnoreChapter: String
        )

        const val EDIT_TYPE = "edit_type"
        const val STORY_ID = "story_id"
        const val CHAPTER_ID = "chapter_id"

        @JvmStatic
        fun newInstance(editType: String, storyId: String?, chapterId: String?) =
            EditChapterFragment().apply {
                arguments =
                    bundleOf(EDIT_TYPE to editType, STORY_ID to storyId, CHAPTER_ID to chapterId)
            }
    }
}