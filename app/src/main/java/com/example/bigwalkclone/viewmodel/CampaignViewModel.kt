package com.example.bigwalkclone.viewmodel

import android.util.Log
import androidx.lifecycle.*
import androidx.paging.*
import com.example.bigwalkclone.model.CampaignModel
import com.example.bigwalkclone.paging.PagerFactory
import com.example.bigwalkclone.repository.CampaignDataSource
import kotlinx.coroutines.flow.*
import java.lang.Exception

class CampaignViewModel() : ViewModel() {
    private lateinit var campaignsLate: Flow<PagingData<CampaignModel>>

    private val _myCampaigns: MutableLiveData<Set<CampaignModel>> by lazy { MutableLiveData<Set<CampaignModel>>() }
    val myCampaigns: LiveData<Set<CampaignModel>> get() = _myCampaigns

    fun setMyCampaigns(newCampaigns: List<CampaignModel>) {
        newCampaigns.forEach {
            Log.d(javaClass.simpleName, "${it.id}, ${it.name}")
        }
        val prevMyCampaigns = _myCampaigns.value ?: emptySet()
        val newMyCampaigns = mutableSetOf<CampaignModel>().apply {
            addAll(prevMyCampaigns)
            addAll(newCampaigns)
        }

        _myCampaigns.postValue(newMyCampaigns)
    }

    fun getCampaign(sortCondition: Comparator<CampaignModel>? = null, filterCondition: (suspend (CampaignModel) -> Boolean)? = null): Flow<PagingData<CampaignModel>> {
        val pageSize = if(sortCondition != null) 200 else 20

        campaignsLate = PagerFactory
            .createPager(
                pageSize = pageSize,
                dataSource = CampaignDataSource(this, sortCondition = sortCondition, pageSize = pageSize)
            )
            .flow.cachedIn(viewModelScope)

        return if(filterCondition != null) {
            campaignsLate.map {
                it.filter(filterCondition)
            }
        }
        else {
            campaignsLate
        }
    }
}