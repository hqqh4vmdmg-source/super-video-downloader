package com.myAllVideoBrowser.data.repository

import com.myAllVideoBrowser.data.local.room.entity.HistoryItem
import com.myAllVideoBrowser.di.qualifier.LocalData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

interface HistoryRepository {
    fun getAllHistory(): Flow<List<HistoryItem>>

    suspend fun saveHistory(history: HistoryItem)

    suspend fun deleteHistory(history: HistoryItem)

    suspend fun deleteAllHistory()
}

@Singleton
class HistoryRepositoryImpl @Inject constructor(
    @param:LocalData private val localDataSource: HistoryRepository
) : HistoryRepository {
    override fun getAllHistory(): Flow<List<HistoryItem>> = localDataSource.getAllHistory()

    override suspend fun saveHistory(history: HistoryItem) = localDataSource.saveHistory(history)

    override suspend fun deleteHistory(history: HistoryItem) =
        localDataSource.deleteHistory(history)

    override suspend fun deleteAllHistory() = localDataSource.deleteAllHistory()
}