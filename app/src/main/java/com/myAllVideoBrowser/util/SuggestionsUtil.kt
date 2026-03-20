package com.myAllVideoBrowser.util
import com.myAllVideoBrowser.data.local.model.Suggestion
import io.reactivex.rxjava3.core.BackpressureStrategy
import io.reactivex.rxjava3.core.Flowable
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import org.json.JSONObject
import java.net.URLEncoder

class SuggestionsUtils {
    companion object {
        fun getSuggestions(okHttpClient: OkHttpClient, input: String): Flowable<List<Suggestion>> {
            return Flowable.create({ emitter ->
                try {
                    val encodedInput = URLEncoder.encode(input, "UTF-8")
                    val url = "https://duckduckgo.com/ac/?q=$encodedInput&kl=wt-wt"
                        .toHttpUrlOrNull() ?: run {
                        emitter.onNext(emptyList())
                        return@create
                    }
                    val request = Request.Builder().url(url).build()
                    val response = okHttpClient.newCall(request).execute()
                        .use { resp -> resp.body?.string() ?: "" }

                    val result = ArrayList<Suggestion>()
                    val jsn = JSONArray(response)
                    for (i in 0 until jsn.length()) {
                        try {
                            val phraseObj = JSONObject(jsn.get(i).toString())
                            val phrase = phraseObj.get("phrase").toString()
                            result.add(Suggestion(content = phrase))
                        } catch (_: Throwable) {
                            // Skip malformed suggestion entry
                        }
                    }
                    emitter.onNext(result)
                } catch (e: Throwable) {
                    AppLogger.e("SuggestionsUtils: Failed to fetch suggestions", e)
                    emitter.onNext(emptyList())
                }
            }, BackpressureStrategy.LATEST)
        }
    }
}
