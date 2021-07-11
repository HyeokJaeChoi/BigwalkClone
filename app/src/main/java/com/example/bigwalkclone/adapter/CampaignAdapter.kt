package com.example.bigwalkclone.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.bigwalkclone.databinding.CampaignItemBinding
import com.example.bigwalkclone.model.CampaignModel

class CampaignAdapter() :
    PagingDataAdapter<CampaignModel, CampaignAdapter.CampaignViewHolder>(CampaignComparator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CampaignViewHolder {
        val binding = CampaignItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CampaignViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CampaignViewHolder, position: Int) {
        getItem(position)?.let { campaign ->
            holder.bind(campaign)
        }
    }

    class CampaignViewHolder(private val campaignBinding: CampaignItemBinding): RecyclerView.ViewHolder(campaignBinding.root) {
        fun bind(campaign: CampaignModel) {
            campaignBinding.run {

            }
        }
    }

    object CampaignComparator: DiffUtil.ItemCallback<CampaignModel>() {
        override fun areItemsTheSame(oldItem: CampaignModel, newItem: CampaignModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CampaignModel, newItem: CampaignModel): Boolean {
            return oldItem == newItem
        }
    }

}