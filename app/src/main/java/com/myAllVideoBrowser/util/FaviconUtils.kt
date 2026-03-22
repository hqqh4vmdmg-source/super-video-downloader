package com.myAllVideoBrowser.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import kotlinx.coroutines.delay
import okhttp3.OkHttpClient
import okhttp3.Request
import okio.use
import java.io.ByteArrayOutputStream

object FaviconUtils {
    fun bitmapToBytes(bitmap: Bitmap?): ByteArray {
        val stream = ByteArrayOutputStream()
        bitmap?.compress(Bitmap.CompressFormat.PNG, 90, stream)
        return stream.toByteArray()
    }

    suspend fun getEncodedFaviconFromUrl(okHttpClient: OkHttpClient, url: String): Bitmap? {
        delay(0)
        return fetchFavicon(okHttpClient, url)
    }

    private fun fetchFavicon(okHttpClient: OkHttpClient, url: String): Bitmap? {
        val host = Uri.parse(url).host ?: return null
        val potentialUrls = listOf(
            "https://$host/favicon.ico",
            "https://${host.replaceFirst("www.", "")}/favicon.ico",
        )

        for (reqUrl in potentialUrls) {
            val request = Request.Builder().url(reqUrl).build()
            val response = okHttpClient.newCall(request).execute()

            if (response.isSuccessful) {
                val bodyStream = response.body?.byteStream() ?: run {
                    response.close()
                    return null
                }
                bodyStream.use { stream ->
                    return BitmapFactory.decodeStream(stream)
                }
            }
            response.close()
        }

        return null
    }
}
