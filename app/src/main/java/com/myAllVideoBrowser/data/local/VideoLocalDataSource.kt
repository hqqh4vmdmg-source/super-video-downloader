package com.myAllVideoBrowser.data.local

import com.myAllVideoBrowser.data.local.room.dao.VideoDao
import com.myAllVideoBrowser.data.local.room.entity.VideoInfo
import com.myAllVideoBrowser.data.repository.VideoRepository
import kotlinx.coroutines.runBlocking
import okhttp3.Request
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VideoLocalDataSource @Inject constructor(
    private val videoDao: VideoDao
) : VideoRepository {
    override fun getVideoInfoBySuperXDetector(
        url: Request,
        isM3u8: Boolean,
        isMpd: Boolean,
        isAudioCheck: Boolean
    ): VideoInfo? = runBlocking { videoDao.getVideoById(url.url.toString()) }

    override fun getVideoInfo(
        url: Request,
        isM3u8OrMpd: Boolean,
        isAudioCheck: Boolean
    ): VideoInfo? = runBlocking { videoDao.getVideoById(url.url.toString()) }

    override fun saveVideoInfo(videoInfo: VideoInfo) {
        runBlocking { videoDao.insertVideo(videoInfo) }
    }
}