package com.myAllVideoBrowser.util.downloaders.generic_downloader.models

object Video {

    object Type {
        const val DEFAULT = 0
        const val HLS_TYPE = 1
        const val HLS_LIVE_TYPE = 2
        const val MP4_TYPE = 3
        const val WEBM_TYPE = 4
        const val QUICKTIME_TYPE = 5
        const val GP3_TYPE = 6
        const val MKV_TYPE = 7
    }

    object Mime {
        const val MIME_TYPE_MP4 = "video/mp4"
        const val MIME_TYPE_M3U8_1 = "application/vnd.apple.mpegurl"
        const val MIME_TYPE_M3U8_2 = "application/x-mpegurl"
        const val MIME_TYPE_M3U8_3 = "vnd.apple.mpegurl"
        const val MIME_TYPE_M3U8_4 = "applicationnd.apple.mpegurl"
        const val MIME_TYPE_WEBM = "video/webm"
        const val MIME_TYPE_QUICKTIME = "video/quicktime"
        const val MIME_TYPE_3GP = "video/3gp"
        const val MIME_TYPE_MKV = "video/x-matroska"
    }

    object TypeInfo {
        const val M3U8 = "m3u8"
        const val MP4 = "mp4"
        const val MOV = "mov"
        const val WEBM = "webm"
        const val GP3 = "3gp"
        const val MKV = "mkv"
        const val OTHER = "other"
    }

    object SUFFIX {
        const val SUFFIX_M3U8 = ".m3u8"
        const val SUFFIX_MP4 = ".mp4"
        const val SUFFIX_MOV = ".mov"
        const val SUFFIX_WEBM = ".webm"
        const val SUFFIX_3GP = ".3gp"
        const val SUFFIX_MKV = ".mkv"
    }
}
