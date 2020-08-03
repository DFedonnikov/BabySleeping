package com.babysleep.ui.naturesounds

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.babysleep.presentation.naturesounds.SoundsItem
import com.babysleep.ui.SoundItemLayout

class SoundsAdapter(private val clickListener: (item: SoundsItem) -> Unit) :
    ListAdapter<SoundsItem, SoundsAdapter.SoundsViewHolder>(ITEM_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SoundsViewHolder {
        return SoundsViewHolder(SoundItemLayout(parent.context))
    }

    override fun onBindViewHolder(holder: SoundsViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    inner class SoundsViewHolder(private val view: SoundItemLayout) :
        RecyclerView.ViewHolder(view) {

        fun bind(item: SoundsItem) {
            view.render(item.renderData)
        }

    }
}

private val ITEM_CALLBACK = object : DiffUtil.ItemCallback<SoundsItem>() {
    override fun areItemsTheSame(oldItem: SoundsItem, newItem: SoundsItem): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: SoundsItem, newItem: SoundsItem): Boolean =
        oldItem == newItem
}