package com.myAllVideoBrowser.data.local

import com.myAllVideoBrowser.data.local.room.dao.HistoryDao
import com.myAllVideoBrowser.data.local.room.entity.HistoryItem
import com.myAllVideoBrowser.data.repository.HistoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HistoryLocalDataSource @Inject constructor(private val historyDao: HistoryDao) :
    HistoryRepository {
    override fun getAllHistory(): Flow<List<HistoryItem>> = historyDao.getHistory()

    override suspend fun saveHistory(history: HistoryItem) = historyDao.insertHistoryItem(history)

    override suspend fun deleteHistory(history: HistoryItem) = historyDao.deleteHistoryItem(history)

    override suspend fun deleteAllHistory() = historyDao.clear()
}