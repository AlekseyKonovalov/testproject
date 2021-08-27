package com.alekseykon.testproject.presentation.fragments.applicationsList.adapter

import com.alekseykon.testproject.domain.models.SearchItemEntity
import com.alekseykon.testproject.presentation.core.adapter.ItemDiffCallback

internal class ApplicationItemDiffCallback(
    newItems: List<SearchItemEntity>,
    oldItems: List<SearchItemEntity>
) : ItemDiffCallback<SearchItemEntity>(oldItems, newItems) {

    override fun areItemsTheSame(oldItem: SearchItemEntity, newItem: SearchItemEntity): Boolean {
        return oldItem.applicationId == newItem.applicationId
    }

    override fun areContentsTheSame(oldItem: SearchItemEntity, newItem: SearchItemEntity): Boolean {
        return oldItem.applicationState == newItem.applicationState

    }

}