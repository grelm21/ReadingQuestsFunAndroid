package com.example.readingquestsfun.rvadapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.readingquestsfun.R
import com.example.readingquestsfun.models.LootModel
import com.example.readingquestsfun.utils.BaseViewHolder
import com.example.readingquestsfun.utils.RVAdapter
import kotlinx.coroutines.ExperimentalCoroutinesApi

class ReservedItemsAdapter(private val _onClick: (LootModel, position: Int) -> Unit) :
    RVAdapter<LootModel, ReservedItemsAdapter.ReservedItemsViewHolder>() {
    inner class ReservedItemsViewHolder(view: View) : BaseViewHolder<LootModel>(view) {

        private val _descriptionLoot: TextView by lazy { itemView.findViewById(R.id.tv_description) }

        @ExperimentalCoroutinesApi
        override fun bind(data: LootModel) {
            _descriptionLoot.text = data.text

            itemView.setOnClickListener {
                _onClick(data, adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReservedItemsViewHolder =
        ReservedItemsViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.rv_item_chapter_reserved, parent, false)
        )

}