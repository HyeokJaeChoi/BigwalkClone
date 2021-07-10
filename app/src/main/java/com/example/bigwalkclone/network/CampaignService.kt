package com.example.bigwalkclone.network

import com.example.bigwalkclone.model.CampaignModel
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CampaignService {

    @GET("{category}/story")
    suspend fun getCampaigns(
        @Path("category") category: Int = 0,
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 20
    ): List<CampaignModel>

}