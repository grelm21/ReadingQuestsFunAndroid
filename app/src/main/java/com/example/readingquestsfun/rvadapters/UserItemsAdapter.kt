package com.example.readingquestsfun.rvadapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
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
        private val _image: ImageView by lazy {itemView.findViewById(R.id.item_image)}

        @ExperimentalCoroutinesApi
        override fun bind(data: ItemModel) {
            _name.text = data.name
            _count.text = data.quantity.toString()

            Glide.with(context)
                .load(IMAGE_URL)
                .into(_image)
        }
    }

    companion object{
        const val IMAGE_URL = "https://vk.com/s/v1/doc/17HuXMenBx3DSUqIoDeZ8cSjMzgrXz0BFrSpzl7M9NMSq89QF2M"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserItemsViewHolder =
        UserItemsViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.rv_item_chapter_equipment, parent, false)
        )
}