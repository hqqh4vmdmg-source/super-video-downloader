package com.myAllVideoBrowser.util
import android.Manifest
import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.myAllVideoBrowser.R
import com.myAllVideoBrowser.ui.main.home.MainActivity
import com.myAllVideoBrowser.util.downloaders.NotificationReceiver
import com.myAllVideoBrowser.util.downloaders.generic_downloader.models.VideoTaskItem
import com.myAllVideoBrowser.util.downloaders.generic_downloader.models.VideoTaskState
import com.myAllVideoBrowser.util.downloaders.youtubedl_downloader.YoutubeDlDownloaderWorker
import java.io.File
import javax.inject.Singleton

@Singleton
class NotificationsHelper(private val context: Context) {
    companion object {
        const val NOTIFICATION_CHANNEL_ID = "NOTIFICATION_CHANNEL_ID_ALL_DOWNLOADER"
        private const val DOWNLOAD_ICON_RES = R.drawable.ic_download_24dp
        private const val VIDEO_ICON_RES = R.drawable.ic_video_24dp
        private const val CANCEL_ICON_RES = R.drawable.ic_cancel_24dp
    }

    private val notificationManager = NotificationManagerCompat.from(context)

    init {
        createChannel(context)
    }

    fun createNotificationBuilder(task: VideoTaskItem): Pair<Int, NotificationCompat.Builder> {
        val taskPercent = if (task.percentFromBytes == 0F) task.percent else task.percentFromBytes

        val builder = NotificationCompat.Builder(
            context, NOTIFICATION_CHANNEL_ID
        ).setOnlyAlertOnce(true)

        builder.setContentTitle(File(task.fileName).name).setContentText(task.lineInfo)
            .setSmallIcon(DOWNLOAD_ICON_RES).setOngoing(false)
            .setProgress(100, taskPercent.toInt(), false).addAction(notificationActionOpen(false))

        when (task.taskState) {
            VideoTaskState.PREPARE -> {
                builder.setSubText("prepare").setProgress(0, 0, true)
                builder.setOngoing(false).setSmallIcon(DOWNLOAD_ICON_RES)
                builder.addAction(createPauseBroadcastMessage(task.mId))
                builder.addAction(createCancelBroadcastMessage(task.mId))
            }

            VideoTaskState.PENDING -> {
                builder.setSubText("pending").setProgress(0, 0, true)
                builder.setOngoing(false).setSmallIcon(DOWNLOAD_ICON_RES)
                builder.addAction(createPauseBroadcastMessage(task.mId))
                builder.addAction(createCancelBroadcastMessage(task.mId))
            }

            VideoTaskState.DOWNLOADING -> {
                builder.setSubText("downloading...").setProgress(100, taskPercent.toInt(), false)
                builder.setOngoing(false).setSmallIcon(DOWNLOAD_ICON_RES)
                builder.addAction(createPauseBroadcastMessage(task.mId))
                builder.addAction(createCancelBroadcastMessage(task.mId))
            }

            VideoTaskState.PAUSE -> {
                builder.setSubText("pause")
                builder.setProgress(100, taskPercent.toInt(), false)
                builder.setOngoing(false).setSmallIcon(DOWNLOAD_ICON_RES)
                builder.addAction(createResumeBroadcastMessage(task.mId))
                builder.addAction(createCancelBroadcastMessage(task.mId))
            }

            VideoTaskState.SUCCESS -> {
                builder.clearActions()
                val actionOpenInApp = notificationActionOpen(true)
                val actionWatch = notificationActionWatch(task.fileName)
                val actionWatchIntent = notificationIntentWatch(task.fileName)

                builder.setContentIntent(actionWatchIntent)
                builder.setSubText("success!!!").setProgress(0, 0, false)
                builder.setOngoing(false).setSmallIcon(VIDEO_ICON_RES)
                builder.addAction(actionOpenInApp).addAction(actionWatch)
            }

            VideoTaskState.ERROR, VideoTaskState.ENOSPC -> {
                builder.clearActions()
                val action = notificationActionOpen(true)

                builder.setSubText("Error")
                builder.setContentText("Failed " + task.errorMessage)
                    .setProgress(100, taskPercent.toInt(), false)
                builder.setOngoing(false).setSmallIcon(CANCEL_ICON_RES)
                builder.addAction(action)
                builder.addAction(createResumeBroadcastMessage(task.mId))
            }

            VideoTaskState.CANCELED -> {
                builder.setSubText("Canceled")
                builder.setProgress(0, 0, false)
                builder.setOngoing(false).setSmallIcon(CANCEL_ICON_RES)
            }

            else -> {}
        }

        return task.mId.hashCode() to builder
    }

