package com.example.bigwalkclone.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.bigwalkclone.model.CampaignModel
import com.example.bigwalkclone.network.NetworkRequestFactory
import java.lang.Exception

class CampaignDataSource: PagingSource<Int, CampaignModel>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CampaignModel> {
        return try {
            val pageNumber = params.key ?: 0
            val campaigns = NetworkRequestFactory.campaignService.getCampaigns(page = pageNumber)
            LoadResult.Page(
                data = campaigns,
                prevKey = null,
                nextKey = pageNumber + 1
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