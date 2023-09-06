package com.example.readingquestsfun.rvadapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.example.readingquestsfun.R
import com.example.readingquestsfun.models.ChapterConditionModel
import com.example.readingquestsfun.utils.BaseViewHolder
import com.example.readingquestsfun.utils.RVAdapter
import kotlinx.coroutines.ExperimentalCoroutinesApi

class ChapterConditionsAdapter(private val _onClick: (ChapterConditionModel, position: Int) -> Unit) :
    RVAdapter<ChapterConditionModel, ChapterConditionsAdapter.ChapterConditionViewHolder>() {

    inner class ChapterConditionViewHolder(view: View) :
        BaseViewHolder<ChapterConditionModel>(view) {

        private val _textCondition: TextView by lazy{itemView.findViewById(R.id.tv_condition)}

        @ExperimentalCoroutinesApi
        override fun bind(data: ChapterConditionModel) {
            _textCondition.text = data.condition.text

            itemView.setOnClickListener {
                _onClick(data, adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChapterConditionViewHolder =
        ChapterConditionViewHolder(
            LayoutInflater.from(parent.context)
            .inflate(R.layout.rv_item_chapter_conditions, parent, false))
}