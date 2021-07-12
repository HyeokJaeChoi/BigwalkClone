package com.example.bigwalkclone.viewmodel

import android.util.Log
import androidx.lifecycle.*
import androidx.paging.*
import com.example.bigwalkclone.model.CampaignCategoryModel
import com.example.bigwalkclone.model.CampaignModel
import com.example.bigwalkclone.paging.PagerFactory
import com.example.bigwalkclone.repository.CampaignDataSource
import kotlinx.coroutines.flow.*

class CampaignViewModel(private val category: Array<String>) : ViewModel() {
    private lateinit var campaignsLate: Flow<PagingData<CampaignModel>>

    private val _myCampaigns: MutableLiveData<Set<CampaignModel>> by lazy { MutableLiveData<Set<CampaignModel>>() }
    val myCampaigns: LiveData<Set<CampaignModel>> get() = _myCampaigns
    private val _categories: MutableLiveData<List<CampaignCategoryModel>> by lazy {
        MutableLiveData<List<CampaignCategoryModel>>(
            category.mapIndexed { index, categoryItem ->
                CampaignCategoryModel(categoryItem, index == 0)
            }
        )
    }
    val categories: LiveData<List<CampaignCategoryModel>> get() = _categories
    private var selectedCategoryPos = 0

    fun setMyCampaigns(newCampaigns: List<CampaignModel>) {
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

    fun selectCategory(position: Int) {
        _categories.value?.let {
            val newList = it
            newList[position].isSelected = true
            newList[selectedCategoryPos].isSelected = false

            _categories.value = newList
        }
    }
}