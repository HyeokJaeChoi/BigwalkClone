package com.example.bigwalkclone.model


import com.google.gson.annotations.SerializedName

data class CampaignPromoterModel(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)