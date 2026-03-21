package com.myAllVideoBrowser.data.local

import com.myAllVideoBrowser.data.local.room.dao.ConfigDao
import com.myAllVideoBrowser.data.local.room.entity.SupportedPage
import com.myAllVideoBrowser.data.repository.ConfigRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ConfigLocalDataSource @Inject constructor(
    private val configDao: ConfigDao
) : ConfigRepository {

    override suspend fun getSupportedPages(): List<SupportedPage> = configDao.getSupportedPages()

    override suspend fun saveSupportedPages(supportedPages: List<SupportedPage>) {
        configDao.insertAllSupportedPages(supportedPages)
    }
}