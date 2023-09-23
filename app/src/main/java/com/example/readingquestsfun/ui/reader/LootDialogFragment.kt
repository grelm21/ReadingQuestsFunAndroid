package com.example.readingquestsfun.ui.reader

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.readingquestsfun.R
import com.example.readingquestsfun.databinding.FragmentLootDialogBinding
import com.example.readingquestsfun.ui.MainActivity
import com.example.readingquestsfun.viewModels.ReaderViewModel

class LootDialogFragment : DialogFragment() {

    private lateinit var _binding: FragmentLootDialogBinding
    private val _viewModel: ReaderViewModel by activityViewModels()

    private val _index by lazy {
        arguments?.getInt(INDEX)
    }

    private val _info by lazy {
        arguments?.getString(INFO)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.setCanceledOnTouchOutside(false)
        _binding = FragmentLootDialogBinding.inflate(inflater)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /**
         * В зависимости от типа _info - меняется наполнение и логика диалога
         */
        when (_info!!) {

            /**
             * LOOT - увеломления о айтемах и статистике, которая приходит в главе
             */

            "LOOT" -> {

                /**
                 * подписываемся на главу, которую уже получили ранее
                 */
                _viewModel.chapter.observe(viewLifecycleOwner) { chapter ->
                    val loot = chapter.data!!.loot[_index!!]
                    _binding.tvItemDescription.text = loot.text
                    _binding.tvItemName.text = loot.item.name
                }

                /**
                 *получаем value главы, вытаскиваем весь лут, по индексу берем каждый лут
                 */
                _viewModel.chapter.value.let { chapter ->
                    val loot = chapter?.data?.loot?.get(_index!!)

                    /**
                     * кнопка accept посылает айтем юзеру
                     * кнопка dismiss отправляет айтем в резер, его можно взять потом
                     */
                    _binding.btnAccept.setOnClickListener {
                        loot.let {
                            _viewModel.addItemToUser(
                                it!!.item_id,
                                it.item.quantity
                            )
                        }
                        dismiss()
                    }

//                    _binding.btnDismiss.setOnClickListener {
//                        val loot = chapter?.data?.loot?.get(_index!!)
//                        loot.let { _viewModel.addReservedItem(it!!) }
//
//                        dismiss()
//                    }

                    /**
                     * в зависимотсти от типа лута кнопка dismiss может быть видна или нет
                     */
                    with("STATISTICS") {
                        _binding.btnAccept.text =
                            if (loot?.item?.type == this) getString(R.string.dialog_loot_btn_accept_stats)
                            else getString(R.string.dialog_loot_btn_accept_not_stats)
                        _binding.btnDismiss.isVisible = loot?.item?.type != this
                    }
                }
            }

            /**
             * CONDITION выдаем список уловий для открытия следующих глав
             */
            "CONDITION" -> {

                /**
                 * подписываемся на список условий, который компиллим во вьюмодели
                 */
                _viewModel.conditions.observe(viewLifecycleOwner) { conditions ->
                    _binding.tvItemDescription.text = conditions[_index!!].condition.text
                    _binding.tvItemName.text = conditions[_index!!].condition.item.name

                    _binding.btnAccept.text = getString(R.string.dialog_condition_btn_accept)
                    _binding.btnDismiss.text = getString(R.string.dialog_condition_btn_dismiss)
                }

                _viewModel.conditions.value.let { conditions ->

                    val conditionToCheck = conditions?.get(_index!!)!!

                    _viewModel.checkCondition(conditionToCheck)

                    /**
                     *  кнопка accept, если check: ADD то изменяем количество у юзера
                     *  если COMPARE и проверка пройдена то по клитку ничего не делаем
                     */
                    _binding.btnAccept.setOnClickListener {
                        if (conditions?.get(_index!!)!!.condition.check!! == "ADD") {
                            _viewModel.addItemToUser(
                                conditions[_index!!].condition.item_id,
                                conditions[_index!!].condition.item.quantity
                            )
                        }
                        _viewModel.getChapter(conditions[_index!!].chapter_id)
                        dismiss()
                    }

                    _binding.btnIgnore.setOnClickListener {
                        _viewModel.getChapter(conditions[_index!!].condition.ignore_chapter_id)
                        dismiss()
                    }

                    /**
                     * проверяем условие на соостветствие, если не проходит - блокируем кнопку
                     */
                    _viewModel.conditionCheck.observe(viewLifecycleOwner) {
                        _binding.btnAccept.isEnabled = it!!
                    }

//                    _binding.btnDismiss.setOnClickListener {
//                        dismiss()
//                    }
                }
            }


            /**
             * зарезервированный тайтем, открывается при клике на зарезервированный в экипировке
             */

            "RESERVED" -> {

                /**
                 * получаем список зарезервированный айтемов из вьюмодели
                 */
                _viewModel.reservedItemsMutable.observe(viewLifecycleOwner) { reservedItem ->
                    _binding.tvItemDescription.text = reservedItem[_index!!].text
                    _binding.tvItemName.text = reservedItem[_index!!].item.name
                }

                /**
                 *  кнопка accept, изменяем количество у юзера
                 *  отменяем диалог
                 *  вызываем обновленные айтемы юзера. В экипе подписанны - получим обновленный лист
                 *  удаляем айтем, который добавили юзеру, из зарезервированных
                 */
                _viewModel.reservedItemsMutable.value.let { reservedItems ->
                    val reservedItem = reservedItems?.get(_index!!)
                    _binding.btnAccept.setOnClickListener {
                        _viewModel.addItemToUser(
                            reservedItem!!.item_id,
                            reservedItem.item.quantity
                        )
                        dismiss()
                        _viewModel.getUserItems()
                        _viewModel.removeReservedItem(_index!!)
                    }

//                    _binding.btnDismiss.setOnClickListener {
//                        dismiss()
//                    }
                }
            }

            "DEMO" -> {
                _binding.btnAccept.text = getString(R.string.dialog_demo_btn_accept)
                _binding.btnDismiss.text = getString(R.string.dialog_demo_btn_dismiss)

                _binding.tvItemDescription.text = getString(R.string.demo_thanks_for_reading)
                _binding.tvItemName.text =
                    getString(R.string.demo_text)

                _binding.btnAccept.setOnClickListener {
                    startActivity(Intent(requireContext(), MainActivity::class.java))
                    dismiss()
                }
//                _binding.btnDismiss.setOnClickListener {
//                    dismiss()
//                }
            }
        }

        /**
         *  кнопка dismiss всегда отменяет диалог, кроме случая, если LOOT - если отказываемсчя от лута, то он попадает в зарезервированные
         */

        if (_info != "CONDITION"){
            _binding.btnIgnore.visibility = GONE
        }

        _binding.btnDismiss.setOnClickListener {
            with("LOOT") {
                if (_info == this) {
                    _viewModel.chapter.value.let { chapter ->
                        val loot = chapter?.data?.loot?.get(_index!!)
                        loot.let { _viewModel.addReservedItem(it!!) }
                    }
                }
                dismiss()
            }
        }
    }

    companion object {
        private const val INDEX = "index"
        private const val INFO = "is_loot"

        //LOOT, CONDITION, DEMO, RESERVED
        fun newInstance(index: Int, info: String) = LootDialogFragment().apply {
            arguments = bundleOf(INDEX to index, INFO to info)
        }
    }
}