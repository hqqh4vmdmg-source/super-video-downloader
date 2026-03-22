package com.myAllVideoBrowser.util.downloaders.generic_downloader.models

class VideoTaskItem(url: String, coverUrl: String = "", title: String = "", groupName: String = "") :
    Cloneable {

    var mUrl: String = url
    var mCoverUrl: String = coverUrl
    var mCoverPath: String = ""
    var mTitle: String = title
    var mGroupName: String = groupName
    var mDownloadCreateTime: Long = 0
    var mTaskState: Int = VideoTaskState.DEFAULT
    var mMimeType: String? = null
    var mFinalUrl: String? = null
    var mErrorCode: Int = 0
    var mVideoType: Int = Video.Type.DEFAULT
    var mTotalTs: Int = 0
    var mCurTs: Int = 0
    var mSpeed: Float = 0f
    var mPercent: Float = 0f
    var mDownloadSize: Long = 0
    var mTotalSize: Long = 0
    var mFileHash: String? = null
    var mSaveDir: String? = null
    var mIsCompleted: Boolean = false
    var mIsInDatabase: Boolean = false
    var mLastUpdateTime: Long = 0
    var mFileName: String = ""
    var mFilePath: String = ""
    var mPaused: Boolean = false
    var mIsLive: Boolean = false
    var mErrorMessage: String? = null
    var mId: String? = null
    var lineInfo: String? = null
    var accumulatedDuration: Long = 0L

    // Convenience accessors matching the old Java getter/setter names
    var url: String get() = mUrl; set(v) { mUrl = v }
    var coverUrl: String get() = mCoverUrl; set(v) { mCoverUrl = v }
    var coverPath: String get() = mCoverPath; set(v) { mCoverPath = v }
    var title: String get() = mTitle; set(v) { mTitle = v }
    var groupName: String get() = mGroupName; set(v) { mGroupName = v }
    var downloadCreateTime: Long get() = mDownloadCreateTime; set(v) { mDownloadCreateTime = v }
    var taskState: Int get() = mTaskState; set(v) { mTaskState = v }
    var mimeType: String? get() = mMimeType; set(v) { mMimeType = v }
    var finalUrl: String? get() = mFinalUrl; set(v) { mFinalUrl = v }
    var errorCode: Int get() = mErrorCode; set(v) { mErrorCode = v }
    var videoType: Int get() = mVideoType; set(v) { mVideoType = v }
    var totalTs: Int get() = mTotalTs; set(v) { mTotalTs = v }
    var curTs: Int get() = mCurTs; set(v) { mCurTs = v }
    var speed: Float get() = mSpeed; set(v) { mSpeed = v }
    var percent: Float get() = mPercent; set(v) { mPercent = v }
    var downloadSize: Long get() = mDownloadSize; set(v) { mDownloadSize = v }
    var totalSize: Long get() = mTotalSize; set(v) { mTotalSize = v }
    var fileHash: String? get() = mFileHash; set(v) { mFileHash = v }
    var saveDir: String? get() = mSaveDir; set(v) { mSaveDir = v }
    var isCompleted: Boolean get() = mIsCompleted; set(v) { mIsCompleted = v }
    var isInDatabase: Boolean get() = mIsInDatabase; set(v) { mIsInDatabase = v }
    var lastUpdateTime: Long get() = mLastUpdateTime; set(v) { mLastUpdateTime = v }
    var fileName: String get() = mFileName; set(v) { mFileName = v }
    var filePath: String get() = mFilePath; set(v) { mFilePath = v }
    var isPaused: Boolean get() = mPaused; set(v) { mPaused = v }
    var isLive: Boolean get() = mIsLive; set(v) { mIsLive = v }
    var errorMessage: String? get() = mErrorMessage; set(v) { mErrorMessage = v }
    var mId_: String? get() = mId; set(v) { mId = v }

    val percentFromBytes: Float
        get() {
            if (mTotalSize == 0L) return 0f
            return (mDownloadSize.toFloat() / mTotalSize.toFloat()) * 100f
        }

    fun getPercentFromBytes(downloadSize: Long, totalSize: Long): Float {
        if (totalSize == 0L) return 0f
        return (downloadSize.toFloat() / totalSize.toFloat()) * 100f
    }

    val isRunningTask: Boolean get() = mTaskState == VideoTaskState.DOWNLOADING
    val isPendingTask: Boolean get() = mTaskState == VideoTaskState.PENDING || mTaskState == VideoTaskState.PREPARE
    val isErrorState: Boolean get() = mTaskState == VideoTaskState.ERROR
    val isSuccessState: Boolean get() = mTaskState == VideoTaskState.SUCCESS
    val isInterruptTask: Boolean get() = mTaskState == VideoTaskState.PAUSE || mTaskState == VideoTaskState.ERROR
    val isInitialTask: Boolean get() = mTaskState == VideoTaskState.DEFAULT
    val isHlsType: Boolean get() = mVideoType == Video.Type.HLS_TYPE

    fun reset() {
        mDownloadCreateTime = 0L
        mMimeType = null
        mErrorCode = 0
        mVideoType = Video.Type.DEFAULT
        mTaskState = VideoTaskState.DEFAULT
        mSpeed = 0f
        mPercent = 0f
        mDownloadSize = 0
        mTotalSize = 0
        mFileName = ""
        mFilePath = ""
        mCoverUrl = ""
        mCoverPath = ""
        mTitle = ""
        mGroupName = ""
        mErrorMessage = null
        lineInfo = null
    }

    public override fun clone(): VideoTaskItem {
        val cloned = VideoTaskItem(mUrl)
        cloned.mCoverUrl = mCoverUrl
        cloned.mCoverPath = mCoverPath
        cloned.mTitle = mTitle
        cloned.mGroupName = mGroupName
        cloned.mDownloadCreateTime = mDownloadCreateTime
        cloned.mTaskState = mTaskState
        cloned.mMimeType = mMimeType
        cloned.mFinalUrl = mFinalUrl
        cloned.mErrorCode = mErrorCode
        cloned.mVideoType = mVideoType
        cloned.mTotalTs = mTotalTs
        cloned.mCurTs = mCurTs
        cloned.mSpeed = mSpeed
        cloned.mPercent = mPercent
        cloned.mDownloadSize = mDownloadSize
        cloned.mTotalSize = mTotalSize
        cloned.mFileHash = mFileHash
        cloned.mSaveDir = mSaveDir
        cloned.mIsCompleted = mIsCompleted
        cloned.mIsInDatabase = mIsInDatabase
        cloned.mLastUpdateTime = mLastUpdateTime
        cloned.mFileName = mFileName
        cloned.mFilePath = mFilePath
        cloned.mPaused = mPaused
        cloned.mIsLive = mIsLive
        cloned.mErrorMessage = mErrorMessage
        cloned.mId = mId
        cloned.lineInfo = lineInfo
        cloned.accumulatedDuration = accumulatedDuration
        return cloned
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is VideoTaskItem) return false
        return if (mId != null) mId == other.mId else mUrl == other.mUrl
    }

    override fun hashCode(): Int = mId?.hashCode() ?: mUrl.hashCode()

    override fun toString(): String =
        "VideoTaskItem[mId='$mId', mUrl='$mUrl', mTitle='$mTitle', " +
        "mTaskState=$mTaskState, mPercent=$mPercent, mDownloadSize=$mDownloadSize, " +
        "mTotalSize=$mTotalSize, mFilePath='$mFilePath', isLive=$mIsLive]"
}
