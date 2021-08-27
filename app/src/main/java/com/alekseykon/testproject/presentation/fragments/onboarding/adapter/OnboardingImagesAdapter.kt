package com.alekseykon.testproject.presentation.fragments.onboarding.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.alekseykon.testproject.R
import com.alekseykon.testproject.databinding.ItemOnboardingImagesBinding
import com.alekseykon.testproject.presentation.fragments.onboarding.model.OnboardingImageItem

internal class OnboardingImagesAdapter(
    private val items: List<OnboardingImageItem>
) : RecyclerView.Adapter<OnboardingImagesAdapter.OnboardingImagesViewHolder>() {

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnboardingImagesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ItemOnboardingImagesBinding = DataBindingUtil.inflate(
            inflater, R.layout.item_onboarding_images, parent, false
        )
        return OnboardingImagesViewHolder(binding, parent.context)
    }

    override fun onBindViewHolder(holder: OnboardingImagesViewHolder, position: Int) {
        holder.bind(items[position])
    }

    class OnboardingImagesViewHolder(val binding: ItemOnboardingImagesBinding, val context: Context) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: OnboardingImageItem) {
            val imageDrawable: Drawable? = AppCompatResources.getDrawable(context, item.imageRes)
            binding.onboardingIcon.setImageDrawable(imageDrawable)
            binding.onboardingText.text = context.getString(item.textRes)
            binding.onboardingCard.background.setTint(ContextCompat.getColor(context, item.colorRes))
            binding.executePendingBindings()
        }
    }
}