package com.myAllVideoBrowser.data.local

import com.myAllVideoBrowser.data.local.room.dao.ProgressDao
import com.myAllVideoBrowser.data.local.room.entity.ProgressInfo
import com.myAllVideoBrowser.data.repository.ProgressRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProgressLocalDataSource @Inject constructor(
    private val progressDao: ProgressDao
) : ProgressRepository {

    override fun getProgressInfos(): Flow<List<ProgressInfo>> = progressDao.getProgressInfos()

    override suspend fun saveProgressInfo(progressInfo: ProgressInfo) =
        progressDao.insertProgressInfo(progressInfo)

    override suspend fun deleteProgressInfo(progressInfo: ProgressInfo) =
        progressDao.deleteProgressInfo(progressInfo)
}