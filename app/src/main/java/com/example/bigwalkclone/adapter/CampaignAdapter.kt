package com.example.bigwalkclone.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bigwalkclone.R
import com.example.bigwalkclone.databinding.CampaignItemBinding
import com.example.bigwalkclone.model.CampaignModel
import com.example.bigwalkclone.util.DateTimeUtil

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
                Glide.with(root)
                    .load(campaign.smallListThumbnailImagePath)
                    .into(campaignThumbnail)

                campaignName.text = campaign.name
                campaignPromoter.text = campaign.campaignPromoterModel.name

                if(campaign.organizations.isEmpty()) {
                    campaignTypeContainer.setCardBackgroundColor(root.context.resources.getColor(R.color.campaign_type_open, null))
                    campaignType.text = root.context.getString(R.string.campaign_open)
                }
                else {
                    campaignTypeContainer.setCardBackgroundColor(root.context.resources.getColor(R.color.campaign_type_group, null))
                    campaignType.text = root.context.getString(R.string.campaign_group)
                }

                campaignProgressPercent.text = "${campaign.ratio}%"
                campaignProgressBar.progress = campaign.ratio

                if(DateTimeUtil.isCampaignActive(campaign.endDate)) {
                    campaignStatus.also { textView ->
                        textView.text = root.context.getString(R.string.campaign_in_progress)
                        textView.setTextColor(root.context.resources.getColor(R.color.primary, null))
                    }
                    campaignDonateBtn.visibility = View.VISIBLE
                }
                else {
                    if(campaign.myCampaignModel.story) {
                        campaignStatus.also { textView ->
                            textView.text = root.context.getString(R.string.campaign_donate_done)
                            textView.setTextColor(root.context.resources.getColor(R.color.campaign_donate_done, null))
                        }
                        campaignDonateCompleted.visibility = View.VISIBLE
                    }
                    else {
                        campaignStatus.also { textView ->
                            textView.text = root.context.getString(R.string.campaign_done)
                            textView.setTextColor(root.context.resources.getColor(R.color.campaign_done, null))
                        }
                    }

                    campaignThumbnail.alpha = 0.4f
                    campaignName.alpha = 0.4f
                    campaignPromoter.alpha = 0.4f
                }
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