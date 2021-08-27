package com.alekseykon.testproject.presentation.fragments.applicationsList.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.alekseykon.testproject.R
import com.alekseykon.testproject.databinding.ItemApplicationSearchBinding
import com.alekseykon.testproject.domain.models.SearchItemEntity


internal class ApplicationItemAdapter(
    private val onItemClickListener: (searchItemEntity: SearchItemEntity) -> Unit
) : RecyclerView.Adapter<ApplicationItemViewHolder>() {

    private val items: MutableList<SearchItemEntity> = mutableListOf()

    fun setData(newItems: List<SearchItemEntity>) {
        val callback = ApplicationItemDiffCallback(newItems, this.items)
        val result = DiffUtil.calculateDiff(callback)
        this.items.clear()
        this.items.addAll(newItems)
        result.dispatchUpdatesTo(this)
    }

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ApplicationItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ItemApplicationSearchBinding = DataBindingUtil.inflate(
            inflater, R.layout.item_application_search, parent, false
        )
        return ApplicationItemViewHolder(binding, parent.context, onItemClickListener)
    }

    override fun onBindViewHolder(holder: ApplicationItemViewHolder, position: Int) {
        holder.bind(items[position])
    }

}