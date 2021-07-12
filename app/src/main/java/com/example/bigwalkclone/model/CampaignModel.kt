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
    val donatedSteps: Long,
    @SerializedName("enabled")
    val enabled: Boolean,
    @SerializedName("endDate")
    val endDate: String,
    @SerializedName("formattedEndDate")
    val formattedEndDate: String,
    @SerializedName("formattedStartDate")
    val formattedStartDate: String,
    @SerializedName("goalPoint")
    val goalPoint: Long,
    @SerializedName("id")
    val id: Int,
    @SerializedName("largeListThumbnailImagePath")
    val largeListThumbnailImagePath: String?,
    @SerializedName("mediumListThumbnailImagePath")
    val mediumListThumbnailImagePath: String?,
    @SerializedName("my")
    val myCampaignModel: MyCampaignModel,
    @SerializedName("name")
    val name: String,
    @SerializedName("organizations")
    val organizations: List<OrganizationsModel>,
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
    val status: String,
    @SerializedName("service")
    val service: ServiceModel?,
    @SerializedName("event")
    val event: Any?,
    @SerializedName("additionalServiceId")
    val additionalServiceId: Int?,
)