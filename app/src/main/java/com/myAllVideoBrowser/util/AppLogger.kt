package com.myAllVideoBrowser.util
import android.util.Log
import com.myAllVideoBrowser.BuildConfig
import com.myAllVideoBrowser.DLApplication

class AppLogger {

    companion object {

        private const val TAG = DLApplication.DEBUG_TAG

        fun d(message: String) {
            if (BuildConfig.DEBUG) {
                Log.d(TAG, message)
            }
        }

        fun d(message: String, throwable: Throwable) {
            if (BuildConfig.DEBUG) {
                Log.d(TAG, message, throwable)
            }
        }

        fun i(message: String) {
            if (BuildConfig.DEBUG) {
                Log.i(TAG, message)
            }
        }

        fun i(message: String, throwable: Throwable) {
            if (BuildConfig.DEBUG) {
                Log.i(TAG, message, throwable)
            }
        }

        fun w(message: String) {
            Log.w(TAG, message)
        }

        fun w(message: String, throwable: Throwable) {
            Log.w(TAG, message, throwable)
        }

        fun e(message: String) {
            Log.e(TAG, message)
        }

        fun e(message: String, throwable: Throwable) {
            Log.e(TAG, message, throwable)
        }
    }
}