    @SuppressLint("MissingPermission")
    fun showNotification(builderPair: Pair<Int, NotificationCompat.Builder>) {
        val allowed = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) ==
                PackageManager.PERMISSION_GRANTED
        } else {
            true
        }
        if (allowed) {
            notificationManager.notify(builderPair.first, builderPair.second.build())
        }
    }

    fun hideNotification(id: Int) {
        notificationManager.cancel(id)
    }


    private fun notificationActionOpen(
        isFinished: Boolean, isError: Boolean = false
    ): NotificationCompat.Action {
        val intent = Intent(
            context, MainActivity::class.java
        )

        intent.putExtra(YoutubeDlDownloaderWorker.IS_FINISHED_DOWNLOAD_ACTION_KEY, isFinished)
        intent.putExtra(YoutubeDlDownloaderWorker.IS_FINISHED_DOWNLOAD_ACTION_ERROR_KEY, isError)


        val pendingIntent = PendingIntent.getActivity(
            context,
            if (isFinished) 0 else 2,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
        return NotificationCompat.Action(
            VIDEO_ICON_RES,
            context.resources.getString(R.string.download_open_in_app),
            pendingIntent
        )
    }

    private fun notificationActionWatch(filename: String): NotificationCompat.Action {
        return NotificationCompat.Action(
            VIDEO_ICON_RES,
            context.resources.getString(R.string.download_watch_in_app),
            notificationIntentWatch(filename)
        )
    }

    private fun notificationIntentWatch(filename: String): PendingIntent {
        val filenameFixed = File(filename).name
        val intent = Intent(
            context, MainActivity::class.java
        )
        intent.putExtra(YoutubeDlDownloaderWorker.IS_FINISHED_DOWNLOAD_ACTION_KEY, true)
            .putExtra(YoutubeDlDownloaderWorker.DOWNLOAD_FILENAME_KEY, filenameFixed)

        return PendingIntent.getActivity(
            context,
            777,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
    }

    private fun createCancelBroadcastMessage(taskId: String): NotificationCompat.Action {
        val intent = Intent(context, NotificationReceiver::class.java)
        intent.putExtra(NotificationReceiver.TASK_ID, taskId)
        intent.action = NotificationReceiver.ACTION_CANCEL

        return NotificationCompat.Action(
            CANCEL_ICON_RES,
            context.resources.getString(R.string.progress_menu_cancel),
            createActionIntent(intent, taskId.hashCode())
        )
    }

    private fun createPauseBroadcastMessage(taskId: String): NotificationCompat.Action {
        val intent = Intent(context, NotificationReceiver::class.java)
        intent.putExtra(NotificationReceiver.TASK_ID, taskId)
        intent.action = NotificationReceiver.ACTION_PAUSE

        return NotificationCompat.Action(
            DOWNLOAD_ICON_RES,
            context.resources.getString(R.string.progress_menu_pause),
            createActionIntent(intent, taskId.hashCode())
        )
    }

    private fun createResumeBroadcastMessage(taskId: String): NotificationCompat.Action {
        val intent = Intent(context, NotificationReceiver::class.java)
        intent.putExtra(NotificationReceiver.TASK_ID, taskId)
        intent.action = NotificationReceiver.ACTION_RESUME

        return NotificationCompat.Action(
            DOWNLOAD_ICON_RES,
            context.resources.getString(R.string.progress_menu_resume),
            createActionIntent(intent, taskId.hashCode())
        )
    }

    private fun createActionIntent(actionIntent: Intent, requestCode: Int): PendingIntent {
        return PendingIntent.getBroadcast(
            context,
            requestCode,
            actionIntent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
    }

    private fun createChannel(appContext: Context) {
        val name = appContext.applicationInfo.loadLabel(appContext.packageManager)
        val channelDescription = context.getString(R.string.app_download_channel_id)
        val channel = NotificationChannelCompat.Builder(
            NOTIFICATION_CHANNEL_ID,
            NotificationManagerCompat.IMPORTANCE_HIGH
        )
            .setName(name)
            .setDescription(channelDescription)
            .setSound(null, null)
            .build()
        notificationManager.createNotificationChannel(channel)
    }
}
