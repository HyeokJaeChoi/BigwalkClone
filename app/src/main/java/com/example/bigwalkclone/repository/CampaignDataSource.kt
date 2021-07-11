package com.example.bigwalkclone.repository

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.bigwalkclone.model.CampaignModel
import com.example.bigwalkclone.network.NetworkRequestFactory
import com.example.bigwalkclone.viewmodel.CampaignViewModel
import java.lang.Exception

class CampaignDataSource(private val viewModel: CampaignViewModel): PagingSource<Int, CampaignModel>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CampaignModel> {
        return try {
            val pageNumber = params.key ?: 0
            val campaigns = NetworkRequestFactory.campaignService.getCampaigns(page = pageNumber)
            val nextKey = if(campaigns.size >= 20) {
                pageNumber + 1
            }
            else {
                null
            }
            viewModel.setMyCampaigns(campaigns.filter { campaign -> campaign.myCampaignModel.lastDonatedDateTime != null })

            LoadResult.Page(
                data = campaigns,
                prevKey = null,
                nextKey = nextKey,
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, CampaignModel>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}