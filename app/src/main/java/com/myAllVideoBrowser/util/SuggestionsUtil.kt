package com.myAllVideoBrowser.util

import com.myAllVideoBrowser.data.local.model.Suggestion
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import org.json.JSONObject
import java.net.URLEncoder

object SuggestionsUtils {
    suspend fun getSuggestions(okHttpClient: OkHttpClient, input: String): List<Suggestion> =
        fetchSuggestions(okHttpClient, input)

    private fun fetchSuggestions(okHttpClient: OkHttpClient, input: String): List<Suggestion> {
        return try {
            val encodedInput = URLEncoder.encode(input, "UTF-8")
            val url = "https://duckduckgo.com/ac/?q=$encodedInput&kl=wt-wt"
                .toHttpUrlOrNull() ?: return emptyList()
            val request = Request.Builder().url(url).build()
            val response = okHttpClient.newCall(request).execute()
                .use { resp -> resp.body?.string() ?: "" }

            val jsn = JSONArray(response)
            (0 until jsn.length()).mapNotNull { i ->
                runCatching {
                    Suggestion(content = JSONObject(jsn.get(i).toString()).get("phrase").toString())
                }.getOrNull()
            }
        } catch (e: Throwable) {
            AppLogger.e("SuggestionsUtils: Failed to fetch suggestions", e)
            emptyList()
        }
    }
}
