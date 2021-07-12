package com.example.bigwalkclone.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CampaignViewModelFactory(val category: Array<String>): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(Array<String>::class.java).newInstance(category)
    }
}