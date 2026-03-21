package com.myAllVideoBrowser.ui.main.home.browser.homeTab

import com.myAllVideoBrowser.util.AppLogger

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.viewModelScope
import com.myAllVideoBrowser.data.local.model.Suggestion
import com.myAllVideoBrowser.ui.main.base.BaseViewModel
import com.myAllVideoBrowser.util.SuggestionsUtils
import com.myAllVideoBrowser.util.proxy_utils.OkHttpProxyClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class BrowserHomeViewModel @Inject constructor(
    private val okHttpClient: OkHttpProxyClient,
) :
    BaseViewModel() {
    val isSearchInputFocused = ObservableBoolean(false)
    val searchTextInput = ObservableField("")
    val listSuggestions: ObservableField<MutableList<Suggestion>> = ObservableField(mutableListOf())

    private var suggestionJob: Job? = null

    override fun start() {
    }

    override fun stop() {
    }

    fun changeSearchFocus(isFocus: Boolean) {
        this.isSearchInputFocused.set(isFocus)
    }

    fun showSuggestions() {
        suggestionJob?.cancel()
        suggestionJob = viewModelScope.launch(Dispatchers.IO) {
            delay(300)
            try {
                val list = SuggestionsUtils.getSuggestions(
                    okHttpClient.getProxyOkHttpClient(), searchTextInput.get() ?: ""
                )
                if (list.size > 50) {
                    listSuggestions.set(list.subList(0, 50).toMutableList())
                } else {
                    listSuggestions.set(list.toMutableList())
                }
            } catch (e: Throwable) {
                listSuggestions.set(mutableListOf())
                AppLogger.e("Caught exception", e)
            }
        }
    }
}