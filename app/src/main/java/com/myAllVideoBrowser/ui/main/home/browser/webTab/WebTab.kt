package com.myAllVideoBrowser.ui.main.home.browser.webTab

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Message
import android.webkit.WebView
import java.util.UUID

class WebTab(
    val url: String,
    title: String?,
    val favicon: Bitmap? = null,
    val headers: Map<String, String> = emptyMap(),
    var webView: WebView? = null,
    private var resultMsg: Message? = null,
    val id: String = UUID.randomUUID().toString()
) {
    val title: String = title ?: ""

    companion object {
        @SuppressLint("StaticFieldLeak")
        val HOME_TAB = WebTab(
            "",
            "Home Tab",
            id = "home"
        )
    }

    fun getMessage(): Message? = resultMsg

    fun flushMessage() {
        resultMsg = null
    }

    fun isHome(): Boolean = id.contains("home")

    override fun toString(): String =
        "WebTab(url='$url', title=$title, favicon=$favicon, headers=$headers, webView=$webView, resultMsg=$resultMsg, id='$id')"

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as WebTab
        return url == other.url &&
            title == other.title &&
            favicon == other.favicon &&
            headers == other.headers &&
            webView == other.webView &&
            resultMsg == other.resultMsg &&
            id == other.id
    }

    override fun hashCode(): Int {
        var result = url.hashCode()
        result = 31 * result + title.hashCode()
        result = 31 * result + (favicon?.hashCode() ?: 0)
        result = 31 * result + headers.hashCode()
        result = 31 * result + (webView?.hashCode() ?: 0)
        result = 31 * result + (resultMsg?.hashCode() ?: 0)
        result = 31 * result + id.hashCode()
        return result
    }
}
