package com.example.bigwalkclone.viewmodel

import android.util.Log
import androidx.lifecycle.*
import androidx.paging.*
import com.example.bigwalkclone.model.CampaignModel
import com.example.bigwalkclone.repository.CampaignDataSource

class CampaignViewModel : ViewModel() {
    val campaigns: LiveData<PagingData<CampaignModel>> by lazy {
        Pager(
            config = PagingConfig(
                pageSize = 20,
            ),
            pagingSourceFactory = { CampaignDataSource() },
        ).liveData.cachedIn(viewModelScope)
    }
}