package com.myAllVideoBrowser.data.repository

import com.myAllVideoBrowser.data.local.room.entity.ProgressInfo
import com.myAllVideoBrowser.di.qualifier.LocalData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

interface ProgressRepository {

    fun getProgressInfos(): Flow<List<ProgressInfo>>

    suspend fun saveProgressInfo(progressInfo: ProgressInfo)

    suspend fun deleteProgressInfo(progressInfo: ProgressInfo)
}

@Singleton
class ProgressRepositoryImpl @Inject constructor(
    @param:LocalData private val localDataSource: ProgressRepository
) : ProgressRepository {

    override fun getProgressInfos(): Flow<List<ProgressInfo>> =
        localDataSource.getProgressInfos()

    override suspend fun saveProgressInfo(progressInfo: ProgressInfo) =
        localDataSource.saveProgressInfo(progressInfo)

    override suspend fun deleteProgressInfo(progressInfo: ProgressInfo) =
        localDataSource.deleteProgressInfo(progressInfo)
}