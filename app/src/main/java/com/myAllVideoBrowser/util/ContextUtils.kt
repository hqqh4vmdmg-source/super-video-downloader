package com.myAllVideoBrowser.util

import android.content.Context

object ContextUtils {
    private var sApplicationContext: Context? = null

    fun initApplicationContext(appContext: Context) {
        sApplicationContext = appContext.applicationContext
    }

    fun getApplicationContext(): Context =
        checkNotNull(sApplicationContext) { "Global application context not initialized" }
}
