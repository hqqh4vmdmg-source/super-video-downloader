package com.myAllVideoBrowser.data.remote

import com.myAllVideoBrowser.data.local.room.entity.SupportedPage
import com.myAllVideoBrowser.data.remote.service.ConfigService
import com.myAllVideoBrowser.data.repository.ConfigRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ConfigRemoteDataSource @Inject constructor(
    private val configService: ConfigService
) : ConfigRepository {

    override suspend fun getSupportedPages(): List<SupportedPage> =
        configService.getSupportedPages()

    override suspend fun saveSupportedPages(supportedPages: List<SupportedPage>) {}
}