package com.myAllVideoBrowser.ui.main.progress

import com.myAllVideoBrowser.util.AppLogger

import androidx.annotation.VisibleForTesting
import androidx.databinding.ObservableField
import androidx.lifecycle.viewModelScope
import com.myAllVideoBrowser.data.local.room.entity.ProgressInfo
import com.myAllVideoBrowser.data.local.room.entity.VideoInfo
import com.myAllVideoBrowser.data.repository.ProgressRepository
import com.myAllVideoBrowser.ui.main.base.BaseViewModel
import com.myAllVideoBrowser.util.ContextUtils
import com.myAllVideoBrowser.util.FileUtil
import com.myAllVideoBrowser.util.downloaders.generic_downloader.models.VideoTaskState
import com.myAllVideoBrowser.util.downloaders.custom_downloader.CustomRegularDownloader
import com.myAllVideoBrowser.util.downloaders.super_x_downloader.SuperXDownloader
import com.myAllVideoBrowser.util.downloaders.youtubedl_downloader.YoutubeDlDownloader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProgressViewModel @Inject constructor(
    private val fileUtil: FileUtil,
    private val progressRepository: ProgressRepository,
) : BaseViewModel() {

    var progressInfos: ObservableField<List<ProgressInfo>> = ObservableField(emptyList())

    override fun start() {
        downloadProgressStartListen()
    }

    override fun stop() {
    }

    fun stopAndSaveDownload(id: Long) {
        val inf = progressInfos.get()?.find { it.downloadId == id }

        if (inf?.videoInfo?.isRegularDownload == false) {
            inf.let {
                if (inf.videoInfo.isDetectedBySuperX) {
                    SuperXDownloader.stopAndSaveDownload(
                        ContextUtils.getApplicationContext(), it
                    )
                } else {
                    YoutubeDlDownloader.stopAndSaveDownload(
                        ContextUtils.getApplicationContext(), it
                    )
                }
            }
        } else {
            inf?.let {
                CustomRegularDownloader.stopAndSaveDownload(
                    ContextUtils.getApplicationContext(), it
                )
            }
        }
    }

    fun cancelDownload(id: Long, removeFile: Boolean) {
        val inf = progressInfos.get()?.find { it.downloadId == id }
        inf?.let { progressInfo ->
            deleteProgressInfo(progressInfo) { info ->
                if (info.videoInfo.isRegularDownload) {
                    CustomRegularDownloader.cancelDownload(
                        ContextUtils.getApplicationContext(),
                        inf,
                        removeFile
                    )
                } else {
                    info.let {
                        if (inf.videoInfo.isDetectedBySuperX) {
                            SuperXDownloader.cancelDownload(
                                ContextUtils.getApplicationContext(), it, removeFile
                            )
                        } else {
                            YoutubeDlDownloader.cancelDownload(
                                ContextUtils.getApplicationContext(), it, removeFile
                            )
                        }
                    }
                }
                val newList = progressInfos.get()?.filter { it.id != info.id }
                progressInfos.set(newList?.sortedBy { it.id })
            }
        }
    }

    fun pauseDownload(id: Long) {
        val inf = progressInfos.get()?.find { it.downloadId == id }

        if (inf?.videoInfo?.isRegularDownload == true) {
            CustomRegularDownloader.pauseDownload(ContextUtils.getApplicationContext(), inf)
        } else {
            val updated = inf?.copy(downloadStatus = VideoTaskState.PAUSE)
            if (updated != null) {
                saveProgressInfo(updated) { info ->
                    if (inf.videoInfo.isDetectedBySuperX) {
                        SuperXDownloader.pauseDownload(ContextUtils.getApplicationContext(), info)
                    } else {
                        YoutubeDlDownloader.pauseDownload(
                            ContextUtils.getApplicationContext(),
                            info
                        )
                    }
                }
            }
        }
    }

    fun resumeDownload(id: Long) {
        val inf = progressInfos.get()?.find { it.downloadId == id }

        if (inf?.videoInfo?.isRegularDownload == true) {
            CustomRegularDownloader.resumeDownload(ContextUtils.getApplicationContext(), inf)
        } else {
            inf?.let {
                val updated = inf.copy(downloadStatus = VideoTaskState.PREPARE)

                saveProgressInfo(updated) { info ->
                    if (inf.videoInfo.isDetectedBySuperX) {
                        SuperXDownloader.resumeDownload(
                            ContextUtils.getApplicationContext(),
                            info
                        )
                    } else {
                        YoutubeDlDownloader.resumeDownload(
                            ContextUtils.getApplicationContext(),
                            info
                        )
                    }
                }
            }
        }
    }

    fun downloadVideo(videoInfo: VideoInfo?) {
        val context = ContextUtils.getApplicationContext()

        videoInfo?.let {
            if (!fileUtil.folderDir.exists() && !fileUtil.folderDir.mkdirs()) {
                return
            }

            val downloadId = videoInfo.id.hashCode().toLong()
            val progressInfo = ProgressInfo(
                id = videoInfo.id,
                downloadId = downloadId,
                videoInfo = videoInfo,
                isM3u8 = videoInfo.isM3u8
            )

            saveProgressInfo(progressInfo) { info ->
                if (info.videoInfo.isRegularDownload) {
                    CustomRegularDownloader.startDownload(context, info.videoInfo)
                } else {
                    if (info.videoInfo.isDetectedBySuperX) {
                        SuperXDownloader.startDownload(context, info.videoInfo)
                    } else {
                        YoutubeDlDownloader.startDownload(context, info.videoInfo)
                    }
                }
            }
        }
    }

    private fun saveProgressInfo(
        progressInfo: ProgressInfo,
        onSuccess: (ProgressInfo) -> Unit = {}
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            progressRepository.saveProgressInfo(progressInfo)
            onSuccess(progressInfo)
        }
    }

    private fun deleteProgressInfo(
        progressInfo: ProgressInfo,
        onSuccess: (ProgressInfo) -> Unit = {}
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            progressRepository.deleteProgressInfo(progressInfo)
            onSuccess(progressInfo)
        }
    }

    @VisibleForTesting
    internal fun downloadProgressStartListen() {
        viewModelScope.launch {
            progressRepository.getProgressInfos()
                .map { list -> list.filter { it.downloadStatus != VideoTaskState.SUCCESS } }
                .flowOn(Dispatchers.IO)
                .catch { e -> AppLogger.e("Caught exception", e) }
                .collect { active ->
                    progressInfos.set(active.sortedBy { it.id })
                }
        }
    }
}
