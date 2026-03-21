package com.myAllVideoBrowser.ui.component.binding

import androidx.databinding.BindingAdapter
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient

object WebViewBinding {

    @BindingAdapter("loadUrl")
    @JvmStatic
    fun loadUrl(view: WebView, url: String?) {
        url?.let { if (url.isNotEmpty()) view.loadUrl(it) }
    }

    @BindingAdapter("javaScriptEnabled")
    @JvmStatic
    fun javaScriptEnabled(view: WebView, isEnabled: Boolean?) {
        isEnabled?.let { view.settings.javaScriptEnabled = it }
    }

    @BindingAdapter("addJavascriptInterface")
    @JvmStatic
    fun addJavascriptInterface(view: WebView, name: String?) {
        name?.let { view.addJavascriptInterface(view.context, it) }
    }

    @BindingAdapter("webViewClient")
    @JvmStatic
    fun webViewClient(view: WebView, webViewClient: WebViewClient?) {
        webViewClient?.let { view.webViewClient = it }
    }

    @BindingAdapter("webChromeClient")
    @JvmStatic
    fun webChromeClient(view: WebView, webChromeClient: WebChromeClient?) {
        webChromeClient?.let { view.webChromeClient = it }
    }
}
