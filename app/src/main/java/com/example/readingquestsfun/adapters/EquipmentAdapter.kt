package com.example.readingquestsfun.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.readingquestsfun.R
import com.example.readingquestsfun.models.EquipmentModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

class EquipmentAdapter(): RVAdapter<EquipmentModel, EquipmentAdapter.EquipmentViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EquipmentAdapter.EquipmentViewHolder =
        EquipmentViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_equipment, parent, false)
        )

inner class EquipmentViewHolder(view: View) : BaseViewHolder<EquipmentModel>(view){

    private val _image: ImageView by lazy { itemView.findViewById(R.id.icon_equipment) }
    @ExperimentalCoroutinesApi
    override fun bind(data: EquipmentModel) {
        _image.setImageResource(data.icon)
    }

}
}