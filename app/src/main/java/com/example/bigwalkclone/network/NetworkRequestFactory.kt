package com.example.bigwalkclone.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkRequestFactory {
    private const val BASE_URL = "https://app-dev.bigwalk.co.kr:10000/api/campaigns/category/"

    val campaignService: CampaignService = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(OkHttpClient.Builder().addInterceptor(
            HttpLoggingInterceptor().apply { this.level = HttpLoggingInterceptor.Level.BODY }
        ).build())
        .build()
        .create(CampaignService::class.java)
}