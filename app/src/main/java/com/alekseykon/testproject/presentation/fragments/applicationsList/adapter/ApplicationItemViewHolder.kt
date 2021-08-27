package com.alekseykon.testproject.presentation.fragments.applicationsList.adapter

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.alekseykon.testproject.R
import com.alekseykon.testproject.core.extensions.hide
import com.alekseykon.testproject.core.extensions.setOnDebouncedClickListener
import com.alekseykon.testproject.core.extensions.show
import com.alekseykon.testproject.databinding.ItemApplicationSearchBinding
import com.alekseykon.testproject.domain.models.SearchItemEntity
import com.alekseykon.testproject.domain.models.ApplicationStatus

internal class ApplicationItemViewHolder(
    val binding: ItemApplicationSearchBinding,
    val context: Context,
    private val onItemClickListener: (searchItemEntity: SearchItemEntity) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: SearchItemEntity) {
        binding.goToPaymentButton.setOnDebouncedClickListener {
            onItemClickListener.invoke(item)
        }
        binding.phoneText.text = item.clientPhone
        binding.purchaseAmountText.text = context.getString(R.string.rubles, item.purchaseAmount.toString())
        when (item.applicationState) {
            ApplicationStatus.SIGNED -> {
                binding.statusText.text = context.getString(R.string.applications_list_title_signed)
                binding.statusText.setTextColor(ContextCompat.getColor(context, R.color.forest_green))
                binding.goToPaymentButton.show()
            }
            ApplicationStatus.REJECTED -> {
                binding.statusText.text = context.getString(R.string.applications_list_title_rejected)
                binding.statusText.setTextColor(ContextCompat.getColor(context, R.color.red_brand_main))
                binding.goToPaymentButton.hide()
            }
            else -> {
                binding.statusText.text = context.getString(R.string.applications_list_title_waiting)
                binding.statusText.setTextColor(ContextCompat.getColor(context, R.color.ship_cove))
                binding.goToPaymentButton.hide()
            }
        }
        binding.executePendingBindings()

    }
}