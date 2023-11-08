package com.example.readingquestsfun.rvadapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.readingquestsfun.R
import com.example.readingquestsfun.models.ChapterConditionModel
import com.example.readingquestsfun.models.LootModel
import com.example.readingquestsfun.utils.BaseViewHolder
import com.example.readingquestsfun.utils.RVAdapter
import kotlinx.coroutines.ExperimentalCoroutinesApi

class EditChapterLootAdapter(
    private val _onClick: (LootModel) -> Unit,
    private val _onClickDelete: (LootModel) -> Unit
) :
    RVAdapter<LootModel, EditChapterLootAdapter.EditChapterLootViewHolder>() {

    inner class EditChapterLootViewHolder(view: View) :
        BaseViewHolder<LootModel>(view) {

//        private val _textCondition: TextView by lazy{itemView.findViewById(R.id.tv_condition)}
        private val _lootName: TextView by lazy {itemView.findViewById(R.id.tv_loot_item_name)}
        private val _lootDescription: TextView by lazy {itemView.findViewById(R.id.tv_loot_description)}
        private val _lootMax: TextView by lazy {itemView.findViewById(R.id.tv_loot_max)}
        private val _lootQuantity: TextView by lazy {itemView.findViewById(R.id.tv_loot_quantity)}
        private val _lootType: TextView by lazy {itemView.findViewById(R.id.tv_loot_type)}
        private val _delete: ImageView by lazy {itemView.findViewById(R.id.iv_delete)}


        @ExperimentalCoroutinesApi
        override fun bind(data: LootModel) {
            _lootName.text = data.item.name
            _lootDescription.text = data.text
            _lootMax.text = "Максимум: ${data.item.max}"
            _lootQuantity.text = "Количество: ${data.item.quantity}"
            _lootType.text = "Тип: ${data.item.type}"

            itemView.setOnClickListener {
                _onClick(data)
            }

            _delete.setOnClickListener {
                _onClickDelete(data)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EditChapterLootViewHolder =
        EditChapterLootViewHolder(
            LayoutInflater.from(parent.context)
            .inflate(R.layout.rv_item_edit_chapter_loot, parent, false))
}