package com.myAllVideoBrowser.ui.component.binding

import androidx.databinding.BindingAdapter
import android.net.Uri
import androidx.core.content.FileProvider
import android.widget.VideoView
import java.io.File

object VideoViewBinding {

    @BindingAdapter("videoURI")
    @JvmStatic
    fun setVideoURI(view: VideoView, videoPath: String?) {
        videoPath?.let { path ->
            val uri = if (path.startsWith("http")) {
                Uri.parse(path)
            } else {
                FileProvider.getUriForFile(view.context, view.context.packageName + ".provider", File(path))
            }
            view.setVideoURI(uri)
        }
    }
}
