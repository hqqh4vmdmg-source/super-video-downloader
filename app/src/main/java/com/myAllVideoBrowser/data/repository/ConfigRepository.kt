package com.myAllVideoBrowser.data.repository

import androidx.annotation.VisibleForTesting
import com.myAllVideoBrowser.data.local.room.entity.SupportedPage
import com.myAllVideoBrowser.di.qualifier.LocalData
import com.myAllVideoBrowser.di.qualifier.RemoteData
import javax.inject.Inject
import javax.inject.Singleton

interface ConfigRepository {

    suspend fun getSupportedPages(): List<SupportedPage>

    suspend fun saveSupportedPages(supportedPages: List<SupportedPage>)
}

@Singleton
class ConfigRepositoryImpl @Inject constructor(
    @param:LocalData private val localDataSource: ConfigRepository,
    @param:RemoteData private val remoteDataSource: ConfigRepository
) : ConfigRepository {

    @VisibleForTesting
    internal var cachedSupportedPages = listOf<SupportedPage>()

    override suspend fun getSupportedPages(): List<SupportedPage> {
        if (cachedSupportedPages.isNotEmpty()) return cachedSupportedPages

        val local = localDataSource.getSupportedPages()
        if (local.isNotEmpty()) {
            cachedSupportedPages = local
            return local
        }

        val remote = remoteDataSource.getSupportedPages()
        localDataSource.saveSupportedPages(remote)
        cachedSupportedPages = remote
        return remote
    }

    override suspend fun saveSupportedPages(supportedPages: List<SupportedPage>) {
        remoteDataSource.saveSupportedPages(supportedPages)
        localDataSource.saveSupportedPages(supportedPages)
        cachedSupportedPages = supportedPages
    }
}