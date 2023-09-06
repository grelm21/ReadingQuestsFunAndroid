package com.example.readingquestsfun.rvadapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.readingquestsfun.R
import com.example.readingquestsfun.models.ItemModel
import com.example.readingquestsfun.utils.BaseViewHolder
import com.example.readingquestsfun.utils.RVAdapter
import kotlinx.coroutines.ExperimentalCoroutinesApi

class UserItemsAdapter() :
    RVAdapter<ItemModel, UserItemsAdapter.UserItemsViewHolder>() {

    inner class UserItemsViewHolder(view: View) : BaseViewHolder<ItemModel>(view) {

        private val _name: TextView by lazy { itemView.findViewById(R.id.tv_item_name) }
        private val _count: TextView by lazy { itemView.findViewById(R.id.tv_item_count) }

        @ExperimentalCoroutinesApi
        override fun bind(data: ItemModel) {
            _name.text = data.name
            _count.text = data.quantity.toString()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserItemsViewHolder =
        UserItemsViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.rv_item_chapter_equipment, parent, false)
        )
}