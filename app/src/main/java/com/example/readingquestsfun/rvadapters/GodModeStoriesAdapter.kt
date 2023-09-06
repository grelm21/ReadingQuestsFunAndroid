package com.example.readingquestsfun.rvadapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.readingquestsfun.R
import com.example.readingquestsfun.models.StoryModel
import com.example.readingquestsfun.utils.BaseViewHolder
import com.example.readingquestsfun.utils.RVAdapter
import kotlinx.coroutines.ExperimentalCoroutinesApi

class GodModeStoriesAdapter(private val _onClick: (StoryModel) -> Unit) :
    RVAdapter<StoryModel, GodModeStoriesAdapter.GodModeStoriesViewHolder>() {

    inner class GodModeStoriesViewHolder(view: View) : BaseViewHolder<StoryModel>(view) {

        private val _name: TextView by lazy{itemView.findViewById(R.id.tv_story_name)}
        private val _author: TextView by lazy{itemView.findViewById(R.id.tv_story_author)}
        private val _description: TextView by lazy{itemView.findViewById(R.id.tv_story_description)}

        @ExperimentalCoroutinesApi
        override fun bind(data: StoryModel) {
            _name.text = data.title
            _author.text = data.author
            _description.text = data.description

            itemView.setOnClickListener {
                _onClick(data)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GodModeStoriesViewHolder =
        GodModeStoriesViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.rv_item_god_mode_stories, parent, false)
        )
}