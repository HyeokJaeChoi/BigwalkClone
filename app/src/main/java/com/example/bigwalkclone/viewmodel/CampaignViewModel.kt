package com.example.bigwalkclone.viewmodel

import androidx.lifecycle.*
import androidx.paging.*
import com.example.bigwalkclone.model.CampaignModel
import com.example.bigwalkclone.repository.CampaignDataSource
import kotlinx.coroutines.flow.*

class CampaignViewModel : ViewModel() {
    val campaigns: Flow<PagingData<CampaignModel>> by lazy {
        Pager(
            config = PagingConfig(
                pageSize = 20,
            ),
            pagingSourceFactory = { CampaignDataSource() },
        ).flow.cachedIn(viewModelScope)
    }

    val campaignsOpen: Flow<PagingData<CampaignModel>> by lazy {
        campaigns.map { pagingData ->
            pagingData.filter { campaign ->
                campaign.organizations.isEmpty()
            }
        }
    }

    val campaignsGroup: Flow<PagingData<CampaignModel>> by lazy {
        campaigns.map { pagingData ->
            pagingData.filter { campaign ->
                campaign.organizations.isNotEmpty()
            }
        }
    }
}