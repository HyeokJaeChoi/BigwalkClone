package com.example.bigwalkclone.model


import com.google.gson.annotations.SerializedName

data class ServiceModel(
    @SerializedName("id")
    val id: Int,
    @SerializedName("type")
    val type: String,
)