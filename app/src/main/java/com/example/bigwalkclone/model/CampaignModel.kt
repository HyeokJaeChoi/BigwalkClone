package com.example.bigwalkclone.model


import com.google.gson.annotations.SerializedName

data class CampaignModel(
    @SerializedName("beneficiary")
    val beneficiary: String,
    @SerializedName("beneficiaryBtn")
    val beneficiaryBtn: String,
    @SerializedName("beneficiaryLink")
    val beneficiaryLink: String,
    @SerializedName("campaignPromoter")
    val campaignPromoterModel: CampaignPromoterModel,
    @SerializedName("categoryId")
    val categoryId: Int,
    @SerializedName("detailThumbnailImagePath")
    val detailThumbnailImagePath: String,
    @SerializedName("donatedSteps")
    val donatedSteps: Int,
    @SerializedName("enabled")
    val enabled: Boolean,
    @SerializedName("endDate")
    val endDate: String,
    @SerializedName("formattedEndDate")
    val formattedEndDate: String,
    @SerializedName("formattedStartDate")
    val formattedStartDate: String,
    @SerializedName("goalPoint")
    val goalPoint: Int,
    @SerializedName("id")
    val id: Int,
    @SerializedName("largeListThumbnailImagePath")
    val largeListThumbnailImagePath: Any,
    @SerializedName("mediumListThumbnailImagePath")
    val mediumListThumbnailImagePath: Any,
    @SerializedName("my")
    val myCampaignModel: MyCampaignModel,
    @SerializedName("name")
    val name: String,
    @SerializedName("organizations")
    val organizations: List<Any>,
    @SerializedName("participantCount")
    val participantCount: Int,
    @SerializedName("ratio")
    val ratio: Int,
    @SerializedName("remainedDaysToEnd")
    val remainedDaysToEnd: Int,
    @SerializedName("serviceOnTime")
    val serviceOnTime: Boolean,
    @SerializedName("smallListThumbnailImagePath")
    val smallListThumbnailImagePath: String,
    @SerializedName("smsId")
    val smsId: Int,
    @SerializedName("startDate")
    val startDate: String,
    @SerializedName("status")
    val status: String
)