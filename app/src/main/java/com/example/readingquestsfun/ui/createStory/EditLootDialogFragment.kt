package com.example.readingquestsfun.ui.createStory

import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
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
import android.widget.Spinner
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import com.example.readingquestsfun.R
import com.example.readingquestsfun.databinding.FragmentEditLootDialogBinding
import com.example.readingquestsfun.models.ItemModel
import com.example.readingquestsfun.viewModels.EditChapterViewModel
import org.koin.androidx.viewmodel.ext.android.getViewModel

class EditLootDialogFragment : DialogFragment() {

    private lateinit var _binding: FragmentEditLootDialogBinding
    private val _viewModel by lazy { requireParentFragment().getViewModel<EditChapterViewModel>() }

    private lateinit var _itemsAdapter: ArrayAdapter<ItemModel>

    private val _isNew by lazy {
        arguments?.getBoolean(IS_NEW)
    }

    private var newLootItem = LootItem("", "", "")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.setCanceledOnTouchOutside(false)

        _binding = FragmentEditLootDialogBinding.inflate(layoutInflater)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val spinnerSelectedListener by lazy {
            object : OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    _viewModel.itemList.value?.let { newLootItem.itemId = it.data!![p2]._id }
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {}
            }
        }


        _viewModel.lootList.observe(viewLifecycleOwner) {
            if (!_isNew!!) {
                _binding.apply {
                    _binding.etText.setText(it.data!!.text)
                    _binding.etQuantity.setText(it.data!!.item.quantity.toString())

                    val itemId = _viewModel.lootList.value?.let { it.data!!.item_id }
                    val mappedItemsId =
                        _viewModel.itemList.value?.let { items -> items.data!!.map { it._id } }
                    val indexItem = mappedItemsId?.indexOf(itemId)

                    spinnerItem.setSelection(indexItem ?: 0)
                }
            }
        }

        _viewModel.itemList.observe(viewLifecycleOwner) { items ->
            _itemsAdapter = ArrayAdapter(
                requireContext(),
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                items.data!!
            )
            _binding.spinnerItem.adapter = _itemsAdapter

        }

        _binding.spinnerItem.onItemSelectedListener = spinnerSelectedListener

        _binding.etText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                newLootItem.description = p0.toString()
            }

            override fun afterTextChanged(p0: Editable?) {}
        })

        _binding.etQuantity.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                newLootItem.quantity = p0.toString()
                Log.i("LOOT_ITEM", newLootItem.toString())
            }

            override fun afterTextChanged(p0: Editable?) {}
        })

        _binding.btnAddLoot.setOnClickListener {
            //проверять пустой ли лут
            if (_isNew!!) {
                if (newLootItem.itemId.isNullOrBlank() ||
                    newLootItem.description.isNullOrBlank() ||
                    newLootItem.quantity.isNullOrBlank()
                ) {
                    Toast.makeText(requireContext(), "Информация не заполнена", Toast.LENGTH_SHORT).show()
                } else {
                    _viewModel.addLoot(
                        newLootItem.itemId,
                        newLootItem.description,
                        newLootItem.quantity
                    )
                    dismiss()
                }
            } else {
                _viewModel.editLoot(
                    _viewModel.lootList.value.let { it!!.data!!._id },
                    newLootItem.itemId, newLootItem.quantity, newLootItem.description
                )
                dismiss()
            }
        }

        _binding.btnCancel.setOnClickListener {
            showAlertDialog()
        }
    }

    private fun showAlertDialog() {
        if (!_isNew!!){
            if (_viewModel.lootList.value.let { it!!.data!!.text != newLootItem.description } ||
                _viewModel.lootList.value.let { it!!.data!!.item.quantity.toString() != newLootItem.quantity } ||
                _viewModel.lootList.value.let { it!!.data!!.item_id != newLootItem.itemId })
                alertDialog() else dismiss()
        }else{
            if (newLootItem.description.isNotEmpty() ||
                newLootItem.quantity.isNotEmpty())
                alertDialog() else dismiss()
        }
    }

    private fun alertDialog(){
        val dialog = AlertDialog.Builder(requireContext())
        dialog.setTitle("Есть несохраненные изменения. Выйти и потерять данные?")
        dialog.setPositiveButton("Выйти") { _, _ -> dismiss() }
        dialog.setNegativeButton("Продолжить редактирование") { _, _ -> }
        dialog.show()
    }

    companion object {

        data class LootItem(
            var itemId: String,
            var description: String,
            var quantity: String
        )

        private const val IS_NEW = "is_new"

        @JvmStatic
        fun newInstance(isNew: Boolean) = EditLootDialogFragment().apply {
            arguments = bundleOf(IS_NEW to isNew)
        }
    }
}