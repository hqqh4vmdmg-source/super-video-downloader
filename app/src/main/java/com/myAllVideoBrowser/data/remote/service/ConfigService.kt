package com.myAllVideoBrowser.data.remote.service

import com.myAllVideoBrowser.data.local.room.entity.SupportedPage
import retrofit2.http.GET

interface ConfigService {

    @GET("supported_pages.json")
    suspend fun getSupportedPages(): List<SupportedPage>
}