package com.example.readingquestsfun.rvadapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.example.readingquestsfun.R
import com.example.readingquestsfun.models.ChapterConditionModel
import com.example.readingquestsfun.models.ItemModel
import com.example.readingquestsfun.utils.BaseViewHolder
import com.example.readingquestsfun.utils.RVAdapter
import kotlinx.coroutines.ExperimentalCoroutinesApi

class StoryItemsAdapter(
    private val _onClick: (ItemModel) -> Unit
) :
    RVAdapter<ItemModel, StoryItemsAdapter.StoryItemsViewHolder>() {

    inner class StoryItemsViewHolder(view: View) :
        BaseViewHolder<ItemModel>(view) {

//        private val _textCondition: TextView by lazy{itemView.findViewById(R.id.tv_condition)}
        private val _itemName: TextView by lazy {itemView.findViewById(R.id.tv_item_name)}
        private val _itemDescription: TextView by lazy {itemView.findViewById(R.id.tv_item_description)}
        private val _itemMax: TextView by lazy {itemView.findViewById(R.id.tv_item_max)}
        private val _itemQuantity: TextView by lazy {itemView.findViewById(R.id.tv_item_quantity)}
        private val _itemType: TextView by lazy {itemView.findViewById(R.id.tv_item_type)}


        @ExperimentalCoroutinesApi
        override fun bind(data: ItemModel) {
            _itemName.text = data.name
            _itemDescription.text = data.description
            _itemMax.text = "Максимум: ${data.max}"
            _itemQuantity.text = "Количество: ${data.quantity}"
            _itemType.text = "Тип: ${data.type}"

            itemView.setOnClickListener {
                _onClick(data)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryItemsViewHolder =
        StoryItemsViewHolder(
            LayoutInflater.from(parent.context)
            .inflate(R.layout.rv_item_story_items, parent, false))
}