package com.example.readingquestsfun.rvadapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.readingquestsfun.R
import com.example.readingquestsfun.models.ItemModel
import com.example.readingquestsfun.utils.BaseViewHolder
import com.example.readingquestsfun.utils.RVAdapter
import com.google.android.material.progressindicator.LinearProgressIndicator
import kotlinx.coroutines.ExperimentalCoroutinesApi

class UserStatisticsAdapter() :
    RVAdapter<ItemModel, UserStatisticsAdapter.UserStatisticsViewHolder>() {

    inner class UserStatisticsViewHolder(view: View) : BaseViewHolder<ItemModel>(view) {

        private val _progress: LinearProgressIndicator by lazy { itemView.findViewById(R.id.progress_indicator) }
        private val _textName: TextView by lazy { itemView.findViewById(R.id.tv_indicator_name) }
        private val _textCount: TextView by lazy { itemView.findViewById(R.id.tv_indicator_count) }

        @ExperimentalCoroutinesApi
        override fun bind(data: ItemModel) {
            _textName.text = data.name
            _textCount.text = "${data.quantity}/${data.max}"
            _progress.progress = data.quantity
            _progress.max = data.max
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserStatisticsViewHolder =
        UserStatisticsViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.rv_item_chapter_statistics, parent, false)
        )
}