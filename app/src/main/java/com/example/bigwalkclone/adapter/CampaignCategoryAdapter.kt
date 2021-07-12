package com.example.bigwalkclone.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.bigwalkclone.R
import com.example.bigwalkclone.databinding.CampaignCategoryItemBinding
import com.example.bigwalkclone.model.CampaignCategoryModel

class CampaignCategoryAdapter(private val onClick: (Int) -> Unit): ListAdapter<CampaignCategoryModel, CampaignCategoryAdapter.CampaignCategoryViewHolder>(CampaignCategoryComparator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CampaignCategoryViewHolder {
        val binding = CampaignCategoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CampaignCategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CampaignCategoryViewHolder, position: Int) {
        holder.bind(getItem(position), onClick)
    }

    class CampaignCategoryViewHolder(private val binding: CampaignCategoryItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(category: CampaignCategoryModel, onClick: (Int) -> Unit) {
            binding.run {
                categoryName.text = category.category

                val backgroundColor: Int
                val textColor: Int

                if(category.isSelected) {
                    backgroundColor = root.context.resources.getColor(R.color.primary, null)
                    textColor = root.context.resources.getColor(R.color.white, null)

                    categoryContainer.setOnClickListener(null)
                }
                else {
                    backgroundColor = root.context.resources.getColor(R.color.white, null)
                    textColor = root.context.resources.getColor(R.color.black, null)

                    categoryContainer.setOnClickListener { onClick(bindingAdapterPosition)}
                }

                categoryContainer.setCardBackgroundColor(backgroundColor)
                categoryName.setTextColor(textColor)
            }
        }
    }

    object CampaignCategoryComparator: DiffUtil.ItemCallback<CampaignCategoryModel>() {
        override fun areItemsTheSame(
            oldItem: CampaignCategoryModel,
            newItem: CampaignCategoryModel
        ): Boolean {
            return oldItem.category == newItem.category
        }

        override fun areContentsTheSame(
            oldItem: CampaignCategoryModel,
            newItem: CampaignCategoryModel
        ): Boolean {
            return oldItem.category == newItem.category
        }
    }
}