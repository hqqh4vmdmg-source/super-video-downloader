package com.myAllVideoBrowser.util

import com.myAllVideoBrowser.ui.main.home.browser.ContentType
import com.myAllVideoBrowser.util.proxy_utils.OkHttpProxyClient
import okhttp3.Headers
import okhttp3.Request

object VideoUtils {
    fun getContentTypeByUrl(
        url: String,
        headers: Headers?,
        okHttpProxyClient: OkHttpProxyClient
    ): ContentType {
        val regex = Regex("\\.(js|css|m4s|ts)$|^blob:")
        if (regex.containsMatchIn(url)) {
            return ContentType.OTHER
        }

        val request = Request.Builder()
            .url(url)
            .headers(headers ?: Headers.headersOf())
            .get()
            .build()

        return runCatching {
            okHttpProxyClient.getProxyOkHttpClient().newCall(request).execute()
                .use { response ->
                    val contentTypeStr = response.header("Content-Type")

                    when {
                        contentTypeStr?.contains("mpegurl") == true -> ContentType.M3U8
                        contentTypeStr?.contains("dash") == true -> ContentType.MPD
                        contentTypeStr?.contains("video") == true -> ContentType.VIDEO
                        contentTypeStr?.contains("audio", ignoreCase = true) == true -> ContentType.AUDIO
                        contentTypeStr?.contains("application/octet-stream") == true -> {
                            response.body.charStream().use { reader ->
                                val buf = CharArray(7)
                                val content = if (reader.read(buf, 0, 7) > 0) String(buf) else ""
                                when {
                                    content.startsWith("#EXTM3U") -> ContentType.M3U8
                                    content.contains("<MPD") -> ContentType.MPD
                                    else -> ContentType.OTHER
                                }
                            }
                        }
                        else -> ContentType.OTHER
                    }
                }
        }.getOrDefault(ContentType.OTHER)
    }
}
