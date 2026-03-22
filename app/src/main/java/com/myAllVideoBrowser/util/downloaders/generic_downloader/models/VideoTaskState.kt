package com.myAllVideoBrowser.util.downloaders.generic_downloader.models

object VideoTaskState {
    const val DEFAULT = 0       // Default state
    const val PENDING = -1      // Download queued
    const val PREPARE = 1       // Download preparing
    const val START = 2         // Download starting
    const val DOWNLOADING = 3   // Downloading
    const val PROXYREADY = 4    // Video can be streamed while downloading
    const val SUCCESS = 5       // Download complete
    const val ERROR = 6         // Download error
    const val PAUSE = 7         // Download paused
    const val ENOSPC = 8        // Not enough space
    const val CANCELED = 9      // Download canceled
}
