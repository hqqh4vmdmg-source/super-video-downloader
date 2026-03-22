package com.myAllVideoBrowser.ui.main.home.browser.webTab

import android.util.Patterns
import com.myAllVideoBrowser.ui.main.home.browser.BrowserViewModel

object WebTabFactory {
    fun createWebTabFromInput(input: String): WebTab {
        if (input.isEmpty()) return WebTab.HOME_TAB
        return when {
            input.startsWith("http://") || input.startsWith("https://") ->
                WebTab(input, null)
            Patterns.WEB_URL.matcher(input).matches() ->
                WebTab("https://$input", null)
            else ->
                WebTab(String.format(BrowserViewModel.SEARCH_URL, input), null)
        }
    }
}
