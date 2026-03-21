package com.myAllVideoBrowser.ui.main.history

import com.myAllVideoBrowser.util.AppLogger

import androidx.databinding.ObservableField
import androidx.lifecycle.viewModelScope
import com.myAllVideoBrowser.data.local.room.entity.HistoryItem
import com.myAllVideoBrowser.data.repository.HistoryRepository
import com.myAllVideoBrowser.ui.main.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

class HistoryViewModel @Inject constructor(
    private val historyRepository: HistoryRepository,
) :
    BaseViewModel() {

    var historyItems = ObservableField<List<HistoryItem>>(emptyList())

    var searchHistoryItems = ObservableField<List<HistoryItem>>(emptyList())

    val searchQuery = ObservableField("")

    val isLoadingHistory = ObservableField(true)

    override fun start() {
        fetchAllHistory()
    }

    override fun stop() {
    }

    private fun fetchAllHistory() {
        isLoadingHistory.set(true)
        viewModelScope.launch {
            historyRepository.getAllHistory()
                .flowOn(Dispatchers.IO)
                .catch { e -> AppLogger.e("Error fetching history", e) }
                .collect { history ->
                    historyItems.set(history)
                    isLoadingHistory.set(false)
                }
        }
    }

    fun saveHistory(historyItem: HistoryItem) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                historyRepository.saveHistory(historyItem)
            } catch (e: Throwable) {
                AppLogger.e("Caught exception", e)
            }
        }
    }

    fun deleteHistory(historyItem: HistoryItem) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val newItems = historyItems.get()?.filter { it.id != historyItem.id }
                historyItems.set(newItems)
                historyRepository.deleteHistory(historyItem)
            } catch (e: Throwable) {
                AppLogger.e("Caught exception", e)
            }
        }
    }

    fun queryHistory(query: String) {
        if (query.isEmpty()) {
            searchHistoryItems.set(emptyList())
        }
        if (query.isNotEmpty()) {
            val filtered = historyItems.get()
                ?.filter { it.url.contains(query) || it.title?.contains(query) ?: false }
            searchHistoryItems.set(filtered ?: emptyList())
        }
    }

    fun clearHistory() {
        viewModelScope.launch(Dispatchers.IO) {
            isLoadingHistory.set(true)
            historyRepository.deleteAllHistory()
            historyItems.set(emptyList())
            searchHistoryItems.set(emptyList())
            isLoadingHistory.set(false)
        }
    }
}