package com.alekseykon.testproject.presentation.fragments.onboarding.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.alekseykon.testproject.R
import com.alekseykon.testproject.databinding.ItemOnboardingButtonBinding
import com.alekseykon.testproject.presentation.fragments.onboarding.model.OnboardingButtonItem

internal class OnboardingButtonsAdapter(
    private var items: MutableList<OnboardingButtonItem>,
    private val onItemClickListener: (textIndex: Int) -> Unit
) : RecyclerView.Adapter<OnboardingButtonsAdapter.OnboardingButtonsViewHolder>() {

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnboardingButtonsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ItemOnboardingButtonBinding = DataBindingUtil.inflate(
            inflater, R.layout.item_onboarding_button, parent, false
        )
        return OnboardingButtonsViewHolder(binding, parent.context, onItemClickListener)
    }

    override fun onBindViewHolder(holder: OnboardingButtonsViewHolder, position: Int) {
        holder.bind(items[position], position)
    }

    fun setData(data: List<OnboardingButtonItem>) {
        items.clear()
        items.addAll(data)
        notifyDataSetChanged()
    }


    inner class OnboardingButtonsViewHolder(
        val binding: ItemOnboardingButtonBinding,
        val context: Context,
        private val onItemClickListener: (textIndex: Int) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: OnboardingButtonItem, position: Int) {
            binding.textIndex.apply {
                text = item.textLabel
                background = AppCompatResources.getDrawable(
                    context,
                    if (item.isSelected) R.drawable.shape_purple else R.drawable.shape_white
                )
                setTextColor(
                    ContextCompat.getColor(
                        context,
                        if (item.isSelected) R.color.white else R.color.link_water
                    )
                )
            }
            binding.contentBody.setOnClickListener {
                onItemClickListener.invoke(position)
            }
            binding.executePendingBindings()

        }
    }
}