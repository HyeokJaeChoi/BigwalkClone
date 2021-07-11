package com.example.bigwalkclone.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bigwalkclone.databinding.MyCampaignItemBinding
import com.example.bigwalkclone.model.CampaignModel

class MyCampaignAdapter: ListAdapter<CampaignModel, MyCampaignAdapter.MyCampaignViewHolder>(MyCampaignComparator) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyCampaignViewHolder {
        val binding = MyCampaignItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyCampaignViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyCampaignViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    class MyCampaignViewHolder(private val binding: MyCampaignItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(campaign: CampaignModel) {
            binding.run {
                Glide.with(root)
                    .load(campaign.detailThumbnailImagePath)
                    .into(myCampaignThumbnail)

                myCampaignName.text = campaign.name

                if(campaign.myCampaignModel.story) {
                    myCampaignDonateDone.visibility = View.VISIBLE
                }
            }
        }
    }

    object MyCampaignComparator: DiffUtil.ItemCallback<CampaignModel>() {
        override fun areItemsTheSame(oldItem: CampaignModel, newItem: CampaignModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CampaignModel, newItem: CampaignModel): Boolean {
            return oldItem == newItem
        }
    }
}