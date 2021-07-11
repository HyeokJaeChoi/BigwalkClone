package com.example.bigwalkclone.model


import com.google.gson.annotations.SerializedName

data class MyCampaignModel(
    @SerializedName("donatedSteps")
    val donatedSteps: Int,
    @SerializedName("lastDonatedDateTime")
    val lastDonatedDateTime: String?,
    @SerializedName("ranking")
    val ranking: Int,
    @SerializedName("story")
    val story: Boolean
)