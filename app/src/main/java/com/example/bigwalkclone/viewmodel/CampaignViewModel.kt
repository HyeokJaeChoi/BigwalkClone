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
            pagingSourceFactory = { CampaignDataSource(this) },
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

    private val _myCampaigns: MutableLiveData<List<CampaignModel>> by lazy { MutableLiveData<List<CampaignModel>>() }
    val myCampaigns: LiveData<List<CampaignModel>> get() = _myCampaigns

    fun setMyCampaigns(newCampaigns: List<CampaignModel>) {
        val prevMyCampaigns = _myCampaigns.value ?: emptyList()
        val newMyCampaigns = mutableListOf<CampaignModel>().apply {
            addAll(prevMyCampaigns)
            addAll(newCampaigns)
        }

        _myCampaigns.postValue(newMyCampaigns)
    }
}