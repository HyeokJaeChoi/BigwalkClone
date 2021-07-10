package com.example.bigwalkclone.network

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkRequestFactory {
    private const val BASE_URL = "https://app-dev.bigwalk.co.kr:10000/api/campaigns/category/"

    val campaignService: CampaignService = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(OkHttpClient.Builder()
            .addInterceptor(Interceptor {
                val request = it.request().newBuilder().addHeader("X-AUTH-TOKEN", "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxODUiLCJyb2xlcyI6WyJST0xFX1VTRVIiXSwiaWF0IjoxNjExNTYzMzgxLCJleHAiOjE3MDYxNzEzODF9._4DPRRFx09yIBVLqwbTGVSuP6vy5fM4UP3vJXszfP4w")
                it.proceed(request.build())
            })
            .addInterceptor(
            HttpLoggingInterceptor().apply {
                this.level = HttpLoggingInterceptor.Level.BODY
            }
        ).build())
        .build()
        .create(CampaignService::class.java)
}