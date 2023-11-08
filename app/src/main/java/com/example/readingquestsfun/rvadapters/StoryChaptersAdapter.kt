package com.example.readingquestsfun.rvadapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.example.readingquestsfun.R
import com.example.readingquestsfun.models.ChapterConditionModel
import com.example.readingquestsfun.models.ChapterModel
import com.example.readingquestsfun.utils.BaseViewHolder
import com.example.readingquestsfun.utils.RVAdapter
import com.google.android.material.switchmaterial.SwitchMaterial
import kotlinx.coroutines.ExperimentalCoroutinesApi

class StoryChaptersAdapter(
    private val _onClick: (ChapterModel) -> Unit,
    private val _onDemoSwitch: (ChapterModel) -> Unit
) : RVAdapter<ChapterModel, StoryChaptersAdapter.StoryChaptersViewHolder>() {

    inner class StoryChaptersViewHolder(view: View) :
        BaseViewHolder<ChapterModel>(view) {

        private val _chapterNote: TextView by lazy { itemView.findViewById(R.id.tv_chapter_note) }
        private val _chapterText: TextView by lazy { itemView.findViewById(R.id.tv_chapter_text) }
        private val _lootCount: TextView by lazy { itemView.findViewById(R.id.tv_loot_count) }
        private val _conditionCount: TextView by lazy { itemView.findViewById(R.id.tv_condition_count) }
        private val _nextChaptersCount: TextView by lazy { itemView.findViewById(R.id.tv_next_chapters_count) }
        private val _switchDemo: SwitchMaterial by lazy { itemView.findViewById(R.id.switch_demo) }

        @ExperimentalCoroutinesApi
        override fun bind(data: ChapterModel) {
            _chapterNote.text = data.note
            _chapterText.text = data.text
            _lootCount.text = data.loot.size.toString()
            _conditionCount.text = if (data.condition != null) "Есть" else "Нет"
            _nextChaptersCount.text = data.next_chapter_id.size.toString()

            itemView.setOnClickListener {
                _onClick(data)
            }

//            with(data.demo){
                _switchDemo.isChecked = data.demo
//            }

            _switchDemo.setOnCheckedChangeListener { _, _ ->
                _onDemoSwitch(data)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryChaptersViewHolder =
        StoryChaptersViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.rv_item_story_chapters, parent, false)
        )
}